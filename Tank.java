package tankwar.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * 坦克类
 * @author zhjl
 */
public class Tank {

	//设置坦克长、宽、速度、发射频率
	public static final int SPEED = 10;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	public static final int SHOOT_FREQ = 3;

	TankClient tc;
	private boolean good;

	public boolean isGood() {
		return good;
	}

	private boolean live = true;

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private BloodBar bb = new BloodBar();

	private int life = 100;
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	private int x, y;
	private int oldX, oldY;
	
	// 多个对象共享一个随机数产生器
	private static Random r = new Random();

	private boolean bL = false, bR = false, bU = false, bD = false;

	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;
	private Direction fireDir = Direction.D;

	private int step = r.nextInt(12) + 3;

	/**
	 * 坦克构造方法一
	 * @param x 坦克横坐标
	 * @param y 坦克纵坐标
	 * @param good 坦克身份
	 */
	public Tank(int x, int y, boolean good) {
		super();
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}

	/**
	 * 坦克构造方法二
	 * @param x 坦克横坐标
	 * @param y 坦克纵坐标
	 * @param good 坦克身份
	 * @param dir 坦克前进方向
	 * @param tc 调用端引用
	 */
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}

	/**
	 * 画坦克方法
	 * @param g 画笔
	 */
	public void draw(Graphics g) {
		
		//如果死了就不画，如果是敌方坦克直接移除
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
		}

		//如果是我方坦克，画血条
		if(good) bb.draw(g);
		
		//画坦克方法
		Color c = g.getColor();
		if (good)
			g.setColor(Color.YELLOW);
		else
			g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);

		//根据开火方向画炮管
		switch (fireDir) {
		case L:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x - Tank.WIDTH / 2, y + Tank.HEIGHT / 2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x - Tank.WIDTH / 2, y - Tank.HEIGHT / 2);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y - Tank.HEIGHT / 2);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH * 3 / 2, y - Tank.HEIGHT / 2);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH * 3 / 2, y + Tank.HEIGHT / 2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH * 3 / 2, y + Tank.HEIGHT * 3 / 2);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y + Tank.HEIGHT * 3 / 2);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x - Tank.WIDTH / 2, y + Tank.HEIGHT * 3 / 2);
			break;
		default:
			break;
		}
		
		//调用move()方法，修改各项参数
		move();
	}

	/*
	 * 根据新的输入修改坦克状态
	 */
	void move() {
		
		//保存旧的位置，以便碰撞后返回
		this.oldX = x;
		this.oldY = y;

		//根据方向，以SPEED为单位修改坐标
		switch (dir) {
		case L:
			x -= SPEED;
			break;
		case LU:
			x -= SPEED;
			y -= SPEED;
			break;
		case U:
			y -= SPEED;
			break;
		case RU:
			x += SPEED;
			y -= SPEED;
			break;
		case R:
			x += SPEED;
			break;
		case RD:
			x += SPEED;
			y += SPEED;
			break;
		case D:
			y += SPEED;
			break;
		case LD:
			x -= SPEED;
			y += SPEED;
			break;
		case STOP:
			break;
		}

		//如果坦克没有停止，前进方向就是开火方向
		if (this.dir != Direction.STOP)
			this.fireDir = this.dir;

		// 解决坦克出界问题
		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > TankClient.WIDTH)
			x = TankClient.WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.HEIGHT)
			y = TankClient.HEIGHT - Tank.HEIGHT;

		// 为敌方坦克随机指定方向并开火
		if (!good) {
			Direction[] dirs = Direction.values();
			if (step == 0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;
			if (r.nextInt(40) > 40 - SHOOT_FREQ) {
				this.fire();
			}
		}
		
	}
	
	/**
	 * 返回前一步位置
	 */
	public void stay(){
		x = oldX;
		y = oldY;
	}

	/**
	 * 按键事件，用四个布尔量记录按键状态
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		//F2复活
		case KeyEvent.VK_F2:
			this.life = 100;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		
		//根据按键状态确定方向
		locateDirection();
	}

	/**
	 * 松开键盘事件
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_SPACE:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_CONTROL:
			ultraFire();
			break;
		}
		
		//根据键盘状态重定位方向
		locateDirection();
	}

	/*
	 * 定方向函数
	 */
	void locateDirection() {
		if (bL && !bR && !bU && !bD)
			dir = Direction.L;
		else if (!bL && bR && !bU && !bD)
			dir = Direction.R;
		else if (!bL && !bR && bU && !bD)
			dir = Direction.U;
		else if (!bL && !bR && !bU && bD)
			dir = Direction.D;
		else if (bL && !bR && bU && !bD)
			dir = Direction.LU;
		else if (bL && !bR && !bU && bD)
			dir = Direction.LD;
		else if (!bL && bR && bU && !bD)
			dir = Direction.RU;
		else if (!bL && bR && !bU && bD)
			dir = Direction.RD;
		else
			dir = Direction.STOP;
	}

	/**
	 * 开火方法一，根据坦克开火方向生成子弹对象
	 * @return 返回生成的子弹对象
	 */
	public Missile fire() {
		if(!live) return null;
		int x = this.x + WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, good, fireDir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	/**
	 * 开火方法二，自定义方向生成子弹对象
	 * @param dir 方向参数
	 * @return 返回生成的子弹对象
	 */
	public Missile fire(Direction dir){
		if(!live) return null;
		int x = this.x + WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, good, dir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	/**
	 * 大招：一次向8个方向发射8枚子弹
	 */
	public void ultraFire(){
		//把枚举类转成数组
		Direction[] dirs = Direction.values();
		for(int i=0;i<8;i++){
			fire(dirs[i]);
		}
	}
	
	/**
	 * 吃血包恢复到满血
	 * @param b 血包对象
	 * @return 吃血包成功则返回true
	 */
	public boolean eat(Blood b){
		if(this.live && b.isLive() && this.getRect().intersects(b.getRect())){
			this.life = 100;
			b.setLive(false);
			return true;
		}
		return false;
	}

	/**
	 * 生成坦克对象的碰撞矩形，为碰撞检测做准备
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	/**
	 * 判断坦克和墙相撞，并使坦克回到上一步的位置
	 * @param w 被撞的墙
	 * @return	撞上后返回true
	 */
	public boolean collidesWithWall(Wall w){
		if(this.live && this.getRect().intersects(w.getRect())){
			this.stay();
			return true;
		}
		return false;
	}
	
	/**
	 * 判断坦克和坦克相撞，回到上一步位置
	 * @param tanks 装敌方坦克的容器List
	 * @return 相撞并退回则返回true
	 */
	public boolean collidesWithTanks(List<Tank> tanks){
		for(int i=0; i<tanks.size(); i++){
			Tank t = tanks.get(i);
			if(this!=t){
				if(this.live && t.isLive() && this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 内部类血包
	 * @author zhjl
	 */
	private class BloodBar{
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 10);
			int w = WIDTH*life/100;
			g.fillRect(x,y-10,w,10);
			g.setColor(c);
		}
	}
}

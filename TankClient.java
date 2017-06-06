package tankwar.entity;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.JOptionPane;

import java.util.ArrayList;

/**
 * 坦克大战的主窗口
 * 
 * @author nbztx
 *
 */
public class TankClient extends Frame {

	/**
	 * 生成序列号
	 */
	private static final long serialVersionUID = -7976706717154030334L;
	/**
	 * 坦克游戏界面的宽度
	 */
	public static final int WIDTH = 800;
	/**
	 * 坦克游戏界面的高度
	 */
	public static final int HEIGHT = 600;
	/**
	 * 坦克游戏界面的刷新间隔，以毫秒计
	 */
	public static final int GAP = 50;
	/**
	 * 用每波增加的坦克数量代表难度
	 */
	public static int difficulty = 1;

	// 生成墙
	Wall w1 = new Wall(100, 250, 20, 150, this);
	Wall w2 = new Wall(300, 300, 150, 20, this);
	Wall w3 = new Wall(600, 250, 20, 150, this);

	// 创建我方坦克、血包、爆炸、子弹、敌方坦克对象
	Tank myTank = new Tank(600, 450, true, Tank.Direction.STOP, this);
	Blood b = new Blood();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();

	// 创建下一帧画面的引用
	Image offScreenImage = null;

	/**
	 * 每次刷新Frame时自动调用的paint()方法
	 */
	public void paint(Graphics g) {

		// 数据显示栏
		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("tanks count:" + tanks.size(), 10, 70);
		g.drawString("fps:" + 1000 / GAP, 750, 50);
		g.drawString("tank's life:" + myTank.getLife(), 10, 90);
		g.drawString("Stage:" + TankClient.difficulty, 10, 110);

		// 一波坦克死亡后，重新生成下一波坦克
		if (tanks.size() <= 0) {
			TankClient.difficulty += 1;
			int j = 10 + 2 * (TankClient.difficulty - 1);
			for (int i = 0; i < j; i++) {
				if(i<16)
					tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Tank.Direction.D, this));
				else if(i<32)
					tanks.add(new Tank(50 + 40 * (i - 15), 100, false, Tank.Direction.D, this));
				else
					tanks.add(new Tank(50 + 40 * (i - 31), 150, false, Tank.Direction.D, this));
			}
			b.setLive(true);
			b.draw(g);
		}

		// 画子弹
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}

		// 画敌方坦克
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}

		// 画爆炸效果
		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}

		// 画我方坦克
		myTank.collidesWithWall(w1);
		myTank.collidesWithWall(w2);
		myTank.collidesWithTanks(tanks);
		myTank.draw(g);
		myTank.eat(b);

		// 画墙和血包
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		b.draw(g);
	}

	/**
	 * 重写update()方法画背景，update()会自动调用paint()方法画组件
	 * 双缓冲避免频繁闪烁现象，之所以会闪烁是因为paint()还没完成已经刷新
	 */
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(WIDTH, HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.LIGHT_GRAY);
		gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	/**
	 * 显示坦克主窗口
	 */
	public void launchFrame() {

		// 根据难度等级初始化敌方坦克
		int j=10+2*(TankClient.difficulty-1);
		switch(j){
		case 10:
			for (int i = 0; i < 10; i++) {
				tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Tank.Direction.D, this));
			}	
			break;
		case 18:
			for (int i = 0; i < 16; i++) {
				if(i<16)
					tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Tank.Direction.D, this));
				else
					tanks.add(new Tank(50 + 40 * (i - 15), 100, false, Tank.Direction.D, this));
			}	
			break;
		case 28:
			for (int i = 0; i < 28; i++) {
				if(i<16)
					tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Tank.Direction.D, this));
				else
					tanks.add(new Tank(50 + 40 * (i - 15), 100, false, Tank.Direction.D, this));
			}
			break;
		case 38:
			for (int i = 0; i < 38; i++) {
				if(i<16)
					tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Tank.Direction.D, this));
				else if(i<32)
					tanks.add(new Tank(50 + 40 * (i - 15), 100, false, Tank.Direction.D, this));
				else
					tanks.add(new Tank(50 + 40 * (i - 31), 150, false, Tank.Direction.D, this));
			}
			break;
		default:
			break;
		}

		// 设置窗口参数
		this.setLocation(400, 300);
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("TankWar");

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);
		this.addKeyListener(new KeyMoniter());
		this.setVisible(true);

		// 通过Thread类来启动Runnable线程
		new Thread(new PaintThread()).start();
	}

	/**
	 * main()函数
	 * 
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankClient tc = new TankClient();
		tc.launchFrame();
	}*/

	/**
	 * 用线程重画，间隔GAP
	 * 
	 * @author zhjl
	 */
	private class PaintThread implements Runnable {
		public void run() {
			while (myTank.isLive() && difficulty<20) {
				repaint();
				try {
					Thread.sleep(GAP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(myTank.isLive())
				JOptionPane.showMessageDialog(null, "You are the Legend!");
			else
				JOptionPane.showMessageDialog(null, "You're dead at Stage:"+difficulty);
		}
	}

	/**
	 * 键盘监听
	 * 
	 * @author zhjl
	 */
	private class KeyMoniter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

	}
}

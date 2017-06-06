package tankwar.entity;

import java.awt.*;

public class Blood {
	int x, y, w, h;
	TankClient tc;
	int step = 0;

	private boolean live = true;

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private int[][] position = { { 350, 320 }, { 380, 320 }, { 400, 270 }, { 380, 220 }, { 350, 220 }, { 330, 270 } };

	public Blood() {
		x = position[0][0];
		y = position[0][1];
		w = 15;
		h = 15;
	}

	public void draw(Graphics g) {
		if(!live) return;
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		move();
	}

	private void move() {
		step++;
		if (step == position.length) {
			step = 0;
		}
		x = position[step][0];
		y = position[step][1];
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}

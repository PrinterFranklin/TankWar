package tankwar.entity;

import java.awt.*;

public class Explode {
	private int x, y;
	private boolean live = true;
	private TankClient tc;

	int[] diameter = { 4, 8, 14, 22, 32, 44, 32, 22, 14, 8, 4 };
	int step = 0;

	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live) {
			tc.explodes.remove(this);
			return;
		}
		if (step == diameter.length) {
			live = false;
			step = 0;
			return;
		}

		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);

		step++;
	}
}

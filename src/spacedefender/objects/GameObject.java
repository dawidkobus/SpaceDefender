package spacedefender.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class GameObject {
	protected int damage;
	protected boolean alive;

	protected abstract float getX1();

	protected abstract float getX2();

	protected abstract float getY1();

	protected abstract float getY2();

	protected abstract void destroy();

	protected int getDamage() {
		return damage;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean collidesWith(GameObject otherGameObject) {
		if (getY2() < otherGameObject.getY1()) {
			return false;
		}
		if (getX2() < otherGameObject.getX1()) {
			return false;
		}
		if (getY1() > otherGameObject.getY2()) {
			return false;
		}
		if (getX1() > otherGameObject.getX2()) {
			return false;
		}
		return true;
	}

	protected void showHitboxes(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
	}
}

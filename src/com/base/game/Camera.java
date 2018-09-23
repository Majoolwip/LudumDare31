package com.base.game;

import com.base.engine.GameContainer;
import com.base.engine.Vector2f;

public class Camera
{
	private GameObject target;
	private Vector2f pos = new Vector2f(0, 0);

	public Camera(GameObject target)
	{
		this.target = target;
	}

	public void update(GameContainer gc)
	{
		if (!target.isDead())
		{
			pos = target
					.getTilePos()
					.mul(new Vector2f(Level.TS, Level.TS))
					.add(target.getOffset())
					.add(new Vector2f(-gc.getWidth() / 2f, -gc.getHeight() / 2f))
					.reflect();

			pos.setY(0);

			if (pos.getX() > 0)
			{
				pos.setX(0);
			}
			
			if (pos.getX() < -Level.TS * 175)
			{
				pos.setX(-Level.TS * 175);
			}
		}
		else
		{
			if(pos.getX() > -160)
			{
				pos.setX(-160);
			}
		}
	}

	public GameObject getTarget()
	{
		return target;
	}

	public void setTarget(GameObject target)
	{
		this.target = target;
	}

	public Vector2f getPos()
	{
		return pos;
	}

	public void setPos(Vector2f pos)
	{
		this.pos = pos;
	}
}

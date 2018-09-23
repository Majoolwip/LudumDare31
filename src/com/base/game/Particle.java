package com.base.game;

import com.base.engine.GameContainer;
import com.base.engine.Renderer;
import com.base.engine.Vector2f;

public class Particle extends GameObject
{
	private float dX, dY;
	
	private float speed = 50f;
	private float cd = 0.2f;
	
	int color = 0;
	
	private float fallDistance = 0;
	
	public Particle(Vector2f tilePos, Vector2f offset, int color, float cd)
	{
		super.tilePos = new Vector2f(tilePos.getX(), tilePos.getY());
		super.offset = new Vector2f(offset.getX(), offset.getY());
		
		this.color = color;
		this.cd = cd;
		
		dX = (float) ((Math.random() * 100) - 50);
		dY = (float) ((Math.random() * 100) - 50);
	}

	@Override
	public void update(GameContainer gc, float delta, Level level)
	{
		offset.setX(offset.getX() + delta * dX);
		offset.setY(offset.getY() + delta * dY);
		
		fallDistance += delta * 5f;
		
		offset.setY(offset.getY() + fallDistance);
		
		if(offset.getY() > Level.TS)
		{
			offset.setY(0);
			tilePos.setY(tilePos.getY() + 1);
		}
		
		if(offset.getY() < 0)
		{
			offset.setY(Level.TS);
			tilePos.setY(tilePos.getY() - 1);
		}
		
		if(offset.getX() > Level.TS)
		{
			offset.setX(0);
			tilePos.setX(tilePos.getX() + 1);
		}
		
		if(offset.getX() < 0)
		{
			offset.setX(Level.TS);
			tilePos.setX(tilePos.getX() - 1);
		}
		
		cd -= delta;
		
		if(cd <= 0)
			setDead(true);
	}

	@Override
	public void render(GameContainer gc, Renderer r, Level level)
	{
		r.drawPixel((int)(tilePos.getX() * Level.TS + offset.getX()), (int)(tilePos.getY() * Level.TS + offset.getY()), color, 0);
		r.drawPixel((int)(tilePos.getX() * Level.TS + offset.getX()) + 1, (int)(tilePos.getY() * Level.TS + offset.getY()), color, 0);
		r.drawPixel((int)(tilePos.getX() * Level.TS + offset.getX()), (int)(tilePos.getY() * Level.TS + offset.getY() + 1), color, 0);
		r.drawPixel((int)(tilePos.getX() * Level.TS + offset.getX() + 1), (int)(tilePos.getY() * Level.TS + offset.getY() + 1), color, 0);

	}

	@Override
	public void collide(GameObject go)
	{
		// TODO Auto-generated method stub
		
	}

}

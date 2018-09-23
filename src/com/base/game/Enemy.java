package com.base.game;

import com.base.engine.AudioPlayer;
import com.base.engine.GameContainer;
import com.base.engine.ImageTile;
import com.base.engine.Light;
import com.base.engine.Renderer;
import com.base.engine.Vector2f;

public class Enemy extends GameObject
{
	private ImageTile enemy = new ImageTile("/images/enemy.png",Level.TS,Level.TS);
	
	private float cd = 1;
	
	private AudioPlayer shoot = new AudioPlayer("/sound/enemyshoot.wav");
	private AudioPlayer dead = new AudioPlayer("/sound/enemydead.wav");
	
	public Enemy(int x, int y)
	{
		super.setTilePos(new Vector2f(x,y));
		tag = "enemy";
		enemy.lb = 3;
		
		cd = (float) Math.random();
	}
	
	float increment = 0;

	@Override
	public void update(GameContainer gc, float delta, Level level)
	{
		increment += delta * 2;
		
		offset.setX(offset.getX() + delta * (float)(Math.sin(increment) * 25));
		offset.setY(offset.getY() + delta * (float)(Math.cos(increment) * 25));
		
		if(offset.getY() > Level.TS / 2)
		{
			offset.setY(Level.TS / -2);
			tilePos.setY(tilePos.getY() + 1);
		}
		
		if(offset.getY() < -Level.TS / 2)
		{
			offset.setY(Level.TS / 2);
			tilePos.setY(tilePos.getY() - 1);
		}
		
		if(offset.getX() > Level.TS / 2)
		{
			offset.setX(Level.TS / -2);
			tilePos.setX(tilePos.getX() + 1);
		}
		
		if(offset.getX() < -Level.TS / 2)
		{
			offset.setX(Level.TS / 2);
			tilePos.setX(tilePos.getX() - 1);
		}
		
		cd -= delta;
		
		if(cd <= 0)
		{
			GameObject target = level.getObject("player");
			cd = 1;
			
			if(target != null && Math.abs(target.getTilePos().getX() - tilePos.getX()) < 10)
			{
				level.addObject(new EBullet((int)(tilePos.getX() * Level.TS + (offset.getX() + 4)), (int)(tilePos.getY() * Level.TS + (offset.getY() + 4)), target));
				shoot.play();
			}
		}
		
		Physics.addObject(this);
	}

	@Override
	public void render(GameContainer gc, Renderer r, Level level)
	{
		r.drawImageTile(enemy, 0, 0, (int)(tilePos.getX() * Level.TS + offset.getX()), (int)(tilePos.getY() * Level.TS + offset.getY()));
	}

	@Override
	public void collide(GameObject go)
	{
		if(go.getTag().equals("pBullet"))
		{
			dead.play();
			setDead(true);
		}
	}

}

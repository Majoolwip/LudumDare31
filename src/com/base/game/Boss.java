package com.base.game;

import com.base.engine.AudioPlayer;
import com.base.engine.GameContainer;
import com.base.engine.Image;
import com.base.engine.Renderer;
import com.base.engine.Vector2f;

public class Boss extends GameObject
{

	private Image image = new Image("/images/boss.png");
	
	int lives = 25;

	float cd = 1;
	
	private AudioPlayer hurt = new AudioPlayer("/sound/bossHurt.wav");
	
	public Boss(int x, int y)
	{
		tilePos = new Vector2f(x,y);
		tag = "boss";
	}
	
	float temp = 0;
	
	@Override
	public void update(GameContainer gc, float delta, Level level)
	{
		temp += delta;
		offset.setX(offset.getX() + delta * (float)(Math.cos(temp) * 50));
		offset.setY(offset.getY() + delta * (float)(Math.sin(temp * 2) * 10));
		
		if(offset.getY() > Level.TS / 2)
		{
			offset.setY( offset.getY() - Level.TS);
			tilePos.setY(tilePos.getY() + 1);
		}
		
		if(offset.getY() < -Level.TS / 2)
		{
			offset.setY(offset.getY() + Level.TS);
			tilePos.setY(tilePos.getY() - 1);
		}
		
		if(offset.getX() > Level.TS / 2)
		{
			offset.setX(offset.getX() - Level.TS);
			tilePos.setX(tilePos.getX() + 1);
		}
		
		if(offset.getX() < -Level.TS / 2)
		{
			offset.setX(offset.getX() + Level.TS);
			tilePos.setX(tilePos.getX() - 1);
		}
		
		cd -= delta;
		
		if(cd < 0)
		{
			cd = 2.0f;
			level.addObject(new BBullet(tilePos, offset.add(new Vector2f(4,4)), 50,0));
			level.addObject(new BBullet(tilePos, offset.add(new Vector2f(4,4)), -50,0));
			level.addObject(new BBullet(tilePos, offset.add(new Vector2f(4,4)), 0,50));
			level.addObject(new BBullet(tilePos, offset.add(new Vector2f(4,4)), 0,-50));
			level.addObject(new BBullet(tilePos, offset.add(new Vector2f(4,4)), 35,35));
			level.addObject(new BBullet(tilePos, offset.add(new Vector2f(4,4)), -35,35));
			level.addObject(new BBullet(tilePos, offset.add(new Vector2f(4,4)), -35,-35));
			level.addObject(new BBullet(tilePos, offset.add(new Vector2f(4,4)), 35,-35));
		}
		
		Physics.addObject(this);
	}

	@Override
	public void render(GameContainer gc, Renderer r, Level level)
	{
		r.drawImage(image, (int)(tilePos.getX() * Level.TS + offset.getX()) - Level.TS, (int)(tilePos.getY() * Level.TS + offset.getY()) - Level.TS);
	}

	@Override
	public void collide(GameObject go)
	{
		if(go.getTag().equals("pBullet"))
		{
			lives -= 1;
			
			hurt.play();
			
			if(lives <= 0)
			{
				setDead(true);
			}
		}
	}

}

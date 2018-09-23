package com.base.game;

import com.base.engine.GameContainer;
import com.base.engine.Image;
import com.base.engine.Renderer;
import com.base.engine.Vector2f;

public class BBullet extends GameObject
{
	private static final Image image = new Image("/images/eBullet.png");
	
	int dX, dY;
	
	public BBullet(Vector2f tilePos, Vector2f offset, int dX, int dY)
	{
		super.tilePos = new Vector2f(tilePos.getX(), tilePos.getY());
		super.offset = new Vector2f(offset.getX(), offset.getY());
		this.dX = dX;
		this.dY = dY;
		tag = "eBullet";
	}

	@Override
	public void update(GameContainer gc, float delta, Level level)
	{
		offset.setX(offset.getX() + delta * dX);
		offset.setY(offset.getY() + delta * dY);
		
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
		
		Physics.addObject(this);
		
		if(level.getTile((int)tilePos.getX(), (int)tilePos.getY()) == 1)
		{
			setDead(true);

			for(int i = 0; i < 5; i++)
			{
				level.addObject(new Particle(tilePos, offset, 0xffffffff, 0.2f));
			}
			
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r, Level level)
	{
		if(tilePos.getX() * Level.TS + r.getTranslate().getX() > gc.getWidth())
		{
			setDead(true);
			return;
		}
		r.drawImage(image, (int)(tilePos.getX() * Level.TS + offset.getX()), (int)(tilePos.getY() * Level.TS + offset.getY()));
	}

	@Override
	public void collide(GameObject go)
	{
		if(go.getTag().equals("player"))
		{
			setDead(true);
		}
	}
}

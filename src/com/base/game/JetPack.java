package com.base.game;

import com.base.engine.GameContainer;
import com.base.engine.Image;
import com.base.engine.Renderer;
import com.base.engine.Vector2f;

public class JetPack extends GameObject
{
	private Image image = new Image("/images/jetPack.png");
	
	public JetPack(int x, int y)
	{
		super.tilePos = new Vector2f(x,y);
		super.offset = new Vector2f(0,0);
		tag = "jetpack";
	}
	
	@Override
	public void update(GameContainer gc, float delta, Level level)
	{
		Physics.addObject(this);
	}

	@Override
	public void render(GameContainer gc, Renderer r, Level level)
	{
		r.drawImage(image, (int)(tilePos.getX() * Level.TS + offset.getX()),(int)(tilePos.getY() * Level.TS + offset.getY()));
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

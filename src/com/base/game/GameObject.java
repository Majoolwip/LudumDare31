package com.base.game;

import com.base.engine.GameContainer;
import com.base.engine.Renderer;
import com.base.engine.Vector2f;

public abstract  class GameObject
{
	protected Vector2f tilePos = new Vector2f(0,0);
	protected Vector2f offset = new Vector2f(0,0);
	
	protected String tag = "null";
	private boolean dead = false;
	
	public abstract void update(GameContainer gc, float delta, Level level);
	public abstract void render(GameContainer gc, Renderer r, Level level);
	public abstract void collide(GameObject go);
	
	public Vector2f getTilePos()
	{
		return tilePos;
	}
	public void setTilePos(Vector2f tilePos)
	{
		this.tilePos = tilePos;
	}
	public Vector2f getOffset()
	{
		return offset;
	}
	public void setOffset(Vector2f offset)
	{
		this.offset = offset;
	}
	public boolean isDead()
	{
		return dead;
	}
	public void setDead(boolean dead)
	{
		this.dead = dead;
	}
	public String getTag()
	{
		return tag;
	}
	public void setTag(String tag)
	{
		this.tag = tag;
	}
	
}

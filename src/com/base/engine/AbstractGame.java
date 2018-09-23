package com.base.engine;

public abstract class AbstractGame
{
	public abstract void update(GameContainer gc, float delta);
	public abstract void render(GameContainer gc, Renderer r);
}

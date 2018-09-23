package com.base.game;

import java.awt.event.KeyEvent;

import com.base.engine.AbstractGame;
import com.base.engine.AudioPlayer;
import com.base.engine.GameContainer;
import com.base.engine.Input;
import com.base.engine.Pixel;
import com.base.engine.Renderer;

public class Game extends AbstractGame
{
	private Level level;

	public Game()
	{
		level = new Level();
	}
	
	@Override
	public void update(GameContainer gc, float delta)
	{
		if(Input.isKeyDown(KeyEvent.VK_NUMPAD9))
		{
			gc.getRenderer().setAmbientLight(Pixel.getLightSum(gc.getRenderer().getAmbientLight(), 0.05f));
		}
		if(Input.isKeyDown(KeyEvent.VK_NUMPAD3))
		{
			gc.getRenderer().setAmbientLight(Pixel.getLightSum(gc.getRenderer().getAmbientLight(), -0.05f));
		}
		
		if(Input.isKeyDown(KeyEvent.VK_NUMPAD7))
		{
			gc.setScale(gc.getScale() + 0.5f);
			gc.resize();
			System.out.println("hey");
		}
		if(Input.isKeyDown(KeyEvent.VK_NUMPAD1))
		{
			gc.setScale(gc.getScale() - 0.5f);
			gc.resize();
		}
		
		
		level.update(gc, delta);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		level.render(gc, r);
	}

	public static void main(String args[])
	{
		GameContainer gc = new GameContainer(new Game());
		gc.setWidth(160);
		gc.setHeight(96);
		gc.setScale(5f);
		gc.start();
	}
	
}

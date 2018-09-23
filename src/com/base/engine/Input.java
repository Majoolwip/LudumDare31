package com.base.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener
{
	private GameContainer gc;
	
	private static final int NUM_KEYS = 256;
	private static boolean[] keys = new boolean[NUM_KEYS];
	private static boolean[] keysLast = new boolean[NUM_KEYS];
	private static final int NUM_BUTTONS = 5;
	private static boolean[] buttons = new boolean[NUM_KEYS];
	private static boolean[] buttonsLast = new boolean[NUM_KEYS];

	private Vector2f mousePos = new Vector2f(0,0);
	
	public Input(GameContainer gc)
	{
		this.gc = gc;
		
		gc.getWindow().getCanvas().addKeyListener(this);
		gc.getWindow().getCanvas().addMouseListener(this);
		gc.getWindow().getCanvas().addMouseMotionListener(this);
	}
	
	public static void update()
	{
		keysLast = keys.clone();
		buttonsLast = buttons.clone();
	}
	
	public static boolean isKey(int keyCode)
	{
		return keys[keyCode];
	}
	
	public static boolean isKeyDown(int keyCode)
	{
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	public static boolean isKeyUp(int keyCode)
	{
		return !keys[keyCode] && keysLast[keyCode];
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		mousePos.setX(e.getX() / gc.getScale());
		mousePos.setY(e.getY() / gc.getScale());
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mousePos.setX(e.getX() / gc.getScale());
		mousePos.setY(e.getY() / gc.getScale());
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		buttons[e.getButton()] = false;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
	
}

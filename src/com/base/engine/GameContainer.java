package com.base.engine;

public class GameContainer implements Runnable
{
	private Thread thread;
	private AbstractGame game;
	private Window window;
	private Renderer renderer;
	private Input input;
	
	private int width = 320, height = 240;
	private float scale = 2.5f;
	private String title = "LudumDare";
	private volatile boolean isRunning = false;
	private double frameCap = 1.0 / 60.0;
	
	public GameContainer(AbstractGame game)
	{
		this.game = game;
	}
	
	public void start()
	{
		if(isRunning)
			return;
		
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop()
	{
		if(!isRunning)
			return;
		
		isRunning = false;
	}
	
	public void run()
	{
		isRunning = true;
		
		double firstTime = 0;
		double lastTime = Time.getTime();
		double passedTime = 0;
		
		double unprocessedTime = 0;
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while(isRunning)
		{
			boolean render = true;
			
			firstTime = Time.getTime();
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while(unprocessedTime >= frameCap)
			{
				render = true;
				unprocessedTime -= frameCap;
				
				game.update(this, (float) frameCap);
				Input.update();
	
				if(frameTime >= 1)
				{
					frameTime = 0;
					fps = frames;
					frames = 0;
					System.out.println(fps);
				}
			}
			
			if(render)
			{
				renderer.clear();
				game.render(this, renderer);
				renderer.overlayLight();
				window.update();
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		cleanUp();
	}
	
	public void resize()
	{
		window.resize(this);
		renderer = new Renderer(this);
		input = new Input(this);
	}
	
	private void cleanUp()
	{
		window.dispose();
	}

	public Window getWindow()
	{
		return window;
	}

	public void setWindow(Window window)
	{
		this.window = window;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public float getScale()
	{
		return scale;
	}

	public void setScale(float scale)
	{
		this.scale = scale;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Renderer getRenderer()
	{
		return renderer;
	}

	public void setRenderer(Renderer renderer)
	{
		this.renderer = renderer;
	}
}

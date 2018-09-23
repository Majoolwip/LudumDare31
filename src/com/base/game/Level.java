package com.base.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.base.engine.AudioPlayer;
import com.base.engine.GameContainer;
import com.base.engine.Image;
import com.base.engine.ImageTile;
import com.base.engine.Input;
import com.base.engine.Light;
import com.base.engine.LightBox;
import com.base.engine.Renderer;

public class Level
{
	public static final int TS = 8;
	
	private final int levelW = 200;
	private final int levelH = 12;
	private int[] tiles = new int[levelW * levelH];
	
	private Camera camera;
	
	private ImageTile tileSheet = new ImageTile("/images/tileSheet.png", 8, 8);
	private Image deadScreen = new Image("/images/deadScreen.png");
	private Image startScreen = new Image("/images/startScreen.png");
	private Image winScreen = new Image("/images/winScreen.png");
	private Image optionScreen = new Image("/images/optionMenu.png");
	private LightBox lightBox = new LightBox(100, 0xffff6600);
	
	private ArrayList<GameObject> go = new ArrayList<GameObject>();
	private ArrayList<Light> lights = new ArrayList<Light>();
	
	private AudioPlayer music = new AudioPlayer("/sound/music.wav");
	
	private Player player;
	private Boss boss;
	
	public Level()
	{
		loadLevel("/images/tileData.png");
		player = (Player) getObject("player");
		camera = new Camera(player);
		boss = (Boss) getObject("boss");
		music.loop();
	}
	
	public void update(GameContainer gc, float delta)
	{
		if(player.isDead())
		{
			camera.getPos().setX(camera.getPos().getX() + delta * 50);
			
			if(Input.isKeyDown(KeyEvent.VK_SPACE))
			{
				go.clear();
				resetLevel();
				player = (Player) getObject("player");
				boss = (Boss) getObject("boss");
				camera = new Camera(player);
			}
		}
		
		Physics.update();
		
		for(int i = 0; i < go.size(); i++)
		{
			if(go.get(i).isDead())
			{
				go.remove(i);
				continue;
			}
			
			go.get(i).update(gc, delta, this);
		}
		
		camera.update(gc);
	}

	public void render(GameContainer gc, Renderer r)
	{
		r.setTranslate(camera.getPos().getX(), camera.getPos().getY());
		
		for(int x = (int) (-camera.getPos().getX() / Level.TS); x < (int) (-camera.getPos().getX() / Level.TS) + gc.getWidth() / Level.TS + 1; x++)
		{
			for(int y = (int) (-camera.getPos().getY() / Level.TS); y < (int) (-camera.getPos().getY() / Level.TS) + gc.getHeight() / Level.TS; y++)
			{
				if(x >= levelW)
					return;
				if(tiles[x + y * levelW] == 1)
				{
					tileSheet.lb = 2;
					r.drawImageTile(tileSheet, 1, 0, x * tileSheet.tW, y * tileSheet.tH);
				}
				else if(tiles[x + y * levelW] == 0)
				{
					tileSheet.lb = 0;
					r.drawImageTile(tileSheet, 0, 0, x * tileSheet.tW, y * tileSheet.tH);
				}
				else
				{
					tileSheet.lb = 0;
					r.drawImageTile(tileSheet, 3, 0, x * tileSheet.tW, y * tileSheet.tH);
				}
			}
		}
		
		r.drawImage(startScreen, 160, 0);
		r.drawImage(optionScreen, 0, 0);
		
		for(int i = 0; i < go.size(); i++)
		{
			go.get(i).render(gc, r, this);
		}
		
		for(int x = 0; x < levelW; x++)
		{
			for(int y = 0; y < levelH; y++)
			{
				if(tiles[x + y * levelW] == 2)
				{
					tileSheet.lb = 0;
					r.drawImageTile(tileSheet, 2, 0, x * tileSheet.tW, y * tileSheet.tH);
					for(int i = 0; i < Level.TS; i++)
					{
						r.drawLightBox(lightBox, (x * Level.TS) + i, (y + 1) * Level.TS);
					}
				}
			}
		}
		
		for(int i = 0; i < lights.size(); i++)
		{
			Light l = lights.get(i);
			
			if(l.x * Level.TS + camera.getPos().getX() >= 0 || l.x * Level.TS + camera.getPos().getX() < gc.getWidth())
			r.drawLight(lights.get(i), lights.get(i).x * Level.TS + 4, lights.get(i).y * Level.TS + 4);
		}
		
		if(player.isDead() && !boss.isDead())
		{
			r.drawImage(deadScreen, (int)-camera.getPos().getX(), (int)-camera.getPos().getY());
		}
		
		if(boss.isDead())
		{
			r.drawImage(winScreen, (int)-camera.getPos().getX(), (int)-camera.getPos().getY());
		}
	}
	
	public int getTile(int x, int y)
	{
		if(x < 0 || x >= levelW || y < 0 || y >= levelH)
			return 1;
		
		return tiles[x + y * levelW];
	}
	
	public void addObject(GameObject gameObject)
	{
		go.add(gameObject);
	}
	
	public void loadLevel(String path)
	{
		Image image = new Image(path);
		
		for(int x = 0; x < image.w; x++)
		{
			for(int y = 0; y < image.h; y++)
			{
				if(image.p[x + y * image.w] == 0xff000000)
				{
					tiles[x + y * image.w] = 1;
				}
				else if(image.p[x + y * image.w] == 0xffff0000)
				{
					tiles[x + y * image.w] = 2;
				}
				else if(image.p[x + y * image.w] == 0xff00ff00)
				{
					go.add(new Player(x,y));
				}
				else if(image.p[x + y * image.w] == 0xffff00ff)
				{
					go.add(new Enemy(x,y));
				}
				else if(image.p[x + y * image.w] == 0xffffffff)
				{
					tiles[x + y * image.w] = 0;
				}
				else if(image.p[x + y * image.w] == 0xff666666)
				{
					tiles[x + y * image.w] = 3;
				}
				else if(image.p[x + y * image.w] == 0xff0000ff)
				{
					go.add(new JetPack(x,y));
				}
				else if(image.p[x + y * image.w] == 0xff00ffff)
				{
					go.add(new Boss(x,y));
				}
				else
				{
					lights.add(new Light(image.p[x + y * image.w], 50, x, y));
				}
			}
		}
	}
	
	public GameObject getObject(String tag)
	{
		for(int i = 0; i < go.size(); i++)
		{
			if(go.get(i).getTag().equals(tag))
				return go.get(i);
		}
		
		return null;
	}
	
	public void resetLevel()
	{
		Image image = new Image("/images/tileData.png");
		
		for(int x = 0; x < image.w; x++)
		{
			for(int y = 0; y < image.h; y++)
			{
				if(image.p[x + y * image.w] == 0xff00ff00)
				{
					go.add(new Player(x,y));
				}
				else if(image.p[x + y * image.w] == 0xffff00ff)
				{
					go.add(new Enemy(x,y));
				}
				else if(image.p[x + y * image.w] == 0xff0000ff)
				{
					go.add(new JetPack(x,y));
				}
				else if(image.p[x + y * image.w] == 0xff00ffff)
				{
					go.add(new Boss(x,y));
				}
			}
		}
	}
}

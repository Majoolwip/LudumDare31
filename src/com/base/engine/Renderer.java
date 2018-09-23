package com.base.engine;

import java.awt.image.DataBufferInt;

public class Renderer
{
	private int w, h;
	private int[] p;
	private int[] lm;
	private int[] lb;
	
	private int ambientLight = Pixel.getColor(0.1f, 0.1f, 0.1f);
	
	private Vector2f translate = new Vector2f(0,0);
	
	public Renderer(GameContainer gc)
	{
		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		lm = new int[p.length];
		lb = new int[p.length];
		w = gc.getWidth();
		h = gc.getHeight();
	}
	
	public void clear()
	{
		for(int i = 0; i < p.length; i++)
		{
			p[i] = 0;
			lm[i] = ambientLight;
		}
	}
	
	public void overlayLight()
	{
		for(int i = 0; i < p.length; i++)
		{
			p[i] = Pixel.getLightOverlay(p[i], lm[i]);
		}
	}
	
	public void drawPixel(int x, int y, int value, int lbv)
	{
		x += (int)translate.getX();
		y += (int)translate.getY();
		
		if(x < 0 || x >= w || y < 0 || y >= h || value == 0xffff00ff)
			return;
		
		p[x + y * w] = value;
		lb[x + y * w] = lbv;
	}
	
	public void drawLightMap(int x, int y, int value)
	{
		x += (int)translate.getX();
		y += (int)translate.getY();
		
		if(x < 0 || x >= w || y < 0 || y >= h)
			return;
		
		lm[x + y * w] = Pixel.getColorBlend(lm[x + y * w], value);
	}
	
	public void drawImage(Image image, int offX, int offY)
	{
		for(int x = 0; x < image.w; x++)
		{
			for(int y = 0; y < image.h; y++)
			{
				drawPixel(x + offX,y + offY,image.p[x + y * image.w], image.lb);
			}
		}
	}
	
	public void drawImage(Image image, int offX, int offY, boolean glow)
	{
		for(int x = 0; x < image.w; x++)
		{
			for(int y = 0; y < image.h; y++)
			{
				drawPixel(x + offX,y + offY,image.p[x + y * image.w], image.lb);
				if(glow && image.p[x + y * image.w] != 0xffff00ff)
				{
					drawLightMap(x + offX, y + offY, 0xffffffff);
				}
			}
		}
	}
	
	public void drawImageTile(ImageTile image, int tileX, int tileY, int offX, int offY)
	{
		for(int x = 0; x < image.tW; x++)
		{
			for(int y = 0; y < image.tH; y++)
			{
				drawPixel(x + offX,y + offY,image.p[(x + tileX * image.tW) + (y + tileY * image.tH) * image.w], image.lb);
			}
		}
	}
	
	public void drawLight(Light light, int offX, int offY)
	{
		offX -= light.radius;
		offY -= light.radius;
		
		/*
		if((light.radius + offX) + translate.getX() >= w || (light.radius) + translate.getX() + offX < 0)
			return;
		*/
		for(int i = 0; i <= light.diameter; i++)
		{	
			drawLightLine(light.radius + offX, light.radius + offY, i + offX, 0 + offY, light);
			drawLightLine(light.radius + offX, light.radius + offY, i + offX, light.diameter + offY, light);
			
			drawLightLine(light.radius + offX, light.radius + offY, 0 + offX, i + offY, light);
			drawLightLine(light.radius + offX, light.radius + offY, light.diameter + offX, i + offY, light);
		}
	}
	
	public void drawLightBox(LightBox lightBox, int x, int y)
	{	
		float halfLife = 1.0f;
		boolean halfLifeB = false;
		
		if(x + translate.getX() >= w || x + translate.getX() < 0)
			return;
		
		for(int i = 0; i < lightBox.height; i++)
		{
			if(getLightBlock(x,y - i) == 2)
			{
				halfLife -= 0.1f;
			}
			
			if(getLightBlock(x,y - i) == 3)
			{
				halfLifeB = true;
			}
			
			if(getLightBlock(x,y - i) != 3 && halfLifeB)
			{
				halfLife /= 2;
				halfLifeB = false;
			}
			
			drawLightMap(x, y - i, Pixel.getLightPower(lightBox.lm[i], halfLife));
			
			if(getLightBlock(x,y - i) == 1)
			{
				return;
			}
		}
	}
	
	private void drawLightLine(int x0, int y0, int x1, int y1, Light light)
	{
		
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		int sx = 0;
		int sy = 0;
		int x = x0 - light.radius;
		int y = y0 - light.radius;
		if (x0 < x1)
		{
			sx = 1;
		} else
		{
			sx = -1;
		}
		if (y0 < y1)
		{
			sy = 1;
		} else
		{
			sy = -1;
		}
		int err = dx - dy;

		boolean halfLifeB = false;
		float halfLife = 1.0f;

		int tX = (int) translate.getX();
		int tY = (int) translate.getY();
	
		if((x0 + tX < 0 && x1 + tX < 0) || (x0 + tX > w && x1 + tX > w))
		{
			return;
		}
		
		if((y0 + tY < 0 && y1 + tY < 0) || (y0 + tY > h && y1 + tY > h))
		{
			return;
		}
		
		while (true)
		{
			if (x0 + tX >= 0 && x0 + tX < w && y0 + tY >= 0 && y0 + tY < h)
			{

				if(getLightBlock(x0,y0) == 2)
				{
					halfLife -= 0.1f;
				}
				
				if(getLightBlock(x0,y0) == 3)
				{
					halfLifeB = true;
				}
				
				if(getLightBlock(x0,y0) != 3 && halfLifeB)
				{
					halfLife /= 2;
					halfLifeB = false;
				}
				
				drawLightMap(x0, y0, Pixel.getLightPower(light.getColor(x0 - x, y0 - y), halfLife));
				
				if(Pixel.isLightLower(Pixel.getLightPower(light.getColor(x0 - x, y0 - y), halfLife), ambientLight))
					return;
				
				if(getLightBlock(x0,y0) == 1)
				{
					break;
				}
			}

			if (x0 == x1 && y0 == y1)
			{
				break;
			}

			int e2 = 2 * err;

			if (e2 > -dy)
			{
				err -= dy;
				x0 += sx;
			}
			if (e2 < dx)
			{
				err += dx;
				y0 += sy;
			}
		}
	}
	
	public int getLightBlock(int x, int y)
	{
		x += (int)translate.getX();
		y += (int)translate.getY();
		
		if(x < 0 || x >= w || y < 0 || y >= h)
			return 0;
		
		return lb[x + y * w];
	}
	
	public void setTranslate(float x, float y)
	{
		translate.setX(x);
		translate.setY(y);
	}
	
	public Vector2f getTranslate()
	{
		return translate;
	}

	public int getAmbientLight()
	{
		return ambientLight;
	}

	public void setAmbientLight(int ambientLight)
	{
		this.ambientLight = ambientLight;
	}
}

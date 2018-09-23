package com.base.engine;

public class Light
{
	public int color, radius, diameter;
	public int x, y;
	public int[] lm;
	
	public Light(int color, int radius, int x, int y)
	{
		this(color, radius);
		this.x = x;
		this.y = y;
	}
	
	public Light(int color, int radius)
	{
		this.color = color;
		this.radius = radius;
		this.diameter = radius * 2;
		lm = new int[diameter * diameter];
		
		for(int x = -radius; x < radius; x++)
		{
			for(int y = -radius; y < radius; y++)
			{
				int distance = (int) Math.round(Math.sqrt(x * x + y * y));
				
				if(distance <= radius)
					lm[(x + radius) + (y + radius) * diameter] = Pixel.getLightPower(color, 1f - (distance / (float)radius));
				else
					lm[(x + radius) + (y + radius) * diameter] = 0;
			}
		}
	}

	public int getColor(int x, int y)
	{
		if(x < 0 || x >= diameter || y < 0 || y >= diameter)
			return 0;
		
		return lm[x + y * diameter];
	}
}
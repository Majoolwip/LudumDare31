package com.base.engine;

public class LightBox
{
	public int height, color;
	public int[] lm;
	
	public LightBox(int height, int color)
	{
		this.height = height;
		this.color = color;
		lm = new int[height];
		
		for(int i = 0; i < height; i++)
		{
			lm[i] = Pixel.getLightPower(color, 1 - (i / (float)height));
		}
	}
}

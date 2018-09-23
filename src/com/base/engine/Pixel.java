package com.base.engine;

public class Pixel
{
	public static int getColor(float r, float g, float b)
	{
		return ((int)(r * 255) << 16 | (int)(g * 255) << 8 | (int)(b * 255));
	}
	
	public static float getRed(int color)
	{
		return (0xff & color >> 16) / 255f;
	}
	
	public static float getGreen(int color)
	{
		return (0xff & color >> 8) / 255f;
	}
	
	public static float getBlue(int color)
	{
		return (0xff & color) / 255f;
	}
	
	public static int getLightSum(int color, float power)
	{
		float r = getRed(color) + power; if(r < 0) r = 0;
		float g = getGreen(color) + power; if(g < 0) g = 0;
		float b = getBlue(color) + power; if(b < 0) b = 0;
		if(r > 1) r = 1;
		if(g > 1) g = 1;
		if(b > 1) b = 1;
		return getColor(r,g,b);
	}

	
	public static int getLightPower(int color, float power)
	{
		float r = getRed(color) * power; if(r < 0) r = 0;
		float g = getGreen(color) * power; if(g < 0) g = 0;
		float b = getBlue(color) * power; if(b < 0) b = 0;
		if(r > 1) r = 1;
		if(g > 1) g = 1;
		if(b > 1) b = 1;
		return getColor(r,g,b);
	}

	public static boolean isLightLower(int light, int ambient)
	{
		return (getRed(light) <= getRed(ambient) && getGreen(light) <= getGreen(ambient) && getBlue(light) <= getBlue(ambient));
	}
	
	public static int getLightOverlay(int color, int light)
	{
		return getColor(getRed(color) * getRed(light),
						getGreen(color) * getGreen(light),
						getBlue(color) * getBlue(light));
	}
	
	public static int getColorBlend(int color0, int color1)
	{
		return getColor(Math.max(getRed(color0),getRed(color1)),
						Math.max(getGreen(color0),getGreen(color1)),
						Math.max(getBlue(color0),getBlue(color1)));
	}
}

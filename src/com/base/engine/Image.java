package com.base.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	public int[] p;
	public int w;
	public int h;
	public int lb = 0;
	
	public Image(String path)
	{
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		w = image.getWidth();
		h = image.getHeight();
		p = image.getRGB(0, 0, w, h, null, 0, w);
		
		image.flush();
	}
}

package com.base.engine;

public class ImageTile extends Image
{
	public int tW, tH;
	
	public ImageTile(String path, int tW, int tH)
	{
		super(path);
		this.tW = tW;
		this.tH = tH;
	}

}

package com.base.engine;

public class Vector2f
{
	private float x, y;
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float length()
	{
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public Vector2f normalized()
	{
		float length = length();
		return new Vector2f(x / length, y / length);
	}
	
	public Vector2f add(Vector2f r)
	{
		return new Vector2f(x + r.getX(), y + r.getY());
	}
	
	public Vector2f mul(Vector2f r)
	{
		return new Vector2f(x * r.getX(), y * r.getY());
	}
	
	public Vector2f reflect()
	{
		return new Vector2f(-x, -y);
	}
	
	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}
	
	public boolean isEqual(Vector2f r)
	{
		return x == r.getX() && y == r.getY();
	}
}

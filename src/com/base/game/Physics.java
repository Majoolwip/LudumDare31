package com.base.game;

import java.util.ArrayList;

public class Physics
{
	private static ArrayList<GameObject> go = new ArrayList<GameObject>();
	
	public static void update()
	{
		for(int i = 0; i < go.size(); i++)
		{
			for(int j = i + 1; j < go.size(); j++)
			{
				if(go.get(i).tilePos.isEqual(go.get(j).tilePos))
				{
					go.get(i).collide(go.get(j));
					go.get(j).collide(go.get(i));
				}
			}
		}
		
		go.clear();
	}
	
	public static void addObject(GameObject gameObject)
	{
		go.add(gameObject);
	}
}

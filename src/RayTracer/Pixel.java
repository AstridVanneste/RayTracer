package RayTracer;

import Math.Vector;

import java.awt.*;

public class Pixel
{
	private int x;
	private int y;
	private Color color;

	public Pixel(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.color = Color.BLACK;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public int x()
	{
		return this.x;
	}

	public int y()
	{
		return this.y;
	}
}

package RayTracer;

import Math.Vector;
import RayTracer.Factories.VectorFactory;

import java.awt.*;
import java.security.InvalidParameterException;

public class Pixel
{
	private int x;
	private int y;
	private Vector loc;
	private Color color;

	public Pixel(int x, int y, Vector loc) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(loc))
		{
			throw new InvalidParameterException("Location parameter is not a point");
		}
		this.x = x;
		this.y = y;
		this.loc = loc;
		this.color = Color.DARK_GRAY;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public Vector getLoc()
	{
		return this.loc;
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

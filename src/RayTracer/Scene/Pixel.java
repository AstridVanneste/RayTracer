package RayTracer.Scene;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import RayTracer.Settings;
import Util.Color;

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
		this.color = new Color(Settings.BACKGROUND_COLOR);
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

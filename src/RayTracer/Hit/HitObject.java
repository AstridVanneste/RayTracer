package RayTracer.Hit;

import Math.Vector;
import RayTracer.RayTracer;

import java.awt.*;

public class HitObject
{
	private Vector hitpoint;
	private double distance;
	private Color color;

	public HitObject(Vector hitpoint, double distance, Color color)
	{
		this.hitpoint = hitpoint;
		this.distance = distance;
		this.color = color;
	}

	public Vector getHitpoint()
	{
		return this.hitpoint;
	}

	public void setHitpoint(Vector hitpoint)
	{
		this.hitpoint = hitpoint;
	}

	public double getDistance()
	{
		return this.distance;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
}

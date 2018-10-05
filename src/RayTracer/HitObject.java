package RayTracer;

import Math.Vector;

public class HitObject
{
	private Vector hitpoint;
	private double distance;
	private Vector color;

	public HitObject(Vector hitpoint, double distance, Vector color)
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

	public Vector getColor()
	{
		return this.color;
	}

	public void setColor(Vector color)
	{
		this.color = color;
	}
}

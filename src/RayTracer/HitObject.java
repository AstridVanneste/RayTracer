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
}

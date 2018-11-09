package RayTracer.Lighting;

import java.awt.*;
import Math.Vector;
import RayTracer.Tracer;

public class Lighter
{
	private Color color;
	private Vector normal;

	public Color getColor()
	{
		return this.color;
	}

	/**
	 * Will determine the color that will be seen through a certain ray, taking into account refraction, reflection and lighting.
	 * @return
	 */
	public Color illuminate(Tracer tracer)
	{
		return this.color;
	}
}

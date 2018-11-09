package RayTracer.Lighting;

import java.awt.*;
import Math.Vector;
import RayTracer.Tracer;

public class LightManager
{
	private Color color;

	private Shader shader;

	public LightManager(Color color, Vector normal)
	{
		this.color = color;
	}

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

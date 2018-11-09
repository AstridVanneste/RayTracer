package RayTracer.Lighting;

import java.awt.*;
import Math.Vector;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;

public class LightManager
{
	private Color color;

	private Shader shader;

	public LightManager(Color color, Vector normal)
	{
		this.color = color;
		this.shader = new Shader(normal, 0.1); 		// TODO valid m value
	}

	public Color getColor()
	{
		return this.color;
	}

	/**
	 * Will determine the color that will be seen through a certain ray, taking into account refraction, reflection and lighting.
	 * @return
	 */
	public Color illuminate(Tracer tracer, World world, Ray ray, Vector hitpoint)
	{
		float r, g, b;

		Color shade = this.shader.getLighterComponent(world, ray, hitpoint);

		r = shade.getRed() * this.color.getRed();
		g = shade.getGreen() * this.color.getGreen();
		b = shade.getBlue() * this.color.getBlue();

		System.out.println(this.color);
		System.out.println(r + " " + g + " " + b);

		return new Color(r, g, b);
	}
}

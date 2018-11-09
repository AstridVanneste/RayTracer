package RayTracer.Lighting;

import Math.Vector;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;

public class LightManager
{

	private Shader shader;

	public LightManager(Color color, Vector normal)
	{
		this.shader = new Shader(normal, 0.1); 		// TODO valid m value
	}

	/**
	 * Will determine the color that will be seen through a certain ray, taking into account refraction, reflection and lighting.
	 * @return
	 */
	public Color illuminate(Tracer tracer, World world, Ray ray, HitObject hit, Color color)
	{
		Color shade = this.shader.getLight(world, ray, hit);
		color.scale(shade);
		return color;
	}
}

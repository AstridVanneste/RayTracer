package RayTracer.Lighting;

import Math.Vector;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Scene.Objects.Entity;
import Util.Color;

public class LightManager
{
	private Shader shader;

	public LightManager(Color color, Vector normal, Entity entity)
	{
		this.shader = new PhongShader(entity);
	}

	/**
	 * Will determine the color that will be seen through a certain ray, taking into account refraction, reflection and lighting.
	 * @return
	 */
	public Color illuminate(Tracer tracer, World world, Ray ray, HitObject hit, Color color)
	{
		color = new Color(color);
		Color shade = this.shader.getLight(world, ray, tracer, hit);
		color.scale(shade);
		return color;
	}
}

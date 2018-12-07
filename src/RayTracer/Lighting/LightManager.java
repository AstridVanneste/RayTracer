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
	private Reflector reflector;

	public LightManager(Entity entity)
	{
		double[] eta = {16, 16, 16};
		this.shader = new CookTorranceShader(entity, 0.5, eta, 0.7);
		//this.shader = new PhongShader(entity);

		this.reflector = new Reflector();
	}

	/**
	 * Will determine the color that will be seen through a certain ray, taking into account refraction, reflection and lighting.
	 * @return
	 */
	public Color calculateColor(Tracer tracer, World world, Ray ray, HitObject hit, Color color)
	{
		color = new Color(color);

		Color scale = new Color(Color.BLACK);


		Color shade = new Color(this.shader.getLight(world, ray, tracer, hit));
		scale.add(shade);

		Color reflection = new Color(this.reflector.calculateReflection(world, ray, tracer, hit));
		reflection.scale(0.5);
		scale.add(reflection);

		color.scale(scale);

		return color;
	}
}

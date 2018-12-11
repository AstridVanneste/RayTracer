package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.Material;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Scene.Objects.Entity;
import Util.Color;
import Util.IO;
import org.json.JSONObject;

import java.io.IOException;

public class LightManager
{
	private Shader shader;
	private Reflector reflector;

	public LightManager(Entity entity)
	{
		double[] eta = {1000, 11.7077, 3.3385};
		this.shader = new CookTorranceShader(entity, 0.5, eta, 0.9);
		//this.shader = new PhongShader(entity);

		this.reflector = new Reflector(0.9);
	}

	public LightManager(Entity entity, String materialName)
	{
		try
		{
			String s = IO.readAllLines("res/JSON/materials/" + materialName + ".json");
			JSONObject json = new JSONObject(s);
			Material material = new Material(json);
			this.shader = new CookTorranceShader(entity, material);
			this.reflector = new Reflector(material);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
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

		Color reflection = new Color(this.reflector.calculateReflection(ray, tracer, hit));
		//System.out.println(reflection);
		//scale.add(reflection);

		color.scale(scale);

		return color;
	}
}

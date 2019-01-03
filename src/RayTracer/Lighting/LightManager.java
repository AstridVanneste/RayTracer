package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Settings;
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
	private Refractor refractor;

	public LightManager()
	{
		double[] eta = {1000, 11.7077, 3.3385};

		if(Settings.SHADER == ShaderType.COOK_TORRANCE)
		{
			this.shader = new CookTorranceShader(1.0, 0.5, eta, 0.9);
		}
		else if(Settings.SHADER == ShaderType.PHONG)
		{
			this.shader = new PhongShader();
		}

		this.reflector = new Reflector(0.0);
		this.refractor = new Refractor(0.0, 0.0);
	}

	public LightManager(String materialName)
	{
		try
		{
			String s = IO.readAllLines("res/JSON/materials/" + materialName + ".json");
			JSONObject json = new JSONObject(s);
			Material material = new Material(json);
			this.shader = new CookTorranceShader(material);
			this.reflector = new Reflector(material);
			this.refractor = new Refractor(material);
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
	public Color calculateColor(Tracer tracer, World world, Ray ray, HitObject hit)
	{
		Color color = new Color(hit.getColor());

		Color scale = new Color(Color.BLACK);

		// LIGHTING
		Color shade = new Color(this.shader.getLight(world, ray, tracer, hit));
		scale.add(shade);

		// REFLECTION
		Color reflection = new Color(this.reflector.calculateReflection(ray, tracer, hit));
		scale.add(reflection);

		// REFRACTION
		Color refraction = this.refractor.calculateRefraction(ray, tracer, hit);
		if(refraction == null)
		{
			// total internal reflection => intensity refraction has to be added to intensity reflection
			refraction = new Color(reflection);
			//refraction.scale(1/this.reflector.getReflectivity());
			refraction.scale(this.refractor.getRefractivity());
		}
		else
		{
			refraction = new Color(refraction);
		}
		scale.add(refraction);

		color.scale(scale);

		return color;
	}
}

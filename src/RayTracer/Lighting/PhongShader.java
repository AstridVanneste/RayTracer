package RayTracer.Lighting;

import Math.Vector;
import Math.Geometry;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Scene.Objects.Entity;
import Util.Color;

public class PhongShader extends Shader
{

	protected Color getAmbientComponent(Light light)
	{
		Color lightColor = new Color(light.getColor());

		lightColor.scale(light.getAmbientStrength());

		return lightColor;
	}

	private Color getDiffuseComponent(Light light, HitObject hit)
	{
		Vector lightDir = Vector.subtract(light.getPosition(), hit.getHitpoint());

		double fraction = Vector.dotProduct(lightDir, hit.getNormal());

		Color lightColor = new Color(light.getColor());

		lightColor.scale(fraction);

		return lightColor;
	}

	private Color getSpecularComponent(Light light, Ray r, HitObject hit)
	{
		Vector lightDir = Vector.subtract(light.getPosition(), hit.getHitpoint());

		Vector viewDir = r.getDir();
		Vector reflectDir = Geometry.reflect(lightDir, hit.getNormal());

		double spec = Math.pow(Math.max(Vector.dotProduct(viewDir, reflectDir), 0.0), 32);

		Color color = new Color(light.getColor());

		color.scale(light.getSpecularStrength());
		color.scale(spec);

		return color;
	}


	protected Color getLighterComponent(Light light, Ray r, HitObject hit)
	{
		Color color = new Color(Color.BLACK);
		color.add(this.getAmbientComponent(light));
		color.add(this.getDiffuseComponent(light, hit));
		color.add(this.getSpecularComponent(light, r, hit));
		return color;
	}


}

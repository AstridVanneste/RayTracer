package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.Objects.Entity;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;

abstract public class Shader
{
	protected Entity entity;

	public Shader(Entity entity)
	{
		this.entity = entity;
	}

	public abstract Color getLight(World world, Ray ray, Tracer tracer, HitObject hit);
}

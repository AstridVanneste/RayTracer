package RayTracer.Scene;

import RayTracer.Hit.Hittable;
import RayTracer.Lighting.Light;

import java.util.List;

public class World
{
	private List<Hittable> objects;
	private List<Light> lights;

	public World(List<Hittable> objects, List<Light> lights)
	{
		this.objects = objects;
		this.lights = lights;
	}

	public List<Hittable> getObjects()
	{
		return objects;
	}

	public List<Light> getLights()
	{
		return lights;
	}
}

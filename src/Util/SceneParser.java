package Util;

import RayTracer.Hit.Hittable;
import RayTracer.Lighting.Light;
import RayTracer.Scene.Camera;
import RayTracer.Scene.Objects.Cube;
import RayTracer.Scene.Objects.Mesh;
import RayTracer.Scene.Objects.Quad;
import RayTracer.Scene.Objects.Sphere;
import RayTracer.Scene.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneParser
{
	public class JSON
	{
		public static final String CAMERA = "camera";
		public static final String AMBIENT_LIGHT = "global_lights";
		public static final String POINT_LIGHT = "point_lights";
		public static final String OBJECT = "objects";
		public static final String OBJECT_TYPE = "type";
		public static final String SPHERE = "sphere";
		public static final String QUAD = "quad";
		public static final String CUBE = "cube";
		public static final String MESH = "mesh";
	}

	private String path;

	public SceneParser(String path)
	{
		this.path = path;
	}

	public void parseSettings()
	{

	}

	public Camera parseCamera() throws IOException
	{
		String file = IO.readAllLines(this.path);

		JSONObject json = new JSONObject(file);

		return new Camera(json.getJSONObject(JSON.CAMERA));
	}

	public World parseWorld() throws IOException
	{
		String file = IO.readAllLines(this.path);
		JSONObject json = new JSONObject(file);

		List<Hittable> entities = this.parseEntities(json);
		List<Light> lights =  this.parseLights(json);

		return new World(entities, lights);
	}

	private List<Hittable> parseEntities(JSONObject json)
	{
		List<Hittable> entities = new ArrayList<>();

		JSONArray jsonEntities = json.getJSONArray(JSON.OBJECT);

		for (int i = 0; i < jsonEntities.length(); i++)
		{
			JSONObject jsonEntity = jsonEntities.getJSONObject(i);
			String type = jsonEntity.getString(JSON.OBJECT_TYPE);

			switch (type)
			{
				case JSON.QUAD:
					entities.add(new Quad(jsonEntity, i));
					break;
				case JSON.CUBE:
					entities.add(new Cube(jsonEntity, i));
					break;
				case JSON.SPHERE:
					entities.add(new Sphere(jsonEntity, i));
					break;
				case JSON.MESH:
					entities.add(new Mesh(jsonEntity, i));
					break;
				default:
					System.out.println("Unknown Entity type: " + type);
			}
		}

		return entities;
	}

	private List<Light> parseLights(JSONObject json)
	{
		List<Light> lights = new ArrayList<>();

		JSONArray jsonLights = json.getJSONArray(JSON.AMBIENT_LIGHT);

		for(int i = 0; i < jsonLights.length(); i++)
		{
			JSONObject jsonLight = jsonLights.getJSONObject(i);
			lights.add(new Light(jsonLight, JSON.AMBIENT_LIGHT));
		}

		jsonLights = json.getJSONArray(JSON.POINT_LIGHT);

		for(int i = 0; i < jsonLights.length(); i++)
		{
			JSONObject jsonLight = jsonLights.getJSONObject(i);
			lights.add(new Light(jsonLight, JSON.POINT_LIGHT));
		}

		return lights;
	}
}

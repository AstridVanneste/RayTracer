package Util;

import RayTracer.Hit.Hittable;
import RayTracer.Lighting.Light;
import RayTracer.Scene.Objects.Cube;
import RayTracer.Scene.Objects.Mesh;
import RayTracer.Scene.Objects.Quad;
import RayTracer.Scene.Objects.Sphere;
import RayTracer.Scene.World;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class SceneParser
{
	public class Keys
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

		JSONArray jsonEntities = json.getJSONArray(Keys.OBJECT);

		for (int i = 0; i < jsonEntities.length(); i++)
		{
			JSONObject jsonEntity = jsonEntities.getJSONObject(i);
			String type = jsonEntity.getString(Keys.OBJECT_TYPE);

			switch (type)
			{
				case Keys.QUAD:
					entities.add(new Quad(jsonEntity, i));
					break;
				case Keys.CUBE:
					entities.add(new Cube(jsonEntity, i));
					break;
				case Keys.SPHERE:
					entities.add(new Sphere(jsonEntity, i));
					break;
				case Keys.MESH:
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

		JSONArray jsonLights = json.getJSONArray(Keys.AMBIENT_LIGHT);

		for(int i = 0; i < jsonLights.length(); i++)
		{
			JSONObject jsonLight = jsonLights.getJSONObject(i);
			lights.add(new Light(jsonLight, Keys.AMBIENT_LIGHT));
		}

		jsonLights = json.getJSONArray(Keys.POINT_LIGHT);

		for(int i = 0; i < jsonLights.length(); i++)
		{
			JSONObject jsonLight = jsonLights.getJSONObject(i);
			lights.add(new Light(jsonLight, Keys.POINT_LIGHT));
		}

		return lights;
	}
}

package Util;

import RayTracer.Hit.Hittable;
import RayTracer.Lighting.Light;
import RayTracer.Scene.Objects.Cube;
import RayTracer.Scene.Objects.Mesh;
import RayTracer.Scene.Objects.Sphere;
import RayTracer.Scene.World;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneParser
{
	private class Keys
	{
		public static final String CAMERA = "camera";
		public static final String AMBIENT_LIGHT = "global_light";
		public static final String POINT_LIGHT = "point_light";
		public static final String OBJECT = "objects";
		public static final String OBJECT_TYPE = "type";
		public static final String SPHERE = "sphere";
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
		List<Light> lights = new ArrayList<>();

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
				case Keys.CUBE:
					entities.add(new Cube(jsonEntity));
					break;
				case Keys.SPHERE:
					entities.add(new Sphere(jsonEntity));
					break;
				case Keys.MESH:
					entities.add(new Mesh(jsonEntity));
					break;
			}
		}

		return entities;
	}
}

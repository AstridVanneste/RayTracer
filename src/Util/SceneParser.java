package Util;

import RayTracer.Hit.Hittable;
import RayTracer.Lighting.Light;
import RayTracer.Scene.Objects.Cube;
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
		BufferedReader reader = new BufferedReader(new FileReader(this.path));
		JSONObject json = new JSONObject(reader.read());

		List<Hittable> entities = this.parseEntities(json);
		List<Light> lights = new ArrayList<>();

		return new World(entities, lights);
	}

	private List<Hittable> parseEntities(JSONObject json)
	{
		List<Hittable> entities = new ArrayList<>();

		// CUBES
		JSONArray jsonArray = json.getJSONArray(Keys.CUBE);

		for(int i = 0; i < jsonArray.length(); i++)
		{
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			entities.add(new Cube(jsonObject));
		}

		return entities;
	}
}

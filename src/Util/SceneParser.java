package Util;

import RayTracer.Hit.Hittable;
import RayTracer.Lighting.Light;
import RayTracer.Lighting.ShaderType;
import RayTracer.Scene.Camera;
import RayTracer.Scene.Objects.*;
import RayTracer.Scene.World;
import RayTracer.Settings;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneParser
{
	public class JSON
	{
		public static final String GENERAL = "general";
		public static final String CAMERA = "camera";
		public static final String AMBIENT_LIGHT = "global_lights";
		public static final String POINT_LIGHT = "point_lights";
		public static final String OBJECT = "objects";
		public static final String OBJECT_TYPE = "type";
		public static final String SPHERE = "sphere";
		public static final String QUAD = "quad";
		public static final String CUBE = "cube";
		public static final String MESH = "mesh";
		public static final String CYLINDER = "cylinder";

		public class SETTINGS
		{
			public static final String LIGHTING_MODEL = "lighting-model";
			public static final String TRACE_DEPTH = "trace-depth";
			public static final String BACKGROUND_COLOR = "background";
		}
	}

	private String path;

	private JSONObject json;

	public SceneParser(String path) throws IOException
	{
		this.path = path;
		this.createJSON();
	}

	private void createJSON() throws IOException
	{
		String file = IO.readAllLines(this.path);

		this.json = new JSONObject(file);
	}

	public void parseSettings()
	{
		JSONObject settings = this.json.getJSONObject(JSON.GENERAL);
		Settings.BACKGROUND_COLOR = new Color(settings.getJSONArray(JSON.SETTINGS.BACKGROUND_COLOR));
		Settings.TRACE_LEVEL = settings.getInt(JSON.SETTINGS.TRACE_DEPTH);

		switch(settings.getString(JSON.SETTINGS.LIGHTING_MODEL))
		{
			case "Phong":
				Settings.SHADER = ShaderType.PHONG;
				break;
			case "Cook-Torrance":
				Settings.SHADER = ShaderType.COOK_TORRANCE;
				break;
		}
	}

	public Camera parseCamera()
	{
		return new Camera(this.json.getJSONObject(JSON.CAMERA));
	}

	public World parseWorld()
	{
		List<Hittable> entities = this.parseEntities();
		List<Light> lights =  this.parseLights();

		return new World(entities, lights);
	}

	private List<Hittable> parseEntities()
	{
		List<Hittable> entities = new ArrayList<>();

		JSONArray jsonEntities = this.json.getJSONArray(JSON.OBJECT);

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
				case JSON.CYLINDER:
					entities.add(new TaperedCylinder(jsonEntity, i));
					break;
				default:
					System.out.println("Unknown Entity type: " + type);
			}
		}

		return entities;
	}

	private List<Light> parseLights()
	{
		List<Light> lights = new ArrayList<>();

		JSONArray jsonLights = this.json.getJSONArray(JSON.AMBIENT_LIGHT);

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

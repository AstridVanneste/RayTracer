package RayTracer.Scene;

import org.json.JSONArray;
import org.json.JSONObject;

public class Material
{
	private class JSON
	{
		public static final String ROUGHNESS = "roughness";
		public static final String KS = "kSpecular";
		public static final String REFRACTION_INDEX = "refractionIndex";
		public static final String REFLECTIVITY = "reflectivity";
	}

	private double roughness;
	private double kd;
	private double[] refractionIndex;
	private double reflectivity;


	public Material(double roughness, double kd, double[] refractionIndex, double reflectivity)
	{
		this.roughness = roughness;
		this.kd = kd;
		this.refractionIndex = refractionIndex;
		this.reflectivity = reflectivity;
	}

	public Material(JSONObject json)
	{
		this.roughness = json.getDouble(JSON.ROUGHNESS);
		this.kd = 1 - json.getDouble(JSON.KS);
		this.reflectivity = json.getDouble(JSON.REFLECTIVITY);
		this.refractionIndex = new double[3];

		JSONArray refraction = json.getJSONArray(JSON.REFRACTION_INDEX);
		for(int i = 0; i < refractionIndex.length; i++)
		{
			this.refractionIndex[i] = refraction.getDouble(i);
		}
	}

	public double getRoughness()
	{
		return roughness;
	}

	public double getKd()
	{
		return kd;
	}

	public double[] getRefractionIndex()
	{
		return refractionIndex;
	}

	public double getReflectivity()
	{
		return reflectivity;
	}
}

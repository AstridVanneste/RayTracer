package RayTracer.Lighting;

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
		public static final String REFRACTIVITY = "refractivity";
	}

	private double roughness;
	private double kd;
	private double[] refractionIndex;
	private double reflectivity;
	private double refractivity;


	public Material(double roughness, double kd, double[] refractionIndex, double reflectivity, double refractivity)
	{
		this.roughness = roughness;
		this.kd = kd;
		this.refractionIndex = refractionIndex;
		this.reflectivity = reflectivity;
		this.refractivity = refractivity;
	}

	public Material(JSONObject json)
	{
		this.roughness = json.getDouble(JSON.ROUGHNESS);
		this.kd = 1 - json.getDouble(JSON.KS);
		this.reflectivity = json.getDouble(JSON.REFLECTIVITY);
		this.refractivity = json.getDouble(JSON.REFRACTIVITY);
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

	public double getRefractivity()
	{
		return this.refractivity;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("MATERIAL:\n");

		builder.append("roughness:\t");
		builder.append(this.roughness);
		builder.append("\n");

		builder.append("kd:\t");
		builder.append(this.kd);
		builder.append("\n");

		builder.append("refractionIndex:\t");
		builder.append(this.refractionIndex[0]);
		builder.append(this.refractionIndex[1]);
		builder.append(this.refractionIndex[2]);
		builder.append("\n");

		builder.append("reflectivity:\t");
		builder.append(this.reflectivity);
		builder.append("\n");

		builder.append("refractivity:\t");
		builder.append(this.refractivity);
		builder.append("\n");

		return builder.toString();
	}
}

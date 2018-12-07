package RayTracer.Lighting;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import RayTracer.Scene.Objects.Entity;
import Util.Color;
import Util.SceneParser;
import org.json.JSONObject;

import java.security.InvalidParameterException;

public class Light
{
	private class JSON_KEYS
	{
		public static final String COLOR = "color";
		public static final String INTENSITY = "intensity";
		public static final String POSITION = "position";
		public static final String SPECULAR_STRENGTH = "specular_strength";
	}

	private Vector position;
	private Color  color;

	private double ambientStrength;
	private double specularStrength;

	private boolean enableAmbient;
	private boolean enableDiffuse;
	private boolean enableSpecular;

	public Light(Vector position, Color color, double ambientStrength, double specularStrength) throws InvalidParameterException
	{


		if(!VectorFactory.isPoint(position))
		{
			throw new InvalidParameterException("Position argument is not a point");
		}

		this.position = position;
		this.color = color;
		this.ambientStrength = ambientStrength;
		this.specularStrength  = specularStrength;

		this.enableAmbient = true;
		this.enableDiffuse = true;
		this.enableSpecular = true;
	}

	public Light(JSONObject jsonObject, String type)
	{
		this.color = new Color(jsonObject.getJSONArray(JSON_KEYS.COLOR));

		if(type.equals(SceneParser.Keys.AMBIENT_LIGHT))
		{
			this.ambientStrength = jsonObject.getDouble(JSON_KEYS.INTENSITY);
			this.enableAmbient = true;
			this.enableDiffuse = false;
			this.enableSpecular = false;
		}
		else if(type.equals(SceneParser.Keys.POINT_LIGHT))
		{
			this.specularStrength =  jsonObject.getDouble(JSON_KEYS.SPECULAR_STRENGTH);
			this.color.scale(jsonObject.getDouble(JSON_KEYS.INTENSITY));
			this.position = VectorFactory.createPointVector(jsonObject.getJSONArray(JSON_KEYS.POSITION));
			this.enableAmbient = false;
			this.enableDiffuse = true;
			this.enableSpecular = true;
		}
	}

	public Vector getPosition()
	{
		return this.position;
	}

	public Color getColor()
	{
		return this.color;
	}

	public double getAmbientStrength()
	{
		return this.ambientStrength;
	}

	public double getSpecularStrength()
	{
		return this.specularStrength;
	}

	public boolean isEnableAmbient()
	{
		return this.enableAmbient;
	}

	public void setEnableAmbient(boolean enableAmbient)
	{
		this.enableAmbient = enableAmbient;
	}

	public boolean isEnableDiffuse()
	{
		return this.enableDiffuse;
	}

	public void setEnableDiffuse(boolean enableDiffuse)
	{
		this.enableDiffuse = enableDiffuse;
	}

	public boolean isEnableSpecular()
	{
		return this.enableSpecular;
	}

	public void setEnableSpecular(boolean enableSpecular)
	{
		this.enableSpecular = enableSpecular;
	}
}

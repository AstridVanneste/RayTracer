package RayTracer.Lighting;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import Util.Color;

import java.security.InvalidParameterException;

public class Light
{
	private Vector position;
	private Color  color;

	private double ambientStrength;
	private double specularStrength;

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
}

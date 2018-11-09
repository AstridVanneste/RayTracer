package RayTracer.Lighting;

import Math.Vector;
import RayTracer.Factories.VectorFactory;

import java.awt.*;
import java.security.InvalidParameterException;

public class Light
{
	private Vector position;
	private Color  color;

	public Light(Vector position, Color color) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(position))
		{
			throw new InvalidParameterException("Position argument is not a point");
		}

		this.position = position;
		this.color = color;
	}

	public Vector getPosition()
	{
		return this.position;
	}

	public Color getColor()
	{
		return this.color;
	}
}

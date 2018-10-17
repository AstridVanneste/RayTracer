package RayTracer.Objects;

import RayTracer.Hit.Hittable;

import java.awt.*;

public abstract class Object implements Hittable
{
	private Color color;

	public Object()
	{
		this.color = Color.LIGHT_GRAY;
	}

	public Object(Color color)
	{
		this.color = color;
	}

	protected Color getColor()
	{
		return this.color;
	}

	protected void  setColor(Color color)
	{
		this.color = color;
	}
}

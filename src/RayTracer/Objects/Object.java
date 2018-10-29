package RayTracer.Objects;

import RayTracer.Hit.Hittable;
import RayTracer.Transformation;
import Math.Matrix;

import java.awt.*;

public abstract class Object implements Hittable
{
	private Color color;
	protected boolean transform;
	protected Transformation transformation;

	public Object()
	{
		this.color = Color.LIGHT_GRAY;
		this.transform = false;
		this.transformation = new Transformation(Matrix.identityMatrix(4), Matrix.identityMatrix(4));
	}

	public Object(Color color)
	{
		this.color = color;
		this.transform = false;
		this.transformation = new Transformation(Matrix.identityMatrix(4), Matrix.identityMatrix(4));
	}

	public Object(Color color, Transformation transformation)
	{
		this.color = color;
		this.transform = true;
		this.transformation = transformation;
	}

	protected Color getColor()
	{
		return this.color;
	}

	protected void  setColor(Color color)
	{
		this.color = color;
	}

	public void setTransformation(Transformation transformation)
	{
		this.transform = true;
		this.transformation = transformation;
	}
}

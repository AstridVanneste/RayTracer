package RayTracer.Hit;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.RayTracer;
import RayTracer.Transformation;

import java.security.InvalidParameterException;

public class Ray
{
	private Vector eye;
	private Vector dir;

	public Ray(Vector eye, Vector dir)
	{
		if(VectorFactory.isPoint(eye))
		{
			this.eye = eye;
		}
		else
		{
			throw new InvalidParameterException("Eye should be a point");
		}
		if(VectorFactory.isVector(dir))
		{
			this.dir = dir;
			this.dir.normalize(false);
		}
		else
		{
			throw new InvalidParameterException("Direction should be a vector");
		}
	}

	public Ray(Ray r)
	{
		this.eye = r.eye;
		this.dir = r.dir;
	}

	public Vector getEye()
	{
		return this.eye;
	}

	public void setEye(Vector eye)
	{
		this.eye = eye;
	}

	public Vector getDir()
	{
		return this.dir;
	}

	public void setDir(Vector dir)
	{
		this.dir = dir;
	}

	public Vector getPoint(double k)
	{
		return Vector.add(this.eye, Vector.multiply(this.dir, k));
	}

	public void inverseTransform(Transformation transformation)
	{
		this.eye = transformation.inverse(this.eye);
		this.dir = transformation.inverse(this.dir);
	}

	@Override
	public String toString()
	{
		return "EYE: " + this.eye + " DIRECTION: " + this.dir;
	}
}

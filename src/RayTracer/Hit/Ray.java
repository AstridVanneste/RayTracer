package RayTracer.Hit;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.RayTracer;

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
			throw new InvalidParameterException("Eye vector size should be 4 but is " + eye.size());
		}
		if(VectorFactory.isVector(dir))
		{
			this.dir = dir;
		}
		else
		{
			throw new InvalidParameterException("Direction vector size should be 4 but is " + dir.size());
		}
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

	@Override
	public String toString()
	{
		return "EYE: " + this.eye + " DIRECTION: " + this.dir;
	}
}

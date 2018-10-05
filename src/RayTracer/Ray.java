package RayTracer;

import Math.Vector;
import java.security.InvalidParameterException;

public class Ray
{
	private Vector eye;
	private Vector dir;

	public Ray(Vector eye, Vector dir)
	{
		if(eye.size() == 4)
		{
			this.eye = eye;
		}
		else
		{
			throw new InvalidParameterException("Eye vector size should be 4 but is " + eye.size());
		}
		if(dir.size() == 4)
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
}

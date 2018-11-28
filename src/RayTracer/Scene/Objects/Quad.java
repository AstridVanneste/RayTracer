package RayTracer.Scene.Objects;

import RayTracer.Factories.VectorFactory;
import Math.Vector;

public class Quad extends Polygon
{
	public Quad()
	{
		super();
		Vector RF = VectorFactory.createPointVector(1, 0, 1);
		Vector RB = VectorFactory.createPointVector(1, 0, -1);
		Vector LF = VectorFactory.createPointVector(-1, 0, 1);
		Vector LB = VectorFactory.createPointVector(-1, 0, -1);

		Vector[] limits = new Vector[4];
		limits[0] = RF;
		limits[1] = RB;
		limits[2] = LB;
		limits[3] = LF;

		this.setLimits(limits);
	}
}

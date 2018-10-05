package RayTracer;

import Factories.VectorFactory;
import Math.Vector;

public class RayTracer
{
	public static void main(String args[])
	{
		int xLimit = 500;
		int yLimit = 600;

		Vector eye = new Vector(4);
		eye.set(0, 0);
		eye.set(1, 2.5);
		eye.set(2, 5);
		eye.set(3, 1);

		double zOffsetScreen = 2;

		for(int x = 0; x < xLimit; x++)
		{
			for(int y = 0; y < yLimit; y++)
			{
				Vector pixelPoint = VectorFactory.createPointVector(x, y, zOffsetScreen);
				Vector ray = Vector.subtract(eye, pixelPoint);
			}
		}
	}
}

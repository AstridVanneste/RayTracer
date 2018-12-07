package RayTracer.Factories;

import Math.Vector;
import org.json.JSONArray;
import org.json.JSONObject;

public class VectorFactory
{
	public static Vector createPointVector(double x, double y, double z)
	{
		Vector point = new Vector(4);
		point.set(0, x);
		point.set(1, y);
		point.set(2, z);
		point.set(3, 1);

		return point;
	}

	public static Vector createPointVector(JSONArray json)
	{
		return createPointVector(json.getDouble(0), json.getDouble(1), json.getDouble(2));
	}

	public static Vector createVector(double x, double y, double z)
	{
		Vector vector = new Vector(4);
		vector.set(0, x);
		vector.set(1, y);
		vector.set(2, z);
		vector.set(3, 0);

		return vector;
	}

	public static Vector createVector(JSONArray json)
	{
		return createVector(json.getDouble(0), json.getDouble(1), json.getDouble(2));
	}

	public static boolean isPoint(Vector v)
	{
		if(v.size() != 4)
		{
			return false;
		}
		if(v.get(3) != 1)
		{
			return false;
		}
		return true;
	}

	public static boolean isVector(Vector v)
	{
		if(v.size() != 4)
		{
			System.out.println("size != 4");
			return false;
		}
		if(v.get(3) != 0)
		{
			System.out.println("vector[3] != 0");
			return false;
		}
		return true;
	}
}

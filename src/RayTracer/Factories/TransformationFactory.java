package RayTracer.Factories;

import Math.Matrix;
import Math.Vector;
import Math.Utils;
import RayTracer.Transformation;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;


public class TransformationFactory
{
	/*
		Proper Transformation Order:

		SCALING -> ROTATING -> TRANSLATING
	*/

	private class JSON
	{
		public static final String TRANSLATION = "trans";
		public static final String ROTATION = "rot";
		public static final String SCALING = "scaling";
		public static final String TYPE = "type";
		public static final String VALUE = "val";
		public static final String AXIS = "axis";
		public static final String ANGLE = "angle";
	}

	public static Transformation transformation(JSONArray json)
	{
		Transformation transformation = new Transformation();

		for(int i = 0; i < json.length(); i++)
		{
			JSONObject transf = json.getJSONObject(i);
			transformation.add(transformation(transf));
		}

		System.out.println(transformation);

		return transformation;
	}

	public static Transformation transformation(JSONObject json)
	{
		String type = json.getString(JSON.TYPE);

		switch(type)
		{
			case JSON.TRANSLATION:
				JSONArray value = json.getJSONArray(JSON.VALUE);
				return translationTransformation(value.getDouble(0), value.getDouble(1), value.getDouble(2));
			case JSON.ROTATION:
				Vector axis = new Vector(json.getJSONArray(JSON.AXIS));
				double angle = json.getDouble(JSON.ANGLE);
				angle = Utils.degToRad(angle);
				return rotationTransformation(axis, angle);
			case JSON.SCALING:
				value = json.getJSONArray(JSON.VALUE);
				return scalingTransformation(value.getDouble(0), value.getDouble(1), value.getDouble(2));
			default:
				System.out.println("Unknown transformation type: " + type);
		}

		return new Transformation();
	}

	public static Transformation translationTransformation(double x, double y, double z)
	{
		return new Transformation(translation(x, y, z), inverseTranslation(x, y, z));
	}

	public static Transformation translationTransformation(Vector v)
	{
		return TransformationFactory.translationTransformation(v.get(0), v.get(1), v.get(2));
	}

	public static Pair<Matrix, Matrix> translationPair(double x, double y, double z)
	{
		return new Pair<>(translation(x, y, z), inverseTranslation(x, y, z));
	}

	private static Matrix translation(double x, double y, double z)
	{
		Matrix matrix = Matrix.identityMatrix(4);

		matrix.set(0, 3, x);
		matrix.set(1, 3, y);
		matrix.set(2, 3, z);

		return matrix;
	}

	private static Matrix inverseTranslation(double x, double y, double z)
	{
		return translation(-x, -y, -z);
	}

	public static Transformation scalingTransformation(double x, double y, double z)
	{
		return new Transformation(scaling(x, y, z), inverseScaling(x, y, z));
	}

	public static Pair<Matrix, Matrix> scalingPair(double x, double y, double z)
	{
		return new Pair<>(scaling(x, y, z), inverseScaling(x, y, z));
	}

	private static Matrix scaling(double x, double y, double z)
	{
		Matrix matrix = new Matrix(4, 4);

		matrix.set(0, 0, x);
		matrix.set(1, 1, y);
		matrix.set(2, 2, z);
		matrix.set(3, 3, 1);

		return matrix;
	}

	private static Matrix inverseScaling(double x, double y, double z)
	{
		return scaling(1/x, 1/y, 1/z);
	}

	public static Transformation rotationTransformation(Vector axis, double theta)
	{
		axis.normalize();
		return new Transformation(rotation(axis.get(0), axis.get(1), axis.get(2), theta), inverseRotation(axis.get(0), axis.get(1), axis.get(2), theta));
	}

	public static Pair<Matrix, Matrix> rotationPair(Vector axis, double theta)
	{
		axis.normalize();
		return new Pair<Matrix, Matrix>(rotation(axis.get(0), axis.get(1), axis.get(2), theta), inverseRotation(axis.get(0), axis.get(1), axis.get(2), theta));
	}

	private static Matrix rotation(double x, double y, double z, double theta)
	{
		Matrix matrix = new Matrix(4, 4);

		double cos = Math.cos(theta);
		double sin = Math.sin(theta);

		matrix.set(0, 0, cos + Math.pow(x, 2) * (1 - cos));
		matrix.set(0, 1, x * y * (1 - cos) - z * sin);
		matrix.set(0, 2, x * z * (1 - cos) + y * sin);

		matrix.set(1, 0, x * y * (1 - cos) + z * sin);
		matrix.set(1, 1, cos + Math.pow(y, 2) * (1 - cos));
		matrix.set(1, 2, y * z * ( 1 - cos) - x * sin);

		matrix.set(2, 0, z * x * (1 - cos) - y * sin);
		matrix.set(2, 1, z * y * (1 - cos) + x * sin);
		matrix.set(2, 2, cos + Math.pow(z, 2) * (1 - cos));

		matrix.set(3, 3, 1);

		return matrix;
	}

	private static Matrix inverseRotation(double x, double y, double z, double theta)
	{
		return rotation(x, y, z, -theta);
	}
}

package Util;

import RayTracer.Factories.VectorFactory;
import RayTracer.Objects.Object;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Math.Vector;

public class OBJReader
{
	private class prefixes
	{
		public static final String VERTEX = "v";
		public static final String FACE = "f";
	}


	public static List<Object> read(String path) throws IOException
	{
		List<Object> objects = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new FileReader(path));

		String line;

		while((line = reader.readLine()) != null)
		{

		}

		return objects;
	}

	private static Vector parsePoint(String line)
	{
		String split[] = line.split(" ");

		int x = Integer.parseInt(split[1]);
		int y = Integer.parseInt(split[2]);
		int z = Integer.parseInt(split[3]);

		return VectorFactory.createPointVector(x, y, z);
	}

	private static Object parseObject(String line, List<Vector> points)
	{
		String split[] = line.split(" ");


	}
}

package Util;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.Hittable;
import RayTracer.Objects.Object;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Math.Vector;
import RayTracer.Objects.Polygon;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DurationDV;

public class OBJReader
{
	private class Prefixes
	{
		public static final String VERTEX = "v ";
		public static final String FACE = "f ";
	}


	public static List<Hittable> read(String path)
	{
		// TODO check file path stuffs

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			return read(reader);
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static List<Hittable> read(BufferedReader reader) throws IOException
	{
		List<Hittable> objects = new ArrayList<>();

		String line;

		List<Vector> points = new ArrayList<>();

		while((line = reader.readLine()) != null)
		{
			if(line.startsWith(Prefixes.VERTEX))
			{
				points.add(parsePoint(line));
			}
			else if(line.startsWith(Prefixes.FACE))
			{
				objects.add(parseFace(line, points));
			}
		}

		return objects;
	}


	private static Vector parsePoint(String line)
	{
		System.out.println(line);
		String split[] = line.split("\\s+");

		Double x = Double.parseDouble(split[1]);
		Double y = Double.parseDouble(split[2]);
		Double z = Double.parseDouble(split[3]);

		return VectorFactory.createPointVector(x, y, z);
	}


	private static Hittable parseFace(String line, List<Vector> points)
	{
		String split[] = line.split("\\s+");

		Vector limits[] = new Vector[split.length - 1];

		for(int i = 0; i < split.length - 1; i++)
		{
			String element[] = split[i + 1].split("/");
			int pointIndex = Integer.parseInt(element[0]) - 1;

			limits[i] = points.get(pointIndex);
			System.out.println("LIMIT: " + i + " = " + points.get(pointIndex));
		}

		return new Polygon(limits);
	}
}

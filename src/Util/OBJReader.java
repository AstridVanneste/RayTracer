package Util;

import RayTracer.Factories.VectorFactory;
import RayTracer.Scene.Objects.Mesh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Math.Vector;
import RayTracer.Scene.Objects.Polygon;

public class OBJReader
{
	private class Prefixes
	{
		public static final String VERTEX = "v ";
		public static final String FACE = "f ";
	}


	public static Mesh read(String path)
	{
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

	public static Mesh read(BufferedReader reader) throws IOException
	{

		List<Polygon> faces = OBJReader.parseFaces(reader);
		//System.out.println("#Vertices: " + points.size());
		//System.out.println("#Faces: " + faces.size());

		return new Mesh(faces);
	}

	public static List<Polygon> parseFaces(BufferedReader reader) throws IOException
	{
		List<Polygon> faces = new ArrayList<>();

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
				faces.add(parseFace(line, points));
			}
		}

		return faces;
	}


	private static Vector parsePoint(String line)
	{
		String split[] = line.split("\\s+");

		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[3]);
		double z = Double.parseDouble(split[2]);

		return VectorFactory.createPointVector(x, y, z);
	}


	private static Polygon parseFace(String line, List<Vector> points)
	{
		String split[] = line.split("\\s+");

		Vector limits[] = new Vector[split.length - 1];

		for(int i = 0; i < split.length - 1; i++)
		{
			String element[] = split[i + 1].split("/");
			int pointIndex = Integer.parseInt(element[0]) - 1;

			limits[i] = points.get(pointIndex);
		}

		return new Polygon(limits);
	}
}

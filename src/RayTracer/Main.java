package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Objects.Hittable;
import RayTracer.Objects.Plane;
import RayTracer.Objects.Polygon;

import javax.swing.*;
import java.util.ArrayList;

public class Main
{
	public static void main(String args[])
	{
		int width = 1280;
		int height = 720;

		Vector eye = VectorFactory.createPointVector(0, 5, 2);

		Vector screenOffset = VectorFactory.createPointVector(-1, 1, 1);
		Screen screen = new Screen(width, height, screenOffset, 0.01);

		ArrayList<Hittable> objects = populateWorld();

		RayTracer rayTracer = new RayTracer(eye, screen, objects);


		JFrame frame = new JFrame("RayTracer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(rayTracer);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	private static ArrayList<Hittable> populateWorld()
	{
		ArrayList<Hittable> objects = new ArrayList<>();

		Vector point = VectorFactory.createPointVector(0, 1, 1);
		Vector normal = VectorFactory.createVector(0, 1, 1);

		Vector[] limits = new Vector[3];

		limits[0] = VectorFactory.createPointVector(1 , 0, 0);
		limits[1] = VectorFactory.createPointVector( 1, 1, -1);
		limits[2] = VectorFactory.createPointVector( -1, 1, -1);
		//limits[3] = VectorFactory.createPointVector( -50, 0, 50);

		Plane plane = new Plane(normal, point);
		Polygon polygon = new Polygon(limits);
		objects.add(polygon);

		return objects;
	}
}

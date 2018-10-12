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
		Vector eye = VectorFactory.createPointVector(0, 200, 100);
		int xLimit = 1280;
		int yLimit = 720;
		Vector screenOffset = VectorFactory.createPointVector(0, 0, 50);

		ArrayList<Hittable> objects = populateWorld();

		Screen screen = new Screen(xLimit, yLimit, screenOffset, 1);

		RayTracer rayTracer = new RayTracer(eye, screen, objects);
		JFrame frame = new JFrame("RayTracer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(rayTracer);
		frame.setSize(xLimit, yLimit);
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

		limits[0] = VectorFactory.createPointVector(50 , 0, -50);
		limits[1] = VectorFactory.createPointVector( 50, 20, -100);
		limits[2] = VectorFactory.createPointVector( -50, 20, -100);
		//limits[3] = VectorFactory.createPointVector( -50, 0, 50);

		Plane plane = new Plane(normal, point);
		Polygon polygon = new Polygon(limits);
		objects.add(polygon);

		return objects;
	}
}

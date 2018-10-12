package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Hit.Hittable;
import RayTracer.Objects.Plane;
import RayTracer.Objects.Polygon;
import RayTracer.Screen.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main
{
	public static void main(String args[])
	{
		int width = 1000;
		int height = 1000;

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

		Vector[] limits = new Vector[4];

		limits[0] = VectorFactory.createPointVector(1 , 0, 0);
		limits[1] = VectorFactory.createPointVector( 1, 1, -1);
		limits[2] = VectorFactory.createPointVector( -1, 1, -1);
		limits[3] = VectorFactory.createPointVector( -1, 0, 0);

		Plane plane = new Plane(normal, point, Color.YELLOW);
		Polygon polygon = new Polygon(limits, Color.YELLOW);
		objects.add(polygon);

		return objects;
	}
}

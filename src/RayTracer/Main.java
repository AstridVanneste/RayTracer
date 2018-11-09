package RayTracer;

import RayTracer.Factories.TransformationFactory;
import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Hit.Hittable;
import RayTracer.Objects.*;
import RayTracer.Objects.Polygon;
import RayTracer.Screen.Screen;
import Util.OBJReader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Main
{
	private static int WIDTH = 1000;
	private static int HEIGHT = 1000;


	private static String OBJ_FILE  = "res/OBJ/cube.obj";

	public static void main(String args[])
	{

		Vector eye = VectorFactory.createPointVector(2, 2, 3);

		Vector screenOffset = VectorFactory.createPointVector(-2, -2, 1.5);
		Screen screen = new Screen(WIDTH, HEIGHT, screenOffset, 0.005);
		List<Hittable> objects = new ArrayList<>();

		//objects.addAll(populateWorld());

		System.out.println("Tracing file: " + OBJ_FILE);
		Mesh mesh = OBJReader.read(OBJ_FILE);


		objects.add(mesh);


		RayTracer rayTracer = new RayTracer(eye, screen, objects);


		JFrame frame = new JFrame("RayTracer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(rayTracer);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	private static ArrayList<Hittable> populateWorld()
	{
		ArrayList<Hittable> objects = new ArrayList<>();

		Vector point = VectorFactory.createPointVector(0, 1, 1);
		Vector normal = VectorFactory.createVector(0, 1, 1);
		Plane plane = new Plane(normal, point, Color.RED);

		Vector[] limits1 = new Vector[4];
		limits1[0] = VectorFactory.createPointVector(1 , -1, 1);
		limits1[1] = VectorFactory.createPointVector( 1, -1, -1);
		limits1[2] = VectorFactory.createPointVector( -1, -1, -1);
		limits1[3] = VectorFactory.createPointVector( -1, -1, 1);
		Polygon square1 = new Polygon(limits1, Color.YELLOW);

		Vector[] limits2 = new Vector[4];
		limits2[0] = VectorFactory.createPointVector(1 , -1, 1);
		limits2[1] = VectorFactory.createPointVector( 1, -1, -1);
		limits2[2] = VectorFactory.createPointVector( 1, 1, -1);
		limits2[3] = VectorFactory.createPointVector( 1, 1, 1);
		Polygon square2 = new Polygon(limits2, Color.BLUE);

		Vector[] limits3 = new Vector[4];
		limits3[0] = VectorFactory.createPointVector(1 , -1, -1);
		limits3[1] = VectorFactory.createPointVector( 1, 1, -1);
		limits3[2] = VectorFactory.createPointVector( -1, 1, -1);
		limits3[3] = VectorFactory.createPointVector( -1, -1, -1);
		Polygon square3 = new Polygon(limits3, Color.CYAN);

		Vector[] limits4 = new Vector[4];
		limits4[0] = VectorFactory.createPointVector(1 , 1, 1);
		limits4[1] = VectorFactory.createPointVector( 1, 1, -1);
		limits4[2] = VectorFactory.createPointVector( -1, 1, -1);
		limits4[3] = VectorFactory.createPointVector( -1, 1, 1);
		Polygon square4 = new Polygon(limits4, Color.GREEN);

		Vector[] limits5 = new Vector[4];
		limits5[0] = VectorFactory.createPointVector(-1 , -1, 1);
		limits5[1] = VectorFactory.createPointVector( -1, -1, -1);
		limits5[2] = VectorFactory.createPointVector( -1, 1, -1);
		limits5[3] = VectorFactory.createPointVector( -1, 1, 1);
		Polygon square5 = new Polygon(limits5, Color.PINK);

		Vector[] limits6 = new Vector[4];
		limits6[0] = VectorFactory.createPointVector(1 ,  -1,1);
		limits6[1] = VectorFactory.createPointVector( 1,  1, 1);
		limits6[2] = VectorFactory.createPointVector( -1, 1, 1);
		limits6[3] = VectorFactory.createPointVector( -1, -1,1);
		Polygon square6 = new Polygon(limits6, Color.RED);

		Polygon[] sides = new Polygon[6];
		sides[0] = square1;
		sides[1] = square2;
		sides[2] = square3;
		sides[3] = square4;
		sides[4] = square5;
		sides[5] = square6;

		/*objects.add(square1);
		objects.add(square2);
		objects.add(square3);
		objects.add(square4);
		objects.add(square5);
		objects.add(square6);*/

		//objects.add(new Cube(sides));

		//objects.add(plane);

		Sphere sphere = new Sphere(VectorFactory.createPointVector(0, 0, 0), 1);
		objects.add(sphere);

		return objects;
	}
}

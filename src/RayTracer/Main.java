package RayTracer;

import RayTracer.Factories.TransformationFactory;
import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Hit.Hittable;
import RayTracer.Lighting.Light;
import RayTracer.Scene.Objects.*;
import RayTracer.Scene.Objects.Polygon;
import RayTracer.Scene.World;
import RayTracer.Screen.Screen;
import Util.OBJReader;
import Util.Color;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main
{
	private static int WIDTH = 1000;
	private static int HEIGHT = 1000;

	private static String OBJ_FILE  = "res/OBJ/shuttle.obj";

	public static void main(String args[])
	{
		// CAMERA
		Vector eye = VectorFactory.createPointVector(2, 3, 5);
		Vector screenOffset = VectorFactory.createPointVector(-2, -2, 3);
		Screen screen = new Screen(WIDTH, HEIGHT, screenOffset, 0.005);

		// LIGHTING
		List<Light> lights = new ArrayList<>();
		lights.add(new Light(VectorFactory.createPointVector(0, 3, 0), Color.WHITE, 0.2, 0.3));
		//lights.add(new Light(VectorFactory.createPointVector(0, 5, 5), Color.WHITE, 0.1, 0.5));

		// OBJECTS
		List<Hittable> objects = new ArrayList<>();

		objects.addAll(populateWorld());

		System.out.println("Tracing file: " + OBJ_FILE);
		Mesh mesh = OBJReader.read(OBJ_FILE);
		Transformation meshTransformation = TransformationFactory.translationTransformation(0.1, 0.1, 0.5);
		//mesh.setTransformation(meshTransformation);

		Mesh surrounding = new Mesh(mesh);
		Transformation transformation = TransformationFactory.translationTransformation(-3, 0, 0);
		surrounding.setTransformation(transformation);
		surrounding.setColor(Color.WHITE);

		mesh.setColor(Color.BLUE);
		//objects.add(mesh);
		//objects.add(surrounding);



		// RAYTRACER
		RayTracer rayTracer = new RayTracer(eye, screen, new World(objects, lights));


		// VISUALIZATION
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

		Sphere sphere = new Sphere();

		objects.add(sphere);

		Vector[] limits7 = new Vector[4];
		limits7[0] = VectorFactory.createPointVector(1, -1, 1);
		limits7[1] = VectorFactory.createPointVector( 1, -1, -	1);
		limits7[2] = VectorFactory.createPointVector( -1, -1, -1);
		limits7[3] = VectorFactory.createPointVector( -1, -1, 1);
		Polygon square7 = new Polygon(limits7, Color.WHITE);

		//Transformation transformation = TransformationFactory.translationTransformation(0, 1, 0);
		Transformation transformation = TransformationFactory.rotationTransformation(VectorFactory.createVector(1, 0, 0), 3.14);
		//Transformation transformation = TransformationFactory.scalingTransformation(2, 2, 2);
		square7.setTransformation(transformation);

		System.out.println(transformation);

		objects.add(square7);

		return objects;
	}
}

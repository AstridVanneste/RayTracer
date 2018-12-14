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
import Util.SceneParser;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
	private static final int TRACE_LEVEL = 3;

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 1000;

	public static void main(String args[]) throws IOException
	{
		// CAMERA
		Vector eye = VectorFactory.createPointVector(1, 4, 5);
		Vector screenOffset = VectorFactory.createPointVector(-3.25, -1, 3);
		Screen screen = new Screen(WIDTH, HEIGHT, screenOffset, 0.005);

		// READING SCENE

		SceneParser sceneParser = new SceneParser("res/JSON/spheres.json");
		World world = sceneParser.parseWorld();

		// RAYTRACER
		RayTracer rayTracer = new RayTracer(eye, screen, world, TRACE_LEVEL);

		// VISUALIZATION
		JFrame frame = new JFrame("RayTracer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(rayTracer);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}


}

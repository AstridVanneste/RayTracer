package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Scene.World;
import RayTracer.Screen.Screen;
import Util.SceneParser;

import javax.swing.*;
import java.io.IOException;

public class Main
{
	private static final int TRACE_LEVEL = 3;

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 1000;

	public static void main(String args[]) throws IOException
	{
		// CAMERA
		Vector eye = VectorFactory.createPointVector(1.5, 4, 4);
		Vector screenOffset = VectorFactory.createPointVector(-3.25, -1, 0);
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

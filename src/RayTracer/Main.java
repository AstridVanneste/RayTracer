package RayTracer;

import javax.swing.*;
import java.io.IOException;

public class Main
{

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 1000;

	public static void main(String args[]) throws IOException
	{
		String scene;

		if(args.length != 0)
		{
			scene = args[0];
		}
		else
		{
			scene = "res/JSON/mesh.json";
		}
		RayTracer rayTracer = new RayTracer(scene);

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

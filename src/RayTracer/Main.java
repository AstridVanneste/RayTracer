package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;

import javax.swing.*;

public class Main
{
	public static void main(String args[])
	{
		Vector eye = VectorFactory.createPointVector(0, 0, 100);

		RayTracer rayTracer = new RayTracer(eye);
		JFrame frame = new JFrame("RayTracer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(rayTracer);
		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}

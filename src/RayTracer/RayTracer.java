package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Objects.Object;
import RayTracer.Objects.Plane;
import RayTracer.Objects.Polygon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RayTracer extends JPanel
{
	public static void main(String args[])
	{
		RayTracer rayTracer = new RayTracer();
		JFrame frame = new JFrame("RayTracer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(rayTracer);
		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	private static ArrayList<Object> populateWorld()
	{
		ArrayList<Object> objects = new ArrayList<>();

		Vector point = VectorFactory.createPointVector(0, 0, -5);
		Vector normal = VectorFactory.createVector(0, 1, 1);

		Vector[] limits = new Vector[4];

		limits[0] = VectorFactory.createPointVector(0, 0, 25);
		limits[1] = VectorFactory.createPointVector(25, 0, 0);
		limits[2] = VectorFactory.createPointVector(0, 25, 0);
		limits[3] = VectorFactory.createPointVector(0, 0, 0);

		Plane plane = new Plane(normal, point);

		Polygon polygon = new Polygon(plane, limits);

		objects.add(polygon);

		return objects;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		int xLimit = 1280;
		int yLimit = 720;

		Vector eye = VectorFactory.createPointVector(0, 100, 100);

		double zOffsetScreen = 2;

		ArrayList<Object> objects = RayTracer.populateWorld();

		System.out.println("start drawing");
		for(int x = 0; x < xLimit; x++)
		{
			for(int y = 0; y < yLimit; y++)
			{
				//System.out.println("Processing [" + x + ", " + y + "] ");
				Vector pixelPoint = VectorFactory.createPointVector(x, y, zOffsetScreen);
				Vector rayDirection = Vector.subtract(eye, pixelPoint);

				Ray ray = new Ray(eye, rayDirection);

				double distance = 0;
				for(Object object: objects)
				{
					HitObject hit = object.hit(ray);

					if(hit != null)
					{
						if(hit.getDistance() < distance || distance == 0)
						{
							g2d.setColor(new Color(new Float(hit.getDistance() % 1), 0f, 0.7f));
							g2d.drawLine(x, y, x, y);
							distance = hit.getDistance();
						}
					}
				}
				if(distance == 0)
				{
					g2d.setColor(Color.BLACK);
					g2d.drawLine(x, y, x, y);
				}
			}
		}
		System.out.println("finished drawing");
	}
}

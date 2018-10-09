package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Objects.Object;
import RayTracer.Objects.Plane;
import RayTracer.Objects.Polygon;
import org.omg.CORBA.INITIALIZE;

import javax.swing.*;
import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class RayTracer extends JPanel
{
	private Vector eye;

	public RayTracer(Vector eye) throws InvalidParameterException
	{
		if(VectorFactory.isPoint(eye))
		{
			this.eye = eye;
		}
		else
		{
			throw new InvalidParameterException("Eye parameter is not a point");
		}
	}

	public static void main(String args[])
	{
		Vector eye = VectorFactory.createPointVector(0, 100, 100);

		RayTracer rayTracer = new RayTracer(eye);
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

		Vector point = VectorFactory.createPointVector(0, 1, 1);
		Vector normal = VectorFactory.createVector(0, 1, 1);

		Vector[] limits = new Vector[3];

		limits[0] = VectorFactory.createPointVector(-50 , 0, -50);
		limits[1] = VectorFactory.createPointVector( -50, 0, 50);
		limits[2] = VectorFactory.createPointVector( 50, 0, 50);
		//limits[3] = VectorFactory.createPointVector( 50, 0, -50);

		Plane plane = new Plane(normal, point);

		Polygon polygon = new Polygon(limits);

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



		double zOffsetScreen = 2;

		ArrayList<Object> objects = RayTracer.populateWorld();

		List<Pixel> screen = new ArrayList<>();

		System.out.println("start drawing");
		for(int x = 0; x < xLimit; x++)
		{
			for(int y = 0; y < yLimit; y++)
			{
				screen.add(new Pixel(x, y));
			}
		}


		screen = this.trace(objects, screen);

		for(Pixel pixel: screen)
		{
			g2d.setColor(pixel.getColor());
			g2d.drawLine(pixel.x(), pixel.y(), pixel.x(), pixel.y());
		}

		System.out.println("finished drawing");
	}

	public List<Pixel> trace(List<Object> world, List<Pixel> screen)
	{
		int zOffsetScreen = 2;

		for(int i = 0; i < screen.size(); i++)
		{
			int x = screen.get(i).x();
			int y = screen.get(i).y();

			//System.out.println("Processing [" + x + ", " + y + "] ");
			Vector pixelPoint = VectorFactory.createPointVector(x, y, zOffsetScreen);
			Vector rayDirection = Vector.subtract(this.eye, pixelPoint);

			Ray ray = new Ray(this.eye, rayDirection);

			double distance = 0;
			for(Object object: world)
			{
				HitObject hit = object.hit(ray);

				if(hit != null)
				{
					if(hit.getDistance() < distance || distance == 0)
					{
						System.out.println("hit [" + x + ", " + y + "]");
						screen.get(i).setColor(new Color(new Float(hit.getDistance() % 1), 0f, 0.7f));
						distance = hit.getDistance();
					}
				}
			}
		}

		return screen;
	}
}

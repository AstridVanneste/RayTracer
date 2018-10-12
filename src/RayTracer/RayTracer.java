package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Objects.Hittable;
import RayTracer.Objects.Plane;
import RayTracer.Objects.Polygon;;

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

	private ArrayList<Hittable> populateWorld()
	{
		ArrayList<Hittable> objects = new ArrayList<>();

		Vector point = VectorFactory.createPointVector(0, 1, 1);
		Vector normal = VectorFactory.createVector(0, 1, 1);

		Vector[] limits = new Vector[3];

		limits[0] = VectorFactory.createPointVector(50 , 0, 0);
		limits[1] = VectorFactory.createPointVector( 50, 0, -50);
		limits[2] = VectorFactory.createPointVector( -50, 0, -50);
		//limits[3] = VectorFactory.createPointVector( -50, 0, 50);

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
		Vector screenOffset = VectorFactory.createPointVector(0, -100, 0);

		ArrayList<Hittable> objects = this.populateWorld();

		Screen screen = new Screen(xLimit, yLimit, screenOffset, 1);

		System.out.println("start drawing");


		List<Pixel> pixels = this.trace(objects, screen.getPixels(Screen.PixelOrder.COLUMN_TDLR));

		for(Pixel pixel: pixels)
		{
			g2d.setColor(pixel.getColor());
			g2d.drawLine(xLimit - pixel.x(), yLimit - pixel.y(), xLimit - pixel.x(), yLimit -  pixel.y());
		}

		System.out.println("finished drawing");
	}

	public List<Pixel> trace(List<Hittable> world, List<Pixel> screen)
	{
		for(int i = 0; i < screen.size(); i++)
		{
			int x = screen.get(i).x();
			int y = screen.get(i).y();

			//System.out.println("Processing [" + x + ", " + y + "] ");
			Vector pixelPoint = screen.get(i).getLoc();
			Vector rayDirection = Vector.subtract(this.eye, pixelPoint);

			Ray ray = new Ray(this.eye, rayDirection);

			double distance = 0;
			for(Hittable object: world)
			{
				HitObject hit = object.hit(ray);

				if(hit != null)
				{
					if(hit.getDistance() < distance || distance == 0)
					{
						System.out.println("hit [" + x + ", " + y + "]");
						screen.get(i).setColor(hit.getColor());
						System.out.println(hit.getColor());
						distance = hit.getDistance();
					}
				}
			}
		}
		return screen;
	}
}

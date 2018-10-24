package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Hit.Hittable;
import RayTracer.Screen.Pixel;
import RayTracer.Screen.Screen;
import Util.PPMWriter;

import javax.swing.*;
import java.awt.*;
import java.security.InvalidParameterException;
import java.util.List;


public class RayTracer extends JPanel
{
	private Vector eye;
	private Screen screen;
	private List<Hittable> world;

	public RayTracer(Vector eye, Screen screen, List<Hittable> world) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(eye))
		{
			throw new InvalidParameterException("Eye parameter is not a point");

		}
		this.eye = eye;
		this.world = world;
		this.screen = screen;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		System.out.println("TRACING...");
		long start = System.nanoTime();
		List<Pixel> pixels = this.screen.getPixels(Screen.PixelOrder.ROW_TDLR);
		pixels = this.trace(pixels);
		long end = System.nanoTime();
		System.out.println("FINISHED!");
		System.out.println("TRACE TIME: " + ((float)(end - start))/1000000000);

		System.out.println("DRAWING...");
		for(Pixel pixel: pixels)
		{
			g2d.setColor(pixel.getColor());
			g2d.drawLine(pixel.x(), this.screen.height() - pixel.y(), pixel.x(), this.screen.height() - pixel.y());
		}

		try
		{
			PPMWriter ppmWriter = new PPMWriter("trace.ppm", PPMWriter.Format.P3, this.screen.width(), this.screen.height());
			ppmWriter.write(pixels);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("FINISHED!");
	}

	private List<Pixel> trace(List<Pixel> pixels)
	{
		for(Pixel pixel: pixels)
		{
			Vector pixelPoint = pixel.getLoc();
			Vector rayDirection = Vector.subtract(pixelPoint, this.eye);

			Ray ray = new Ray(this.eye, rayDirection);

			double distance = 0;
			for(Hittable object: this.world)
			{
				HitObject hit = object.hit(ray);

				if(hit != null)
				{
					if(hit.getDistance() < distance || distance == 0)
					{
						pixel.setColor(hit.getColor());
						distance = hit.getDistance();
					}
				}
			}
		}
		return pixels;
	}
}

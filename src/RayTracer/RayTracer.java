package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Hit.Hittable;
import RayTracer.Scene.World;
import RayTracer.Screen.Pixel;
import RayTracer.Screen.Screen;
import Util.PPMWriter;

import javax.swing.*;
import java.awt.*;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


public class RayTracer extends JPanel implements Tracer
{
	private Vector eye;
	private Screen screen;
	private World world;

	public RayTracer(Vector eye, Screen screen, World world) throws InvalidParameterException
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
			// TODO anti-aliasing by averaging with the surrounding pixels
			g2d.setColor(pixel.getColor().get());
			g2d.drawLine(pixel.x(), this.screen.height() - pixel.y(), pixel.x(), this.screen.height() - pixel.y());
		}

		try
		{
			String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Timestamp(System.currentTimeMillis()));
			PPMWriter ppmWriter = new PPMWriter("traces/" + time + ".ppm", PPMWriter.Format.P3, this.screen.width(), this.screen.height());
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

			HitObject hit = this.trace(ray);

			if(hit != null)
			{
				pixel.setColor(hit.getColor());
			}
		}
		return pixels;
	}

	@Override
	public HitObject trace(Ray r)
	{
		return this.trace(r, null, 2);
	}

	@Override
	public HitObject trace(Ray r, Hittable excluded, int traceLevel)
	{
		HitObject closestHit = null;
		for(Hittable object: this.world.getObjects())
		{
			if(object != excluded)
			{
				HitObject hit = object.hit(r, this, this.world, traceLevel);

				if (hit != null)
				{
					if (closestHit == null)
					{
						closestHit = hit;
					} else if (hit.getDistance() < closestHit.getDistance())
					{
						closestHit = hit;
					}
				}
			}
		}

		return closestHit;
	}
}

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
import Math.Geometry;

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
	private int traceLevel;

	private List<Pixel> image;

	public RayTracer(Vector eye, Screen screen, World world, int traceLevel) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(eye))
		{
			throw new InvalidParameterException("Eye parameter is not a point");

		}
		this.eye = eye;
		this.world = world;
		this.screen = screen;
		this.traceLevel = traceLevel;

		this.image = this.trace();

		this.save();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		for(Pixel pixel: this.image)
		{
			// TODO anti-aliasing by averaging with the surrounding pixels
			g2d.setColor(pixel.getColor().get());
			g2d.drawLine(pixel.x(), this.screen.height() - pixel.y(), pixel.x(), this.screen.height() - pixel.y());
		}
	}

	private List<Pixel> trace()
	{
		System.out.println("TRACING...");
		long start = System.nanoTime();
		List<Pixel> pixels = this.screen.getPixels(Screen.PixelOrder.ROW_TDLR);
		pixels = this.trace(pixels, this.traceLevel);
		long end = System.nanoTime();
		System.out.println("FINISHED!");
		System.out.println("TRACE TIME: " + ((float)(end - start))/1000000000);

		return pixels;
	}

	private List<Pixel> trace(List<Pixel> pixels, int traceLevel)
	{
		for(Pixel pixel: pixels)
		{
			Vector pixelPoint = pixel.getLoc();
			Vector rayDirection = Vector.subtract(pixelPoint, this.eye);

			Ray ray = new Ray(this.eye, rayDirection);

			HitObject hit = this.trace(ray, traceLevel);

			if(hit != null)
			{
				pixel.setColor(hit.getColor());
			}
		}
		return pixels;
	}

	@Override
	public HitObject trace(Ray r, int traceLevel)
	{
		return this.trace(r, null, traceLevel);
	}

	@Override
	public HitObject trace(Ray r, Hittable excluded, int traceLevel)
	{
		int excludedID = -1;
		if(excluded != null)
		{
			excludedID = excluded.getID();
		}

		HitObject closestHit = null;
		for(Hittable object: this.world.getObjects())
		{
			if(object.getID() != excludedID)
			{
				HitObject hit = object.hit(r, this, this.world, traceLevel);

				if (hit != null)
				{
					if (closestHit == null)
					{
						closestHit = hit;
					}
					else if (Geometry.distance(this.eye, hit.getHitpoint()) < Geometry.distance(this.eye, closestHit.getHitpoint()))
					{
						closestHit = hit;
					}
				}
			}
		}

		if(closestHit != null)
		{
			// LIGHTING
			if (traceLevel != 0)
			{
				closestHit.setColor(closestHit.getObject().calculateColor(this, this.world, r, closestHit));
			}
		}

		return closestHit;
	}

	public Pixel[][] frameBuffer(List<Pixel> pixels, int width, int height)
	{
		Pixel[][] buffer = new Pixel[height][width];
		for(Pixel pixel: pixels)
		{
			buffer[pixel.x()][pixel.y()] = pixel;
		}

		return buffer;
	}

	private void save()
	{
		// SAVING
		try
		{
			String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Timestamp(System.currentTimeMillis()));
			PPMWriter ppmWriter = new PPMWriter("traces/" + time + ".ppm", PPMWriter.Format.P3, this.screen.width(), this.screen.height());
			ppmWriter.write(this.image);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*public List<Pixel> antiAliasing(Pixel[][] pixels)
	{
		List<Pixel> antiAliasing = new ArrayList<>();

		for(int i = 0; i < pixels.length; i++)
		{
			for(int j = 0; j < pixels[0].length; j++)
			{
				Pixel pixel = pixels[i][j]. + pixels[i - 1][j] + pixels[i + 1][j] + pixels[i][j - 1] + pixels[i][j + 1] + pixels[i + 1][j - 1] + pixels[i + 1][j + 1];
				antiAliasing.add()
			}
		}
	}*/
}

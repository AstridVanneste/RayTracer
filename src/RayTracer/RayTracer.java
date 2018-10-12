package RayTracer;

import RayTracer.Factories.VectorFactory;
import Math.Vector;
import RayTracer.Objects.Hittable;

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
		List<Pixel> pixels = this.trace(screen.getPixels(Screen.PixelOrder.COLUMN_TDLR));
		System.out.println("FINISHED!");

		System.out.println("DRAWING...");
		//System.out.println("NUMBER OF PIXELS = " + pixels.size());
		for(Pixel pixel: pixels)
		{
			System.out.println("pixel: [" + (this.screen.width() - pixel.x()) + ", " + (this.screen.height() - pixel.y()) + "]");
			g2d.setColor(pixel.getColor());
			g2d.drawLine(this.screen.width() - pixel.x(), this.screen.width() - pixel.y(), this.screen.height() - pixel.x(), this.screen.height() -  pixel.y());
		}

		System.out.println("FINISHED!");
	}

	private List<Pixel> trace(List<Pixel> screen)
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
			for(Hittable object: this.world)
			{
				HitObject hit = object.hit(ray);

				if(hit != null)
				{
					if(hit.getDistance() < distance || distance == 0)
					{
						System.out.println("hit [" + x + ", " + y + "]");
						screen.get(i).setColor(hit.getColor());
						//System.out.println(hit.getColor());
						distance = hit.getDistance();
					}
				}
			}
		}
		return screen;
	}
}

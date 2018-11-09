package RayTracer.Scene.Objects;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;

import java.awt.*;

public class Cube extends Object
{
	private static final int NUMBER_SIDES = 6;

	private Polygon sides[];

	public Cube(Polygon sides[])
	{
		if(sides.length == NUMBER_SIDES)
		{
			this.sides = sides;
		}
	}

	public Cube(Polygon sides[], Color color)
	{
		this(sides);
		this.setColor(color);
	}

	@Override
	protected HitObject internalHit(Ray r, Tracer tracer, World world)
	{
		HitObject hit = null;
		for(int i = 0; i < NUMBER_SIDES; i++)
		{
			HitObject currentHit = this.sides[i].internalHit(r, tracer, world);
			if(currentHit != null)
			{
				if (hit == null)
				{
					hit = currentHit;
				} else if (hit.getDistance() > currentHit.getDistance())
				{
					hit = currentHit;
				}
			}
		}

		return hit;
	}
}

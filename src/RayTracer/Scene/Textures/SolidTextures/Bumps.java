package RayTracer.Scene.Textures.SolidTextures;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Scene.Textures.Texture;
import Math.Vector;

public class Bumps implements Texture
{
	private static final double MOD_FACTOR = 0.5;

	@Override
	public HitObject sample(HitObject hit)
	{
		Vector normal = new Vector(hit.getNormal());
		Vector hitpoint = new Vector(hit.getHitpoint());


		// MODULO CALCULATION: to make texture repeat itself

		double x = hitpoint.get(0) % MOD_FACTOR;
		double y = hitpoint.get(1) % MOD_FACTOR;
		double z = hitpoint.get(2) % MOD_FACTOR;

		// SINE OPERATION: to create round bumps

		x = Math.sin(x);
		y = Math.sin(y);
		z = Math.sin(z);


		Vector alter = VectorFactory.createVector(x, y, z);

		normal = Vector.add(normal, alter);


		return new HitObject(hit.getObject(), hit.getHitpoint(), hit.getColor(), normal, hit.getK(), hit.getTraceLevel());
	}
}

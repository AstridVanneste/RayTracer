package RayTracer.Scene.Textures.SolidTextures;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import Math.Vector;
import RayTracer.Scene.Textures.Texture;

import java.util.Random;

public class RandomNormalAlteration implements Texture
{
	@Override
	public HitObject sample(HitObject hit)
	{
		Random random = new Random();

		Vector normal = hit.getNormal();

		Vector hitpoint = new Vector(hit.getHitpoint());
		hitpoint.normalize();


		double x = random.nextDouble() + hitpoint.get(0);
		double y = random.nextDouble() + hitpoint.get(1);
		double z = random.nextDouble() + hitpoint.get(2);

		Vector alter = VectorFactory.createVector(x, y, z);


		normal = Vector.add(normal, alter);

		return new HitObject(hit.getObject(), hit.getHitpoint(), hit.getColor(), normal, hit.getK(), hit.getTraceLevel());
	}
}

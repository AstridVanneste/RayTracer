package RayTracer.Scene.Textures.SolidTextures;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Scene.Textures.Texture;
import Math.Vector;

public class SinusoidNormalAlteration implements Texture
{
	@Override
	public HitObject sample(HitObject hit)
	{
		Vector hitpoint = new Vector(hit.getHitpoint());

		double x = Math.sin(hitpoint.get(0));
		double y = Math.sin(hitpoint.get(1));
		double z = Math.sin(hitpoint.get(2));

		Vector alter = VectorFactory.createVector(x, y, z);
		alter.normalize();

		Vector normal = hit.getNormal();
		normal = Vector.add(normal, alter);


		return new HitObject(hit.getObject(), hit.getHitpoint(), hit.getColor(), normal, hit.getK(), hit.getTraceLevel());
	}
}

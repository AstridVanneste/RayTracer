package RayTracer.Scene.Textures;

import RayTracer.Hit.HitObject;
import Util.Color;
import Math.Vector;

public interface Texture
{
	public HitObject sample(HitObject hit);
}

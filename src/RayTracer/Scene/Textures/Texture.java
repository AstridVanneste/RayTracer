package RayTracer.Scene.Textures;

import Util.Color;
import Math.Vector;

public abstract class Texture
{
	public abstract Color sample(Vector position);
}

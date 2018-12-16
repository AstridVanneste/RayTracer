package RayTracer.Scene.Textures;

import Util.Color;
import Math.Vector;

public interface Texture
{
	public Color sample(Vector position);
}

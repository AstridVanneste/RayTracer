package RayTracer.Scene.Textures.SolidTextures;

import RayTracer.Hit.HitObject;
import RayTracer.Scene.Textures.Texture;
import Util.Color;
import Math.Vector;
import org.json.JSONObject;

public class CheckerBoard implements Texture
{
	private class JSON
	{
		public static final String SIZE = "size";
	}

	Vector size;

	public CheckerBoard(Vector size)
	{
		super();
		this.size = size;
	}

	public CheckerBoard(JSONObject jsonObject)
	{
		this.size = new Vector(jsonObject.getJSONArray(JSON.SIZE));
	}

	@Override
	public HitObject sample(HitObject hit)
	{
		Color color;
		if(this.jump(hit.getHitpoint()))
		{
			color = new Color(Color.RED);
		}
		else
		{
			color =  new Color(Color.BLUE);
		}

		return new HitObject(hit.getObject(), hit.getHitpoint(), color, hit.getNormal(), hit.getK(), hit.getTraceLevel());
	}

	public boolean jump(Vector position)
	{
		double A = 10000;

		int x = (int) (A + position.get(0)/this.size.get(0));
		int y = (int) (A + position.get(1)/this.size.get(1));
		int z = (int) (A + position.get(2)/this.size.get(2));

		int result = ((x + y + z)%2);

		return result == 1;
	}
}

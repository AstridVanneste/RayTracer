package RayTracer.Scene.Textures.SolidTextures;

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
	public Color sample(Vector position)
	{
		if(this.jump(position))
		{
			return new Color(Color.RED);
		}
		else
		{
			return new Color(Color.BLUE);
		}
	}

	public boolean jump(Vector position)
	{
		double A = 10000;

		int x = (int) (A + position.get(0)/this.size.get(0));
		int y = (int) (A + position.get(1)/this.size.get(1));
		int z = (int) (A + position.get(2)/this.size.get(2));

		System.out.println("x = " + x);
		System.out.println("y = " + y);
		System.out.println("z = " + z);
		System.out.println((x + y + z));

		int result = ((x + y + z)%2);

		return result == 1;
	}
}

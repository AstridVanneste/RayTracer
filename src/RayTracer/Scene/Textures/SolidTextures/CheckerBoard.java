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
			return new Color(Color.WHITE);
		}
		else
		{
			return new Color(Color.BLACK);
		}
	}

	public boolean jump(Vector position)
	{
		int x = (int) (position.get(0)/this.size.get(0));
		int y = (int) (position.get(1)/this.size.get(1));
		int z = (int) (position.get(2)/this.size.get(2));

		int result = (((x + y + z)%2)+1)%2;

		return result == 1;
	}
}

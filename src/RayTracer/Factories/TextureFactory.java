package RayTracer.Factories;

import RayTracer.Scene.Textures.SolidTextures.CheckerBoard;
import RayTracer.Scene.Textures.Texture;
import org.json.JSONObject;

import java.security.InvalidParameterException;

public class TextureFactory
{
	private class JSON
	{
		public static final String TYPE = "type";
	}

	private class TEXTURES
	{
		public static final String CHECKERBOARD = "checkerboard";
	}

	public static Texture get(JSONObject jsonObject)
	{
		String type = jsonObject.getString(JSON.TYPE);
		switch(type)
		{
			case TEXTURES.CHECKERBOARD:
				return new CheckerBoard(jsonObject);
			default:
				throw new InvalidParameterException(type + " is not a know texture type");
		}
	}
}

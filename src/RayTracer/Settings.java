package RayTracer;

import RayTracer.Lighting.ShaderType;
import Util.Color;

public class Settings
{
	public static Color BACKGROUND_COLOR = Color.DARK_GRAY;

	public static int TRACE_LEVEL = 3;

	public static ShaderType SHADER = ShaderType.COOK_TORRANCE;
}

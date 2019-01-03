package Math;

public class Utils
{
	public static boolean XOR(boolean x, boolean y)
	{
		return ((x || y) && !(x && y));
	}

	public static double degToRad(double angle)
	{
		return (angle * Math.PI) / 180;
	}

	public static double radToDeg(double angle)
	{
		return (angle * 180)/Math.PI;
	}
}

package Math;

public class Utils
{
	public static boolean XOR(boolean x, boolean y)
	{
		return ( ( x || y ) && ! ( x && y ) );
	}
}

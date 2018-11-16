package Math;

public class Compare
{
	private static final double EPSILON = 4e-15;

	public static int compare(double d1, double d2)
	{
		double delta = d1 - d2;

		// EQUALITY
		if(Math.abs(delta) < EPSILON)
		{
			return 0;
		}

		// d1 > d2
		if(delta > 0)
		{
			return 1;
		}

		// d2 > d1
		return -1;
	}
}

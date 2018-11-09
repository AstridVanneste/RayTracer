package Util;

import Math.Vector;

public class Color extends Vector
{
	public static Color RED = new Color(1, 0, 0);
	public static Color GREEN = new Color(0, 1, 0);
	public static Color BLUE = new Color(0, 0, 1);
	public static Color PINK = new Color(1, 0, 1);
	public static Color YELLOW = new Color(1, 1, 0);
	public static Color CYAN = new Color(0, 1, 1);
	public static Color BLACK = new Color(0, 0, 0);
	public static Color WHITE = new Color(1, 1, 1);
	public static Color DARK_GRAY = new Color(0.15, 0.15, 0.15);
	public static Color LIGHT_GRAY = new Color(0.5, 0.5, 0.5);

	public Color(double r, double g, double b)
	{
		super(3);
		super.set(0, r);
		super.set(1, g);
		super.set(2, b);
	}

	public java.awt.Color get()
	{
		return new java.awt.Color((float) super.get(0), (float) super.get(1), (float) super.get(2));
	}

	public double getRed()
	{
		return super.get(0);
	}

	public double getGreen()
	{
		return super.get(1);
	}

	public double getBlue()
	{
		return super.get(2);
	}

	public int getRed255()
	{
		return (int) super.get(0) * 255;
	}

	public int getGreen255()
	{
		return (int) super.get(1) * 255;
	}

	public int getBlue255()
	{
		return (int) super.get(2) * 255;
	}
}

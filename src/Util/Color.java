package Util;

import org.json.JSONArray;

public class Color
{
	public static final Color RED = new Color(255, 0, 0);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color BLUE = new Color(0, 0, 255);
	public static final Color PINK = new Color(255, 0, 255);
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color CYAN = new Color(0, 255, 255);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color DARK_GRAY = new Color(32, 32, 32);
	public static final Color LIGHT_GRAY = new Color(128, 128, 128);


	private int r;
	private int g;
	private int b;

	public Color(int r, int g, int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public Color(Color c)
	{
		this.r = c.r;
		this.g = c.g;
		this.b = c.b;
	}

	public Color(JSONArray c)
	{
		this.r = c.getInt(0);
		this.g = c.getInt(1);
		this.b = c.getInt(2);
	}

	public java.awt.Color get()
	{
		this.clip();
		return new java.awt.Color(this.r, this.g,  this.b);
	}

	public int getRed()
	{
		return this.r;
	}

	public int getGreen()
	{
		return this.g;
	}

	public int getBlue()
	{
		return this.b;
	}

	public void scale(Color c)
	{
		this.r = (int) (this.r * c.r/255.0);
		this.g = (int) (this.g * c.g/255.0);
		this.b = (int) (this.b * c.b/255.0);
		this.clip();
	}

	public void scale(double s)
	{
		this.r = (int) (this.r * s);
		this.g = (int) (this.g * s);
		this.b = (int) (this.b * s);
		this.clip();
	}

	public void scale(double[] s) throws IllegalArgumentException
	{
		if(s.length != 3)
		{
			throw new IllegalArgumentException("can only scale color with 3 scalars, got " + s.length);
		}
		this.r *= s[0];
		this.g *= s[1];
		this.b *= s[2];
	}

	public void add(Color c)
	{
		this.r += c.r;
		this.g += c.g;
		this.b += c.b;
		this.clip();
	}

	private void clip()
	{
		if(this.r > 255)
		{
			this.r = 255;
		}
		else if(this.r < 0)
		{
			this.r = 0;
		}

		if(this.g > 255)
		{
			this.g = 255;
		}
		else if(this.g < 0)
		{
			this.g = 0;
		}

		if(this.b > 255)
		{
			this.b = 255;
		}
		else if(this.b < 0)
		{
			this.b = 0;
		}
	}

	@Override
	public String toString()
	{
		return "COLOR: " + this.r + " " + this.g + " " + this.b;
	}
}

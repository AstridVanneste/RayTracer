package RayTracer.Screen;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import RayTracer.Screen.Pixel;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Screen
{
	public enum PixelOrder
	{
		COLUMN_TDLR,
		COLUMN_TDRL,
		COLUMN_DTLR,
		COLUMN_DTRL,
		ROW_TDLR,
		ROW_TDRL,
		ROW_DTLR,
		ROW_DTRL;
	}

	private Vector offset;
	private double scalingFactor;

	private int height;
	private int width;

	public Screen(int width, int height, Vector offset, double scalingFactor) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(offset))
		{
			throw new InvalidParameterException("offset parameter is not a point");
		}

		this.height = height;
		this.width = width;

		this.offset = offset;
		this.scalingFactor = scalingFactor;
	}

	public List<Pixel> getPixels(PixelOrder order)
	{
		List<Pixel> pixels = new ArrayList<>();

		for(int i = 0; i < this.width; i++)
		{
			for(int j = 0; j < this.height; j++)
			{
				Vector std = VectorFactory.createPointVector(i, j, 0);
				Vector loc = Vector.add(this.offset, Vector.multiply(std, scalingFactor));
				loc.set(3, 1);
				pixels.add(new Pixel(i, j, loc));
			}
		}

		return pixels;
	}

	public int height()
	{
		return this.height;
	}

	public int width()
	{
		return this.width;
	}
}

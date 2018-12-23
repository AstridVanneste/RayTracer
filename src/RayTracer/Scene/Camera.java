package RayTracer.Scene;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.Ray;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Camera
{
	private class JSON
	{
		public static final String EYE = "position";
		public static final String DIRECTION = "direction";
		public static final String IMAGE_SIZE = "img_size";
		public static final String SCREEN_SIZE = "screen_size";
	}

	private Vector eye;
	private Vector direction;
	private double screenWidth;
	private double screenHeight;
	private int imageWidth;
	private int imageHeight;

	private List<Pixel> pixels;
	private List<Ray> rays;



	public Camera(Vector eye, Vector direction, double screenHeight, double screenWidth, int imageWidth, int imageHeight)
	{
		this.eye = eye;
		this.direction = direction;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.imageHeight = imageHeight;
		this.imageWidth = imageWidth;

		this.generatePixels();
		this.generateRays();
	}

	public Camera(JSONObject jsonObject)
	{
		this.eye = VectorFactory.createPointVector(jsonObject.getJSONArray(JSON.EYE));
		this.direction = VectorFactory.createVector(jsonObject.getJSONArray(JSON.DIRECTION));

		JSONArray screenSize = jsonObject.getJSONArray(JSON.SCREEN_SIZE);

		this.screenWidth = screenSize.getDouble(0);
		this.screenHeight = screenSize.getDouble(1);

		JSONArray imageSize = jsonObject.getJSONArray(JSON.IMAGE_SIZE);

		this.imageWidth = imageSize.getInt(0);
		this.imageHeight = imageSize.getInt(1);

		this.generatePixels();
		this.generateRays();
	}

	public void generateRays()
	{
		this.rays = new ArrayList<>();

		for(Pixel pixel: this.pixels)
		{
			Ray r = new Ray(this.eye, Vector.subtract(pixel.getLoc(), this.eye));
			this.rays.add(r);
		}
	}

	public void generatePixels()
	{
		this.pixels = new ArrayList<>();

		Vector screenCenter = Vector.add(this.eye, this.direction);
		Vector screenAxis = Vector.crossProduct(this.direction, VectorFactory.createVector(0.0, 1.0, 0.0));
		screenAxis.normalize();

		for(int x = 0; x < this.imageWidth; x++)
		{
			for(int y = 0; y < this.imageHeight; y++)
			{
				double i = ((x - this.imageWidth/2.0)/this.imageWidth)*this.screenWidth;
				double j = ((y - this.imageHeight/2.0)/this.imageHeight)*this.screenHeight;

				Vector pixelPos = VectorFactory.createPointVector(0, j, 0);
				pixelPos = Vector.add(pixelPos, Vector.multiply(screenAxis, i));
				pixelPos = Vector.add(pixelPos, screenCenter);

				pixelPos.makePoint();

				Pixel pixel = new Pixel(x, y, pixelPos);

				this.pixels.add(pixel);
			}
		}
	}

	public List<Pixel> getPixels()
	{
		return this.pixels;
	}

	public List<Ray> getRays()
	{
		return this.rays;
	}

	public Vector getEye()
	{
		return this.eye;
	}

	public Vector getDirection()
	{
		return this.direction;
	}

	public double getScreenWidth()
	{
		return this.screenWidth;
	}

	public double getScreenHeight()
	{
		return this.screenHeight;
	}

	public int getImageWidth()
	{
		return this.imageWidth;
	}

	public int getImageHeight()
	{
		return this.imageHeight;
	}
}

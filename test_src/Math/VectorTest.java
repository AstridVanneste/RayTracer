package Math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Math.Vector;

public class VectorTest
{
	@Test
	public void addTest()
	{
		Vector vec1 = new Vector(3);
		Vector vec2 = new Vector(3);

		vec1.set(0, 3);
		vec1.set(1, 0);
		vec1.set(2, 2);

		vec2.set(0, 4);
		vec2.set(1, 1);
		vec2.set(2, 8);

		Vector result = Vector.add(vec1, vec2);

		assertEquals(7, result.get(0));
		assertEquals(1, result.get(1));
		assertEquals(10, result.get(2));
	}

	@Test
	public void subtractTest()
	{
		Vector vec1 = new Vector(3);
		Vector vec2 = new Vector(3);

		vec1.set(0, 3);
		vec1.set(1, 0);
		vec1.set(2, 2);

		vec2.set(0, 4);
		vec2.set(1, 1);
		vec2.set(2, 8);

		Vector result = Vector.subtract(vec1, vec2);

		assertEquals(-1, result.get(0));
		assertEquals(-1, result.get(1));
		assertEquals(-6, result.get(2));
	}

	@Test
	public void scalarMultiplyTest()
	{
		Vector vec = new Vector(3);
		double scalar = 5;

		vec.set(0, 2);
		vec.set(1, 3);
		vec.set(2, 7);

		Vector result = Vector.multiply(vec, scalar);

		assertEquals(10, result.get(0));
		assertEquals(15, result.get(1));
		assertEquals(35, result.get(2));
	}

	@Test
	public void dotProduct()
	{
		Vector vec1 = new Vector(3);
		Vector vec2 = new Vector(3);


	}
}

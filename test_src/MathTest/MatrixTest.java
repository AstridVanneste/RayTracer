package MathTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Math.Matrix;

public class MatrixTest
{
	@Test
	public void addTest()
	{
		Matrix m1 = new Matrix(3, 3);
		Matrix m2 = new Matrix(3, 3);

		m1.set(0, 0, 1);
		m1.set(0, 1, 2);
		m1.set(0, 2, 3);
		m1.set(1, 0, 4);
		m1.set(1, 1, 5);
		m1.set(1, 2, 6);
		m1.set(2, 0, 7);
		m1.set(2, 1, 8);
		m1.set(2, 2, 9);

		m2.set(0, 0, 9);
		m2.set(0, 1, 8);
		m2.set(0, 2, 7);
		m2.set(1, 0, 6);
		m2.set(1, 1, 5);
		m2.set(1, 2, 4);
		m2.set(2, 0, 3);
		m2.set(2, 1, 2);
		m2.set(2, 2, 1);

		Matrix result = Matrix.add(m1, m2);

		assertEquals(10, result.get(0, 0));
		assertEquals(10, result.get(0, 1));
		assertEquals(10, result.get(0, 2));
		assertEquals(10, result.get(1, 0));
		assertEquals(10, result.get(1, 1));
		assertEquals(10, result.get(1, 2));
		assertEquals(10, result.get(2, 0));
		assertEquals(10, result.get(2, 1));
		assertEquals(10, result.get(2, 2));
	}

	@Test
	public void subtractTest()
	{
		Matrix m1 = new Matrix(3, 3);
		Matrix m2 = new Matrix(3, 3);

		m1.set(0, 0, 1);
		m1.set(0, 1, 2);
		m1.set(0, 2, 3);
		m1.set(1, 0, 4);
		m1.set(1, 1, 5);
		m1.set(1, 2, 6);
		m1.set(2, 0, 7);
		m1.set(2, 1, 8);
		m1.set(2, 2, 9);

		m2.set(0, 0, 9);
		m2.set(0, 1, 8);
		m2.set(0, 2, 7);
		m2.set(1, 0, 6);
		m2.set(1, 1, 5);
		m2.set(1, 2, 4);
		m2.set(2, 0, 3);
		m2.set(2, 1, 2);
		m2.set(2, 2, 1);

		Matrix result = Matrix.subtract(m1, m2);

		assertEquals(-8, result.get(0, 0));
		assertEquals(-6, result.get(0, 1));
		assertEquals(-4, result.get(0, 2));
		assertEquals(-2, result.get(1, 0));
		assertEquals(0, result.get(1, 1));
		assertEquals(2, result.get(1, 2));
		assertEquals(4, result.get(2, 0));
		assertEquals(6, result.get(2, 1));
		assertEquals(8, result.get(2, 2));
	}
}

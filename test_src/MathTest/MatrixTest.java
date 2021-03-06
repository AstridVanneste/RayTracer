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

	@Test
	public void identityMatrixTest()
	{
		Matrix identityMatrix = Matrix.identityMatrix(3);

		assertEquals(1, identityMatrix.get(0, 0));
		assertEquals(0, identityMatrix.get(0, 1));
		assertEquals(0, identityMatrix.get(0, 2));
		assertEquals(0, identityMatrix.get(1, 0));
		assertEquals(1, identityMatrix.get(1, 1));
		assertEquals(0, identityMatrix.get(1, 2));
		assertEquals(0, identityMatrix.get(2, 0));
		assertEquals(0, identityMatrix.get(2, 1));
		assertEquals(1, identityMatrix.get(2, 2));
	}

	@Test
	public void scalarMultiplyTest()
	{
		Matrix m = new Matrix(3, 3);
		double s = 2;

		m.set(0, 0, 1);
		m.set(0, 1, 2);
		m.set(0, 2, 3);
		m.set(1, 0, 4);
		m.set(1, 1, 5);
		m.set(1, 2, 6);
		m.set(2, 0, 7);
		m.set(2, 1, 8);
		m.set(2, 2, 9);

		Matrix result = Matrix.multiply(m, s);

		assertEquals(2, result.get(0, 0));
		assertEquals(4, result.get(0, 1));
		assertEquals(6, result.get(0, 2));
		assertEquals(8, result.get(1, 0));
		assertEquals(10, result.get(1, 1));
		assertEquals(12, result.get(1, 2));
		assertEquals(14, result.get(2, 0));
		assertEquals(16, result.get(2, 1));
		assertEquals(18, result.get(2, 2));
	}

	@Test
	public void multiplyTest()
	{
		Matrix m1 = new Matrix(2, 4);
		Matrix m2 = new Matrix(4, 3);

		m1.set(0, 0, -4);
		m1.set(0, 1, -1);
		m1.set(0, 2, -2);
		m1.set(0, 3, 0);
		m1.set(1, 0, 1);
		m1.set(1, 1, 2);
		m1.set(1, 2, 3);
		m1.set(1, 3, 4);

		m2.set(0, 0, 5);
		m2.set(0, 1, -4);
		m2.set(0, 2, 6);
		m2.set(1, 0, 7);
		m2.set(1, 1, -2);
		m2.set(1, 2, -1);
		m2.set(2, 0, 1);
		m2.set(2, 1, 2);
		m2.set(2, 2, 3);
		m2.set(3, 0, -3);
		m2.set(3, 1, 8);
		m2.set(3, 2, 9);

		Matrix result = Matrix.multiply(m1, m2);

		assertEquals( 2, result.height());
		assertEquals(3, result.width());

		assertEquals(-29, result.get(0, 0));
		assertEquals(14, result.get(0, 1));
		assertEquals(-29, result.get(0, 2));
		assertEquals(10, result.get(1, 0));
		assertEquals(30, result.get(1, 1));
		assertEquals(49, result.get(1, 2));
	}
}

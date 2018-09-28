package RayTracer;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Points extends JPanel
{
	public static void main(String args[])
	{
		Points points = new Points();

		JFrame frame = new JFrame("RayTracer.Points");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(points);
		frame.setSize(250, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);


		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.red);

		for(int i = 0; i <= 10000; i++)
		{
			Dimension size = this.getSize();

			int w = size.width;
			int h = size.height;

			Random r = new Random();

			int x = Math.abs(r.nextInt()) % w;
			int y = Math.abs(r.nextInt()) % h;

			g2d.drawLine(x, y, x, y);
		}
	}
}

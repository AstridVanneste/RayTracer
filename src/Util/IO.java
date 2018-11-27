package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IO
{
	public static String readAllLines(String path) throws IOException
	{
		StringBuilder builder = new StringBuilder();

		BufferedReader reader = new BufferedReader(new FileReader(path));

		String line = reader.readLine();

		while(line != null)
		{
			builder.append(line);
			builder.append("\n");
			line = reader.readLine();
		}

		return builder.toString();
	}
}

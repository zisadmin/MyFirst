//package oata;
import java.io.*;

public class Towers{

	private static BufferedReader stdin = new BufferedReader( new InputStreamReader( System.in ) );

	public static void TowersOfHanoi(String source, String dest, 
					 String temp, int n)
	{
		if(n > 0)
		{
			TowersOfHanoi(source, temp, dest, n-1);
			System.out.println(source+" -> "+dest);
			TowersOfHanoi(temp, dest, source, n-1);
		}
	}

	public static void main(String[] args)
        {

                int n;
                // Prompt the user
                System.out.println("Type a positive integer." );
                try{

                        // Read a line of text from the user.
                        String input = stdin.readLine();

                        // converts a String into an int value
                        n = Integer.parseInt( input );

                	System.out.println("Type the name of the source peg:");
                        String source = stdin.readLine();
                	System.out.println("Type the name of the destination peg:");
                        String dest = stdin.readLine();
                	System.out.println("Type the name of the temporary peg:");
                        String temp = stdin.readLine();
			
			TowersOfHanoi(source, dest, temp, n);

                }
                catch(java.io.IOException e)
                {
                        System.out.println(e);
                }



        } // end of main

} // end of class

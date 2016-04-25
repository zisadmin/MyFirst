import java.io.*;

public class Sudoku
{

	static Set empty = new Set(9);
	static Set fullSet;
	static int [][] board;
	static Set [][] allowedSets;

	public static void main(String [] args)
	{
		initializeBoard();

		if(args.length == 0)
			readGame();
		else
			readGame(args[0]);

		boolean solved = solveGame();

		if(!solved)
			printSets();
		printGame();
	}

	/**
	 * Create board array and create and initialize sets for the
	 * board squares.
	**/
	public static void initializeBoard()
	{
		board = new int[9][9];
		allowedSets = new Set[9][9];

		Set fullSet = new Set(9);
		for(int i = 1; i <= 9; i++)
			fullSet.insert(i);

		// Since no moves have been made, any number can go anywhere.
		// Start the sets out with all possibilities.
		for(int i = 0; i < 9; i++)
			for( int j = 0; j < 9; j++)
				allowedSets[i][j] = new Set(fullSet);
	}

	/**
	 * Output the sets to the command line.
	 * Used when the solver fails to complete a game.
	**/
	public static void printSets()
	{
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++)
				allowedSets[i][j].print();
	}

	/**
	 * Place val into the (x,y) position on the board and update sets
	 * to remove val as a possibility in row x, column y, and the box 
	 * containing (x,y).
	**/
	public static void move(int x, int y, int val)
	{
		board[x][y] = val;
		if(val == 0) // handle 0 moves for readGame()
			return;

		// clear everything out of set (x,y)
		allowedSets[x][y].intersection(empty);

		// clear row x
		for(int i = 0; i < 9; i++)
			allowedSets[x][i].delete(val);

		// clear column y
		for(int i = 0; i < 9; i++)
			allowedSets[i][y].delete(val);

		int boxX = x/3;
		int boxY = y/3;

		// clear the box containing (x,y)
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				allowedSets[3*boxX+i][3*boxY+j].delete(val);
	}

	/**
	 * Read in a game from the command line and put moves on the board
	 *
	**/
	public static void readGame()
	{
		try
		{
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader stdin = new BufferedReader(isr);

			String line = "";

			for(int i = 0; i < 9; i++)
			{
				line = stdin.readLine();
				for(int j = 0; j < 9; j++)
					move(i,j,line.charAt(j)-'0');
			}
		}
		catch(java.io.IOException e)
		{
			System.out.println(e);
		}
	}

	/**
	 * Read in a game from a file and put moves on the board.  The 
	 * filename may be provided as an argument to the program.
	**/
	public static void readGame(String filename)
	{
		try
		{
			FileReader fr = new FileReader(filename);
			BufferedReader stdin = new BufferedReader(fr);

			String line = "";

			for(int i = 0; i < 9; i++)
			{
				line = stdin.readLine();
				for(int j = 0; j < 9; j++)
					move(i,j,line.charAt(j)-'0');
			}
		}
		catch(java.io.IOException e)
		{
			System.out.println(e);
		}
	}

	/**
	 * Get the first element of a set.
	 * If the set has one element, return that element.
	**/
	public static int getElement(Set s)
	{
		for(int i = 1; i <= 9; i++)
			if(s.isMember(i))
				return i;

		System.out.println("uh oh");
		return 0;
	}

	/**
	 * Search the board for a space with only one possibility.  If found, 
	 * place that move on the board and update sets.  Continue until 
	 * either no move is made, or there are no possiblities in any squares.
	**/
	public static boolean solveGame()
	{
		boolean solved = false;
		boolean moved = false;
		int moves = 0;
		while(!solved)
		{
			solved = true;
			moved = false;
			for(int i = 0; i < 9; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					if(allowedSets[i][j].size() == 1)
					{
						move(i,j,getElement(allowedSets[i][j]));
						moved = true;
						moves++;
					}
					else if(!allowedSets[i][j].isEmpty())
						solved = false;
				}
			}
			if(!moved) // Give up if no moves were found
				break;
		}

		System.out.println("moves made: " + moves);
		return solved;
	}

	/**
	 * Output the current board position.
	 *
	**/
	public static void printGame()
	{
		System.out.println();
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
				System.out.print(board[i][j]);
			System.out.println();
		}
			
	}
}
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class TokenMover implements ActionListener 
{
	
	private Game gameDriver;
	private Pawn pawn;
	private GameTable gameTable;
	private int location;
	private int direction;
	private JPanel gameBoard;
	
	public TokenMover(Game gameDriver, Pawn pawn, GameTable gameTable, int location, int direction, JPanel gameBoard) 
	{
		// direction : 1 --> forward
		// direction : -1 --> backward

		this.gameDriver = gameDriver;
		this.pawn = pawn;
		this.gameTable= gameTable;
		this.location = location;
		this.direction = direction;
		this.gameBoard = gameBoard;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int current = pawn.getLocation();
		if (current == location) 
		{
			Timer timer = (Timer)e.getSource();
            timer.stop();
            gameDriver.pawnPlaced();
		}
		
		else 
		{
			int targetLocation = mod(current + direction, 40);
			gameTable.placePawnOn(pawn, targetLocation);
			gameBoard.repaint();
		}
	}
	
	private int mod (int n, int d) 
	{
		int q = floor((double)n/d);
		return n - (q*d);
	}
	
	private int floor(double n) 
	{
		int f = (int) n;
		
		if (n < 0 && ((int)n) != n)
			f = ((int)n) - 1;
		
		return f;
	}
	
}

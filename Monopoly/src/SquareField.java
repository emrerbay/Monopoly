import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class SquareField implements Serializable{

	private int location;
	private Point leftTop, rightTop, leftBottom, rightBottom;
	private GameObject object;
	private ArrayList<Pawn> pawns;
	
	public SquareField(int location, Point rightTop, Point leftBottom, GameObject object) 
	{
		this(location, rightTop, leftBottom);
		this.object = object;
	}


	public SquareField(int location, Point rightTop, Point leftBottom) 
	{
		this.location = location;
		this.rightTop = rightTop;
		this.leftBottom = leftBottom;
		determineOtherPoints();
		pawns = new ArrayList<Pawn>();
	}
	
	private void determineOtherPoints() 
	{
		leftTop = new Point(leftBottom.x, rightTop.y);
		rightBottom = new Point(rightTop.x, leftBottom.y);
	}
	
	public void setLocation (int location) 
	{
		this.location = location;
	}
	
	public int getLocation () 
	{
		return location;
	}
	
	public void setObject (GameObject object) 
	{
		this.object = object;
	}
	
	
	public GameObject getObject() 
	{
		return object;
	}
	
	public void placePawn(Pawn pawn) 
	{
		if (!isPawnContained(pawn)) 
		{
			SquareField oldPosition = pawn.getSquareField();
			oldPosition.removePawn(pawn);
			pawn.determineThePositionOn(this);
			pawns.add(pawn);
			pawn.setSquareField(this);
			pawn.setLocation(location);
		}
	}
	
	private boolean isPawnContained(Pawn pawn) 
	{
		boolean contained = false;
		
		for (Pawn p : pawns) 
			if (p.equals(pawn))
				contained = true;
			
	
		return contained;
	}
	
	public void removePawn(Pawn pawn)
	{
		int index = -1;
		for (int i = 0; i < pawns.size(); i++) 
		{
			if (pawns.get(i).equals(pawn)) 
			{
				index = i;	
			}
		}
		
		if (index != -1)
			pawns.remove(index);
		
		replacePawns();
	}
	
	private void replacePawns() 
	{
		ArrayList<Pawn> tempPawns = new ArrayList<Pawn>();
		for (Pawn p : pawns) 
		{
			tempPawns.add(p);
		}
		
		pawns = new ArrayList<Pawn>();

		for (Pawn pawn: tempPawns) 
		{
			pawn.determineThePositionOn(this);
			pawns.add(pawn);
			pawn.setSquareField(this);
			pawn.setLocation(location);
		}
	}
	
	public ArrayList<Pawn> getPawns() 
	{
		return pawns;
	}
	
	public boolean containsPoint(int x, int y) 
	{
		return leftTop.x < x && x < rightTop.x && leftTop.y< y && y < leftBottom.y;
	}
	
	public Point getRightTop() 
	{
		return rightTop;
	}
	
	public Point getRightBottom() 
	{
		return rightBottom;
	}
	
	public Point getLeftTop() 
	{
		return leftTop;
	}
	
	public Point getLeftBottom() 
	{
		return leftBottom;
	}
	
	public int getXDistanceToRightBorder(int x) 
	{
		return rightTop.x - x;
	}
	
	public int getXDistanceToLeftBorder(int x) 
	{
		return leftTop.x - x;
	}
	
	public int getYDistanceToBottomBorder(int y) 
	{
		return rightBottom.y - y;
	}
	
	public int getYDistanceToTopBorder(int y) 
	{
		return rightTop.y - y; 
	}
	
	public boolean areTherePawnsAtTheLeftSideOf(int x, int y) 
	{
		boolean areThere = false;
		for (Pawn p: pawns) 
		{
			Point pos = p.getPosition();
			if (pos.x < x && y == pos.y)
				areThere = true;
		}
		
		return areThere;
	}
	
	public boolean areTherePawnsAboveOf(int x, int y) 
	{
		boolean areThere = false;
		for (Pawn p: pawns) 
		{
			Point pos = p.getPosition();
			if (pos.y < y && x == pos.x)
				areThere = true;
		}
		return areThere;
	}
	
}

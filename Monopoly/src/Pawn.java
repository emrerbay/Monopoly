import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class Pawn implements Serializable
{
	private int location;
	private SquareField squareField;
	private Player owner;
	private int colorID;
	private String colorName;
	private Point position;
	private Color color;
	private boolean hidden;
	
	public Pawn(int location, SquareField square, Player owner, int colorID)  
	{
		this.location = location;
		squareField = square;
		this.owner = owner;
		this.colorID = colorID;
		determineTheColor();
		hidden = false;
		square.placePawn(this);
	}
	
	public void hide() 
	{
		hidden = true;
	}
	
	public boolean isHidden() 
	{
		return hidden;
	}
	
	public void determineTheColor() 
	{
		switch (colorID) 
		{
			case 1:
				colorName = "Red";
				color = Color.RED;
				break;
			case 2:
				colorName = "Blue";
				color = Color.BLUE;
				break;
			case 3:
				colorName = "Magenta";
				color = Color.MAGENTA;
				break;
			case 4:
				colorName = "Orange";
				color = Color.ORANGE;
				break;
			case 5:
				colorName = "Green";
				color = Color.GREEN;
				break;
			case 6:
				colorName = "White";
				color = Color.WHITE;
				break;
			case 7:
				colorName = "Cyan";
				color = Color.CYAN;
				break;
			case 8:
				colorName = "Gray";
				color = Color.GRAY;
				break;
		}
	}
	
	public void determineThePositionOn(SquareField field) 
	{
		if (field.getPawns().size() == 0) 
		{
			position = new Point(field.getLeftTop().x+3, field.getLeftTop().y+3);
		}
		
		else 
		{
			Point lastPawnPosition = field.getPawns().get(field.getPawns().size()-1).getPosition();
			int x = lastPawnPosition.x;
			int y = lastPawnPosition.y;
			
			if (field.getXDistanceToRightBorder(x+10+5) > 5) 
			{
				position = new Point(x+15, y);
			}
			else if (field.getXDistanceToLeftBorder(x-10-5) > 5 && !field.areTherePawnsAtTheLeftSideOf(x, y))
			{
				position = new Point(x-15, y);
			}
			else if (field.getYDistanceToBottomBorder(y+10+5) > 5)
			{
				position = new Point(x, y+15);
			}
			else if (field.getYDistanceToTopBorder(y-10-5) > 5 && !field.areTherePawnsAboveOf(x, y))
			{
				position = new Point(x, y-15);
			}
			else 
			{
				System.out.println("Fatal error! Couldn't place the pawn.");
			}
		}
	}
	
	public Point getPosition() 
	{
		return position;
	}
	
	public void setPosition(Point pos) 
	{
		position = pos;
	}

	
	public int getLocation() 
	{
		return location;
	}
	
	public SquareField getSquareField() 
	{
		return squareField;
	}
	
	public Player getOwner() 
	{
		return owner;
	}
	
	public void setLocation(int location) 
	{
		this.location = location;
	}
	
	public void setSquareField(SquareField newSquareField) 
	{
		squareField = newSquareField;
	}
	
	public void setOwner(Player owner) 
	{
		this.owner = owner;
	}
	
	public Color getColor() 
	{
		return color;
	}
	
	public String getColorName() 
	{
		return colorName;
	}
	
	public boolean equals(Pawn p) 
	{
		return p.getOwner().getID() == owner.getID();
	}
	
	public String toString() 
	{
		return String.format("Pawn: Owner: %s ColorName: %s Position: (%d, %d)", owner, colorName, position.x, position.y);
	}
	
}

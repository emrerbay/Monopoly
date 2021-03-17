import java.io.Serializable;
import java.util.ArrayList;

public class GameTable implements Serializable{

	private ArrayList<SquareField> fields;
	
	public GameTable (ArrayList<SquareField> fields) 
	{
		this.fields = fields;
	}
	
	public void placePawnOn(Pawn pawn, int location) 
	{
		
		fields.get(location).placePawn(pawn);
		
	}
	
	public String getInformationOf(int loc) 
	{
		
		return fields.get(loc).getObject().getInformation();
		
	}
	
	public int getLocationFromPos(int x, int y) 
	{
		int location = -1;
		for(SquareField f: fields)	
			if (f.containsPoint(x, y))
				location = f.getLocation();
		
		return location;
	}
	
	
	public ArrayList<SquareField> getFields() 
	{
		return fields;
	}
	
	public void setFields(ArrayList<SquareField> fields) 
	{
		this.fields = fields;
	}
	
	public void addField(SquareField field) 
	{
		fields.add(field);
	}
	
	public SquareField getSquare(int location) 
	{
		return fields.get(location);
	}
	
	public ArrayList<Land> getLands() 
	{
		ArrayList<Land> lands = new ArrayList<Land>();
		for (SquareField f: fields) 
		{
			GameObject obj = f.getObject();
			if (obj.getType().equals("land")) 
			{
				lands.add((Land)obj);
			}
		}
		return lands;
	}
	
	public ArrayList<Land> getLandsInColorGroup(int colorID) 
	{
		ArrayList<Land> lands = new ArrayList<Land>();
		ArrayList<Land> allLands = getLands();
		
		for (Land l: allLands) 
		{
			if (l.getColorID() == colorID) 
			{
				lands.add(l);
			}
		}
		return lands;
	}
	
}

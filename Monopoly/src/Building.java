
public abstract class Building extends Property 
{
	private SquareField squareField;
	
	public Building(String type, String title, String description, int id, SquareField squareField) 
	{
		super(type, title, description, id);
		this.squareField = squareField;
	}
	
	public SquareField getSquareField() 
	{
		return squareField;	
	}
	
	public void setSquareField(SquareField squareField) 
	{
		this.squareField = squareField;
	}

	public abstract String toString();
	
	
}

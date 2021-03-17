
public class Hotel extends Building
{

	public Hotel(String title, String description, int id, SquareField squareField) 
	{
		super("hotel", title, description, id, squareField);
	}
	
	public String toString() 
	{
		return "Hotel on " + getSquareField().getObject().getTitle();
	}
	
}
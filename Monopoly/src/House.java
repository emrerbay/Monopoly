
public class House extends Building
{

	public House(String title, String description, int id, SquareField squareField) 
	{
		super("house", title, description, id, squareField);
	}
	
	public boolean equals(House h) 
	{
		return h.getID() == getID();
	}

	public String toString() 
	{
		return "House on " + getSquareField().getObject().getTitle();
	}
	
}

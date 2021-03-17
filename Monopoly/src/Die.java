import java.io.Serializable;
import java.util.Random;

public class Die implements Serializable
{

	private int face;
	
	public Die() 
	{
		this(1);
	}
	
	public Die(int face) 
	{
		this.face = face;
	}
	
	public int getFace() 
	{
		return face;
	}
	
	public void setFace(int face) 
	{
		this.face = face;	
	}
	
	public int roll() 
	{
		Random generator = new Random();
		face = generator.nextInt(6) + 1;
		return face;
		
	}
	
	
}

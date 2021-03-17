import java.util.Random;

public class LoseCard extends TriggeredCard 
{

	// bu class, GiftCard class�na benzer bir �ekilde tasarlanm��t�r. (bkz. GiftCard.java'daki a��klamalar)
	// �ans kart� �ekildi�inde, oyuncuya x dolar kaybettiren kartt�r.
	
	private double loseAmount;
	private double [] possibleAmounts;
	private final int NUMBER_OF_POSSIBLE_AMOUNTS = 4;
	private final double INITIAL_AMOUNT = 50.0;
	private final double INCREASE_BY = 50.0;
	
	public LoseCard() 
	{
		super("Oops!");
		setType("lose");
		possibleAmounts = new double[NUMBER_OF_POSSIBLE_AMOUNTS];
		double initialAmount = INITIAL_AMOUNT;
		for (int i = 0; i < possibleAmounts.length; i++) 
		{
			possibleAmounts[i] = initialAmount;
			initialAmount += INCREASE_BY;
		}
		
	}
	
	public void trigger() 
	{
		
		Random generator = new Random();
		int randInt = generator.nextInt(possibleAmounts.length);
		
		loseAmount = possibleAmounts[randInt];
		super.setDescription("You lost $" + loseAmount + "!");
	}
	
	public double getLoseAmount() 
	{
		return loseAmount;
	}
	

}

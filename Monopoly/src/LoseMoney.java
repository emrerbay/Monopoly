
public class LoseMoney extends GameObject
{
	private double amount;
	
	public LoseMoney(int id, double amount) 
	{
		super("losemoney", "Lose $" + (int)amount, "Whoever comes here loses $" + (int)amount, id);
		this.amount = amount;		
		
	}
	
	public double getAmount() 
	{
		return amount;
	}
	
	public void setAmount(double amount) 
	{
		this.amount = amount;
	}
}

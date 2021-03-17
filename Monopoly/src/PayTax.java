
public class PayTax extends GameObject
{

	// Pay Tax karelerini temsil etmek i�in kullan�l�r.
	
	// ne kadar vergi �denece�ini belirtmek i�in:
	private double amount;
	
	public PayTax(int id, double amount) 
	{
		
		
		super("paytax", "Pay $" + (int)amount + " Tax", "Whoever comes here has to pay $" + (int)amount + " tax.\nPaid taxes are accumulated in Free Parking area.", id);
		// GameObject class�n�n constructor�n� yukar�daki verileri kullanarak �a��r�r. bkz. GameObject.java constructor
		
		
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

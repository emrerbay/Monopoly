
public class Public extends Property{

	private double price;
	private double incomePerTurn;
	
	public Public(String title, String description, int id, double price, double rentPerTurn) 
	{
		super("public", title, description, id);
		this.price = price;
		this.incomePerTurn = rentPerTurn;
		
	}
	
	public double getPrice() 
	{
		return price;
	}

	public void setPrice(double price) 
	{
		this.price = price;
	}
	
	public double getIncomePerTurn() 
	{
		
		return incomePerTurn;
		
	}
	
	public void setIncomePerTurn(double incomePerTurn) 
	{
		this.incomePerTurn = incomePerTurn;
	}
	
	@Override
	public String getInformation() 
	{
		
		String info = super.getInformation();
		info += "Price: $" + price + "\n" + "Income Per Turn: $" + incomePerTurn + "\n";
		return info;
		
	}
	
	public boolean equals(Public p) 
	{
		return p.getID() == getID() && p.getDescription().equals(description) && p.getTitle().equals(title);
	}
	
	public String toString() 
	{
		return title;
	}
}

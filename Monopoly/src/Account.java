import java.io.Serializable;

public class Account implements Serializable
{

	private double money;
	private Player owner;
	
	public Account(Player player) 
	{
		owner = player;
	}
	
	public Account(double money) 
	{
		this.money = money;
	}
	
	public Account (Player player, double money) 
	{
		this(player);
		this.money = money;
	}
	
	public boolean deposit (double amount) 
	{
		
		money += amount;
		return true;
		
	}
	
	public boolean withdraw (double amount) 
	{
		boolean successful = true;
		if (amount <= money) 
		{
			money -= amount;
		}
		else 
		{
			successful = false;
		}
		
		return successful;
	}
	
	public double getMoney() 
	{
		
		return money;
		
	}
	
	public void setMoney(double amount) 
	{
		money = amount;
	}
	
	public Player getOwner() 
	{
		return owner;
	}
	
	public void setOwner(Player player) 
	{
		this.owner = player;
	}
	
	public String toString() 
	{
		
		if (owner != null) 
		{
			return String.format("%s's account : ", owner) + "$" + money;
		}
		
		else 
		{
			return "Free Parking's account : $" + money;
		}
		
	}
	
}

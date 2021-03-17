
public class FreeParking extends GameObject 
{
	private Account account;
	
	public FreeParking (String title, String description, int id)
	{
		super("freeparking", title, description, id);
		account = new Account(0.0);
	}
	
	public Account getAccount() 
	{
		return account;
	}
	
	public void setAccount(Account account) 
	{
		this.account = account;
	}
	
	public double getMoneyAmount ()
	{
		
		return account.getMoney();
		
	}
	
	public void giveMoneyTo(Player player) 
	{
		
		player.earnMoney(account.getMoney());
		account.withdraw(account.getMoney());
		
	}
	
	public void putMoneyIn(double amount) 
	{
		account.deposit(amount);
	}
	
	@Override
	public String getInformation() 
	{
		
		String info = super.getInformation();
		
		info += "Amount of money accumulated : $" + account.getMoney();
		return info;
	}
	

}

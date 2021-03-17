
public abstract class Property extends GameObject {

	protected Player owner;
	protected boolean hasOwner;
	
	public Property(String type, String title, String description, int id) 
	{
		super(type, title, description, id);
		owner = null;
		hasOwner = false;
	}
	
	public Player getOwner() 
	{
		return owner;
	}
	
	public void setOwner(Player player) 
	{
		owner = player;
		if (player == null) 
			hasOwner = false;
	}
	
	public boolean isOwned() 
	{
		return hasOwner;
	}
	
	public void assignOwner(Player player) 
	{
		setOwner(player);
		hasOwner = true;
	}

	@Override
	public String getInformation() 
	{
		
		String info = super.getInformation();
		
		if (!hasOwner) 
		{
			info += "This property does not have an owner.\n";
		}
		else 
		{
			info += "Owner of this place is " + owner + "\n";
		}

		return info;
		
	}
	
}

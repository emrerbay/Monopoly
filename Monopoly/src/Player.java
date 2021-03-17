import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{

	private Account bankAccount;
	private String name;
	private boolean inGame;
	private boolean inJail;
	private static int idCounter = 1;
	private int ID;
	private int jailTurnCount = 0;
	private ArrayList<Property> properties;
	private ArrayList<House> houses;
	private ArrayList<Hotel> hotels;
	private ArrayList<Land> lands;
	private ArrayList<Public> publicFields;
	private Pawn pawn;
	
	public Player(String name) 
	{
		this.name = name;
		ID = idCounter;
		idCounter++;
		inGame = true;
		bankAccount = new Account(this);
		properties = new ArrayList<Property>();
		houses = new ArrayList<House>();
		hotels = new ArrayList<Hotel>();
		lands = new ArrayList<Land>();
		publicFields = new ArrayList<Public>();
	}
	
	public void setMoney(double amount) 
	{
		bankAccount.setMoney(amount);
	}
	
	public void loseTheGame() 
	{
		this.inGame = false;
	}
	
	public void goToJail() 
	{
		this.inJail = true;
	}
	
	public void earnMoney(double amount) 
	{
		bankAccount.deposit(amount);
	}
	
	public boolean loseMoney(double amount) 
	{
		return bankAccount.withdraw(amount);
	}
	
	public boolean payRentTo(Player player, double amount) 
	{
		boolean successful = true;
		
		if (loseMoney(amount)) 
		{
			player.earnMoney(amount);
		}
		else 
		{
			successful = false;
		}
		
		return successful;
	}
	
	public boolean canAfford (double amount) 
	{
		
		boolean answer = true;
		if (bankAccount.getMoney() < amount) 
		{
			answer = false;
		}
		
		return answer;
	}

	public Account getBankAccount() 
	{
		return bankAccount;
	}
	
	public void setBankAccount(Account acc) 
	{
		this.bankAccount = acc;
	}
	
	public String getName () 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public int getID() 
	{
		return ID;
	}
	
	public ArrayList<Property> getProperties() 
	{
		return properties;
	}
	
	public boolean isInGame() 
	{
		return inGame;
	}
	
	public boolean isInJail()
	{
		return inJail;
	}
	
	public void setPawn(Pawn pawn) 
	{
		this.pawn = pawn;
	}
	
	public Pawn getPawn() 
	{
		return pawn;
	}
	
	public ArrayList<House> getHouses() 
	{
		return houses;
	}
	
	public ArrayList<Hotel> getHotels() 
	{
		return hotels;
	}
	
	private void jailCounter() 
	{
		if (inJail) 
		{
			jailTurnCount += 1;
			if (jailTurnCount == 2) 
			{
				jailTurnCount = 0;
				inJail = false;
			}
		}
	}
	

	private void earnMoneyPerTurn(MainWindow gameWindow) 
	{
		for (Public p: publicFields) 
		{
			gameWindow.addTextToTextArea(this + " earns $" + p.getIncomePerTurn() + " since they own " + p.getTitle() + ".\n");
			earnMoney(p.getIncomePerTurn());
		}
	}
	
	public void doTasksPerTurn(MainWindow gameWindow) 
	{
		earnMoneyPerTurn(gameWindow);
		jailCounter();
	}
	
	public void loseAllProperties(MainWindow gameWindow, FreeParking freeParking) 
	{
		double amount = getBankAccount().getMoney();
		
		if (amount > 0) 
		{
			loseMoney(amount);
			freeParking.putMoneyIn(amount);
			gameWindow.addTextToTextArea("All money of " + this + " ($" + amount + ") was put in Free Parking area.\n");
		}
		
		if (properties.size() > 0) 
		{
			for (Property property: properties) 
			{
				property.setOwner(null);
				gameWindow.addTextToTextArea(String.format("%s is now unowned!", property));
			}
		}
		
		clearPropertyLists();
	}
	
	public void giveAllPropertiesTo(Player p, MainWindow gameWindow) 
	{
		double amount = bankAccount.getMoney();
		if (amount > 0) 
		{
			this.loseMoney(amount);
			p.earnMoney(amount);
			gameWindow.addTextToTextArea(p + " takes all money of " + this + "($" + amount + ")!\n");
		}
		else 
		{
			gameWindow.addTextToTextArea(p + " could not take any money from " + this + "\nsince " + this + "did not have any money!\n");
		}
		
		if (properties.size() > 0) 
		{
			for (Property property : properties) 
			{
				property.assignOwner(p);
				p.addProperty(property);
				gameWindow.addTextToTextArea(String.format("%s takes %s from %s!\n", p, property, this));
			}

		}
		else 
		{
			gameWindow.addTextToTextArea(p + " could not take any property from " + this + "\nsince " + this + "did not have any property!\n");
		}
		
		clearPropertyLists();
	}
	
	private void addProperty(Property property) 
	{
		String type = property.getType();
		properties.add(property);
		switch(type) 
		{
			case "house":
				houses.add((House)property);
				break;
			case "hotel":
				hotels.add((Hotel) property);
				break;
			case "public":
				publicFields.add((Public) property);
				break;
			case "land":
				lands.add((Land) property);
				break;
		}
		
	}
	
	private void clearPropertyLists() 
	{
		properties = new ArrayList<Property>();
		lands = new ArrayList<Land>();
		houses = new ArrayList<House>();
		hotels = new ArrayList<Hotel>();
		publicFields = new ArrayList<Public>();
	}
	
	private boolean areHouseNumbersEqual (ArrayList<Land> lands) 
	{
		boolean answer = true;
		int initial = getPseudoHouseNumbers(lands.get(0));
		
		for (int i = 1; i < lands.size(); i++) 
		{
			if (initial != getPseudoHouseNumbers(lands.get(i))) 
			{
				answer = false;
			}
		}
		
		return answer;
	}
	
	private int getPseudoHouseNumbers (Land land) 
	{
		int noh = land.getNumberOfHouses();
		
		if (land.isThereAHotel()) 
		{
			noh += land.getHouseLimit() + 1;
		}
		
		return noh;
	}
	
	
	private boolean ownsTheLands(ArrayList<Land> lands) 
	{
		boolean answer = true;
		for (Land l : lands) 
		{
			if (!l.isOwned() || !l.getOwner().equals(this)) 
			{
				answer = false;
			}
		}
		return answer;
	}
	
	private int getMax(int[] arr) 
	{
		int max = arr[0];
		for (int i = 1; i < arr.length; i++) 
		{
			if (arr[i] > max) 
			{ 
				max = arr[i];
			}	
		}
		return max;
	}
	
	public ArrayList<Land> getLands () 
	{
		return lands;
	}
	
	public ArrayList<Public> getPublicFields() 
	{
		return publicFields;
	}
	
	public boolean canBuildHotel(GameTable gameTable) 
	{
		boolean answer = false;
		GameObject object = pawn.getSquareField().getObject();
		
		if (object.getType().equals("land") && canAfford(((Land)object).getHotelBuildingPrice()) && !(((Land)object).isThereAHotel()) && ((Land)object).getNumberOfHouses() == ((Land)object).getHouseLimit()) 
		{
			Land landObject = (Land) object;
			ArrayList<Land> landsInSameColorGroup = gameTable.getLandsInColorGroup(landObject.getColorID());
			boolean areLandsOwned = ownsTheLands(landsInSameColorGroup);

			if (areLandsOwned) 
			{
				boolean houseEqualityCondition = false;
				if (areHouseNumbersEqual(landsInSameColorGroup)) 
				{
					houseEqualityCondition = true;
				}
				else 
				{
					int [] houseNumbers = new int[landsInSameColorGroup.size()];
					for (int i = 0; i < houseNumbers.length; i++) 
					{
						houseNumbers[i] = getPseudoHouseNumbers(landsInSameColorGroup.get(i));
					}
					int max = getMax(houseNumbers);
					if (max != landObject.getNumberOfHouses()) 
					{
						houseEqualityCondition = true;
					}
				}
				answer = houseEqualityCondition;
			}
		}
		
		return answer;
	}
	
	public boolean canBuildHouse(GameTable gameTable) 
	{
		boolean answer = false;
		GameObject object = pawn.getSquareField().getObject();
		
		if (object.getType().equals("land") && canAfford(((Land)object).getHouseBuildingPrice()) && ((Land)object).getNumberOfHouses() < ((Land)object).getHouseLimit() && !(((Land)object).isThereAHotel())) 
		{
			Land landObject = (Land) object;
			ArrayList<Land> landsInSameColorGroup = gameTable.getLandsInColorGroup(landObject.getColorID());
			boolean areLandsOwned = ownsTheLands(landsInSameColorGroup);
			if (areLandsOwned) 
			{
				boolean houseEqualityCondition = false;
				
				if (areHouseNumbersEqual(landsInSameColorGroup)) 
				{
					houseEqualityCondition = true;
				}
				else 
				{
					int [] houseNumbers = new int[landsInSameColorGroup.size()];
					for (int i = 0; i < houseNumbers.length; i++) 
					{
						houseNumbers[i] = getPseudoHouseNumbers(landsInSameColorGroup.get(i));
					}
					int max = getMax(houseNumbers);
					if (max != landObject.getNumberOfHouses()) 
					{
						houseEqualityCondition = true;
					}
				}
				answer = houseEqualityCondition;
			}
		}
		
		return answer;
	}
	
	public void buy() 
	{
		GameObject object = pawn.getSquareField().getObject();
		if (object.getType().equals("land")) 
		{
			Land landObj = (Land)object;
			landObj.assignOwner(this);
			properties.add(landObj);
			lands.add(landObj);
			double price = landObj.getPrice();
			loseMoney(price);
			
			for (House h: landObj.getHouses()) 
			{
				h.setOwner(this);
			}
			
			if (landObj.isThereAHotel()) 
			{
				landObj.getHotel().setOwner(this);
			}
		}
		else if (object.getType().equals("public")) 
		{
			Public publicObj = (Public)object;
			publicObj.assignOwner(this);
			properties.add(publicObj);
			publicFields.add(publicObj);
			double price = publicObj.getPrice();
			loseMoney(price);
		}
	}
	
	public void buildHouse() 
	{
		House house = new House("House", this +"'s House", pawn.getLocation(), pawn.getSquareField());
		house.setOwner(this);
		Land landObj = (Land)(pawn.getSquareField().getObject());
		landObj.buildHouse(house);
		loseMoney(landObj.getHouseBuildingPrice());
		properties.add(house);
		houses.add(house);
	}
	
	public void buildHotel() 
	{
		Hotel hotel = new Hotel("Hotel", this +"'s Hotel", getPawn().getLocation(), pawn.getSquareField());
		hotel.setOwner(this);
		Land landObj = (Land)(pawn.getSquareField().getObject());
		landObj.buildHotel(hotel);
		loseMoney(landObj.getHotelBuildingPrice());
		properties.add(hotel);
		hotels.add(hotel);
	}

	public void removeHotelInLand(Land l) 
	{
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		for (Hotel h: this.hotels) 
		{
			if (((Land) h.getSquareField().getObject()).getID() != l.getID()) 
			{
				hotels.add(h);
			}
		}
		
		this.hotels = hotels;
	}
	
	public void removeHousesInLand(Land l) 
	{
		
		ArrayList<House> houses = new ArrayList<House>();
		for (House h: this.houses) 
		{
			if (((Land) h.getSquareField().getObject()).getID() != l.getID()) 
			{
				houses.add(h);
			}
		}
		
		this.houses = houses;
	}
	
	public void sellPropertyTo (Property p, Player buyer, double price) 
	{
		
		if (p.getType().equals("land")) 
		{
			sellLandTo((Land) p, buyer, price);
		}
		else if (p.getType().equals("public")) 
		{
			sellPublicServiceTo((Public) p, buyer, price);
		}
		
	}
	
	private void sellPublicServiceTo(Public p, Player buyer, double price) 
	{
		earnMoney(price);
		buyer.loseMoney(price);
		
		publicFields.remove(p);
		System.out.println(p.getInformation());
		System.out.println(p.getType());
		buyer.addProperty(p);
		
		p.assignOwner(buyer);
	}
	
	private void sellLandTo(Land l, Player buyer, double price) 
	{
		
		earnMoney(price);
		buyer.loseMoney(price);
		for (House h: l.getHouses()) 
		{
			h.assignOwner(buyer);
			buyer.addProperty(h);
		}
		removeHousesInLand(l);
		
		if (l.isThereAHotel()) 
		{
			l.getHotel().assignOwner(buyer);
			buyer.addProperty(l.getHotel());
			removeHotelInLand(l);
		}
		
		l.assignOwner(buyer);
		buyer.addProperty(l);
		lands.remove(l);
		
	}
	
	public boolean canBuy() 
	{
		boolean answer = false;
		GameObject object = pawn.getSquareField().getObject();
		if (object.getType().equals("land") || object.getType().equals("public")) 
		{
			
			double price = 0;
			Property property = (Property) object;
			if (object.getType().equals("land")) 
			{
				price = ((Land)property).getPrice();
			}
			else 
			{
				price = ((Public)property).getPrice();
			}
			
			if (!property.isOwned() && bankAccount.getMoney() >= price) 
			{
				answer = true;
			}
		}
		
		return answer;
		
	}
	
	public String getPropertyInformation() 
	{
		String str = "";
		if (properties.size() == 0)
		{
			str = toString() + " does not own anything.";
		}
		else 
		{
			str = toString() + " owns:\n";
			for(Land l: lands) 
			{
				str += "\t" + l + "\n";
			}
			
			for (Public p: publicFields) 
			{
				str += "\t" + p + "\n";
			}
			
			for (Land l: lands) 
			{
				if (l.getNumberOfHouses() > 0) 
				{
					String word = l.getNumberOfHouses() > 1 ? "houses" : "house";
					str += "\t" + l.getNumberOfHouses() + " " + word + " on " + l.getTitle() + "\n";
				}
			}
			
			for (Hotel h: hotels) 
			{
				str += "\t" + h + "\n";
			}

		}
		return str;
	}
	
	public static void setIDCounter(int val) 
	{
		idCounter = val;
	}
	
	public String toString() 
	{
		return String.format("Player%d (%s)", ID, name);
	}
}

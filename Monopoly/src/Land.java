import java.io.Serializable;
import java.util.ArrayList;

public class Land extends Property implements Serializable
{

	private int colorID;
	private String colorName;
	private double rentPerTurn;
	private ArrayList<House> houses;
	private Hotel hotel;
	private boolean hotelExists;
	private double price;
	private double houseBuildingPrice;
	private double hotelBuildingPrice;
	private final double HOUSE_RENT_MULTIPLE_COEFFICIENT = 1.5;
	private final double HOTEL_RENT_MULTIPLE_COEFFICIENT = 8;
	private final double HOUSE_PRICE_MULTIPLE_COEFFICIENT = 1.6;
	private final double HOTEL_PRICE_MULTIPLE_COEFFICIENT = 8.3;
	private final int MAXIMUM_NUMBER_OF_HOUSES = 4;
	
	public Land(String title, String description, int id, double price, int colorID, double rentPerTurn) 
	{
		
		super("land", title, description, id);
		this.colorID = colorID;
		this.price = price;
		this.rentPerTurn = rentPerTurn;
		this.houseBuildingPrice = price * 1.5;
		this.hotelBuildingPrice = price * 4;
		houses = new ArrayList<House>();
		determineTheColorName();
		
	}
	
	public Land(String title, String description, int id, double price, int colorID, double rentPerTurn, double houseBuildingPrice) 
	{
		this(title, description, id, price, colorID, rentPerTurn);
		this.houseBuildingPrice = houseBuildingPrice;
	}

	public boolean isThereAHouse() 
	{
		return houses.size() > 0;
	}
	
	public boolean isThereAHotel() 
	{
		
		return hotelExists;
		
	}
	
	public int getNumberOfHouses() 
	{
		return houses.size();
	}
	
	public void determineTheColorName() 
	{
		
		switch(colorID) 
		{
			case 0:
				colorName = "Purple";
				break;
			case 1:
				colorName = "Light Blue";
				break;
			case 2:
				colorName = "Pink";
				break;
			case 3:
				colorName = "Orange";
				break;
			case 4:
				colorName = "Red";
				break;
			case 5:
				colorName = "Yellow";
				break;
			case 6:
				colorName = "Green";
				break;
			case 7:
				colorName = "Blue";
				break;
		}
		
	}
	
	public int getColorID () 
	{
		return colorID;
	}
	
	public String getColorName() 
	{
		return colorName;
	}
	
	public void setColorID (int colorID) 
	{
		this.colorID = colorID;
	}
	
	public void setColorName (String colorName)
	{
		this.colorName = colorName;
	}
	
	
	public double getHouseBuildingPrice() 
	{
		return houseBuildingPrice;
	}
	
	public void setHouseBuildingPrice (double houseBuildingPrice) 
	{
		this.houseBuildingPrice = houseBuildingPrice;
	}
	
	public double getHotelBuildingPrice() 
	{
		return hotelBuildingPrice;
	}
	
	public void setHotelBuildingPrice (double hotelBuildingPrice) 
	{
		this.hotelBuildingPrice = hotelBuildingPrice;
	}
	
	public double getPrice() 
	{
		return calculatePrice();
	}
	
	public void setPrice(double price) 
	{
		this.price = price;
	}
	
	public ArrayList<House> getHouses() 
	{
		return houses;
	}
	
	public void setHouses(ArrayList<House> houses) 
	{
		this.houses = houses;
	}
	
	public Hotel getHotel() 
	{
		return hotel;
	}
	
	public void setHotel(Hotel hotel) 
	{
		this.hotel = hotel;
	}
	
	
	public void buildHouse(House house) 
	{
		houses.add(house);
	}
	
	public void buildHotel(Hotel hotel) 
	{
		this.hotel = hotel;
		hotelExists = true;
		
		for (House h: houses) 
		{
			h.setOwner(null);
		}
		
		hotel.getOwner().removeHousesInLand(this);
		
		houses = new ArrayList<House>();
		
	}
	
	public boolean getRentFromPlayer(Player player) 
	{
		return player.payRentTo(owner, getRentAmount());
	}

	public double getRentAmount() 
	{
		return calculateRentPerTurn();
	}
	
	private double calculateRentPerTurnForHouses(int houseCount) 
	{
		double rpt = rentPerTurn;
		return Math.round(rpt*Math.pow(HOUSE_RENT_MULTIPLE_COEFFICIENT, houseCount)*100.0)/100.0;
	}
	
	private double calculatePriceOfLandWithHouses (int houseCount) 
	{
		return Math.round(price * Math.pow(HOUSE_PRICE_MULTIPLE_COEFFICIENT, houseCount)*100.0)/100.0;
	}
	
	private double getPriceWithHotel() 
	{
		return Math.round((price * HOTEL_PRICE_MULTIPLE_COEFFICIENT)*100.0)/100.0;
	}
	
	private double getRentPerTurnWithHotel () 
	{
		return Math.round((rentPerTurn * HOTEL_RENT_MULTIPLE_COEFFICIENT)*100.0)/100.0;
	}
	
	private double calculateRentPerTurn() 
	{
		
		double rpt = rentPerTurn;
		if (isThereAHouse()) 
		{
			rpt = calculateRentPerTurnForHouses(getNumberOfHouses());
		}
		else if (hotelExists) 
		{
			rpt = getRentPerTurnWithHotel();
		}
		
		return rpt;
		
	}
	
	private double calculatePrice() 
	{
		double price = this.price;
		if (isThereAHouse()) 
		{
			price = calculatePriceOfLandWithHouses(getNumberOfHouses());
		}
		else if (hotelExists) 
		{
			price = getPriceWithHotel();
		}
		return price;
	}
	
	public int getHouseLimit()
	{
		return MAXIMUM_NUMBER_OF_HOUSES;
	}
	
	@Override
	public String getInformation() 
	{
		String info = super.getInformation();
		info += "Color Group: "+ colorName + "\n";
		
		info += "Price when:\n\tThere is no building on this land: $" + price + "\n";
		for (int i = 1; i <= MAXIMUM_NUMBER_OF_HOUSES; i++) 
		{
			String word = i > 1 ? "houses" : "house";
			String isAre = i > 1 ? "are" : "is";
			info += String.format("\tThere %s %d %s built on this land: $", isAre, i, word);
			info += calculatePriceOfLandWithHouses(i) + "\n";
		}
		info += "\tThere is a hotel built on this land: $" + getPriceWithHotel() + "\n";
		info += "Current price: $" + calculatePrice() + "\n";
		
		info += "Rent Per Turn when:\n\tThere is no building on this land: $" + rentPerTurn + "\n";
		for (int i = 1; i <= MAXIMUM_NUMBER_OF_HOUSES; i++) 
		{
			String word = i > 1 ? "houses" : "house";
			String isAre = i > 1 ? "are" : "is";
			info += String.format("\tThere %s %d %s built on this land: $", isAre, i, word);
			info += calculateRentPerTurnForHouses(i) + "\n";
		}
		info += "\tThere is a hotel built on this land: $" + getRentPerTurnWithHotel() + "\n";
		info += "Current rent per turn: $" + calculateRentPerTurn() + "\n";
		info += "House Building Price: $" + houseBuildingPrice + "\n";
		info += "Hotel Building Price: $" + hotelBuildingPrice + "\n";
		
		if (!isThereAHouse()) 
		{
			info += "There is no house on this land.\n";
		}
		else 
		{
			String word = houses.size() > 1 ? "houses" : "house";
			String isAre = houses.size() > 1 ? "are" : "is";
			if (this.isOwned())
				info += "There " + isAre + " " + houses.size() + " " + word + " owned by " + owner + " on this land.\n";
			else
				info += "There " + isAre + " " + houses.size() + " unowned " + word + " on this land.\n"; 
		}
		
		if (!isThereAHotel()) 
		{
			info += "There is no hotel on this land.\n";
		}
		else 
		{
			if (this.isOwned())
				info += "There is a hotel owned by " + hotel.getOwner() + " on this land.\n";
			else
				info += "There is an unowned hotel on this land.\n";
		}
		
		return info;
	}
	
	public boolean equals (Land l) 
	{
		return l.getDescription().equals(description) && l.getTitle().equals(title);
	}
	
	public String toString() 
	{
		return description;
	}

}

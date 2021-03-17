import java.io.Serializable;

public class GameObject implements Serializable {

	//Oyun board'u üzerinde bulunan her kare GameObject türünde bir obje içerir.
	//Mesela, FreeParking karesinde GameObject'i extend eden FreeParking classýnýn objesi bulunur.
	
	protected String title;
	protected String description;
	protected int id;
	protected String type;
	
	// type deðiþkeni, Game classý içerisinde hangi objenin ne tip obje olduðunu ayýrt etmemize yardýmcý olur.
	
	public GameObject(String type, String title, String description, int id) 
	{
		this.type = type;
		this.title = title;
		this.description = description;
		this.id = id;
	}
	
	public String getTitle() 
	{
		return title;
	}	
	
	public String getDescription() 
	{
		return description;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;	
	}
	
	public void setDescription(String description)
	{	
		this.description = description;
	}
	
	public int getID() 
	{
		return id;	
	}
	
	public void setID(int id) 
	{
		this.id = id;
	}
	
	public String getType() 
	{
		return type;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public String getInformation() 
	{
	
		String info = "Title : " + title + "\n";
		info += "Description : " + description + "\n";
		
		return info;
		
		// GameObject objesi hakkýnda bilgi üretir ve döndürür. getInformation metodunu, karelerin üzerine týklandýðýnda karþýmýza çýkan bilgi pencereleri için kullanýyoruz.
		// getInformation() tarafýndan döndürülen bilgileri, karelere týklanýnca karþýmýza çýkan pencerelerde yazdýrýyoruz.
		
	}
	
}

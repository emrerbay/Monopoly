import java.io.Serializable;

public class GameObject implements Serializable {

	//Oyun board'u �zerinde bulunan her kare GameObject t�r�nde bir obje i�erir.
	//Mesela, FreeParking karesinde GameObject'i extend eden FreeParking class�n�n objesi bulunur.
	
	protected String title;
	protected String description;
	protected int id;
	protected String type;
	
	// type de�i�keni, Game class� i�erisinde hangi objenin ne tip obje oldu�unu ay�rt etmemize yard�mc� olur.
	
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
		
		// GameObject objesi hakk�nda bilgi �retir ve d�nd�r�r. getInformation metodunu, karelerin �zerine t�kland���nda kar��m�za ��kan bilgi pencereleri i�in kullan�yoruz.
		// getInformation() taraf�ndan d�nd�r�len bilgileri, karelere t�klan�nca kar��m�za ��kan pencerelerde yazd�r�yoruz.
		
	}
	
}

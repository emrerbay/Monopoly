import java.io.Serializable;

public class Card implements Serializable
{
	// Card classý, oyundaki þans kartlarýný temsil ediyor.
	// Kartlarý Game classý içerisinde Card objeleri oluþturma yoluyla tanýmlýyoruz.
	
	
	private String title;
	private String description;
	private String type;
	private static int idCounter = 1; // kartlarýn ID numaralarýný tanýmlarken otomatik olarak artýrmak için kullanýlýyor.
	// böylece dýþarýdan bir ID vermeden tanýmlanan her Card için otomatik olarak 1'den baþlayýp artacak þekilde ID'ler belirlemiþ oluyoruz
	private int ID;
	
	public Card(String title, String description) 
	{	
		// Bu Constructor title ve description alýyor. Bu title ve description, þans kartý çekildiðinde kartýn içeriðini oyunculara göstermek için Game classýnda kullanýlacak.
		this.description = description;
		this.title = title;
		this.ID = idCounter;
		this.type = "standard";
		// Ýki tip kartýmýz var, biri normal olan, çekildiðinde kendi içinde herhangi bir iþlem yapmayan "standard" kart tipi.
		// Standart olmayan kartlar ise her çekildiðinde farklý bir description ile karþýmýza çýkýyor.
		// Standart olmayan kartlara TriggeredCard dedik. Bu isim, þuradan geliyor: kart her çekildiðinde kart objesi trigger edilmelidir.
		// TriggeredCard'larýn trigger metodu var ve bu metod içerisinde kartýn özelliklerini deðiþtiriyor. Daha çok bilgi için bkz: TriggeredCard.java, MoveCard.java, LoseCard.java
		/* 
		 * Game.java'dan:
		 * if (!picked.getType().equals("standard")) 
		 *	{ 
		 *		((TriggeredCard) picked).trigger();
		 *	}
		 * Mesela bu kýsýmda, eðer seçilen kart standard tip deðilse, kart trigger ediliyor.
		 *  
		 */ 
		// Mesela, LoseCard'ý ele alýrsak; kartý bir çekiþte $150, bir baþka çekiþte $300 kaybettirebilir.
		// MoveCard da ayný þekilde, bir çekiþte 5 adým ileri götürürken, bir baþka çekiþte 7 adým geri götürebilir.
		// Bu type deðiþkeni, Game classýnda hangi kart normal kart, hangi kart Triggered bunu belirlememize yarýyor.
		// TriggeredCard'larýn tipine de "triggered" dedik (bkz. TriggeredCard.java)
		
		
		idCounter++;
		// constructor her çaðrýldýðýnda idCounter bir artýrýlýyor.
		// Bu sayede bir sonraki tanýmlanacak kartýn ID'si þimdikinin bir fazlasý olacak.
		// Böylece tanýmlanan kartlarýn ID'leri 1, 2, 3, 4, 5, 6, ... diye gidiyor.
		
		
		
	}
	
	public Card(String title, String description, String type) 
	{
		// Kartlarý tanýmlarken type'ý da belirleyerek taným yapabiliyoruz bu constructor sayesinde. (bkz. TriggeredCard.java)
		// eðer type belirlenmezse, otomatik olarak "standard" tipi atanýyor. (bkz. Card(String title, String description) constructor'ýndaki this.type = "standard"; satýrý)
		
		
		this(title, description);
		// Card(String title, String description) constructor'ýna title ve description bilgisi gönderilerek çalýþtýrýlýyor.
		// Bu sayede Card(String title, String description) constructor'ý çalýþtýrýlýp title, description, ID bilgileri belirlenmiþ oluyor.
		// Ayný zamanda this.type = "standard"; satýrý da çalýþtýrýlmýþ oluyor fakat zaten sonrasýnda aþaðýda, bu constructorda, this.type = type; diyerek kartýn tipinin belirtilen tip olmasýný saðlýyoruz.
		this.type = type;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public void setID(int ID) 
	{
		this.ID = ID;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public String getType() 
	{
		return type;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public int getID() 
	{
		return ID;
	}
	
	public static void setIDCounter (int num) 
	{
		idCounter = num;
		// Bu metodu, Monopoly.java içerisinde, yeni bir oyun baþlatýrken; ID'yi 1'e eþitlemek için kullanýyoruz. // bkz. Card.setIDCounter(1); (Monopoly.java)
		// Çünkü kartlar Game.java classýnda ID'lerine göre tanýnýyor 
		/*
		 * bkz.
		 * int id = picked.getID();
		 * switch(id) kýsmý. (Game.java) 
		 */
		// Eðer her oyun baþlangýcýna bu classýn idCounter'ýný tekrardan 1'e eþitlemezsek Game classýnda kartlar tanýnamaz ve kartlarýn üzerinde yazan iþlemler uygulanamaz.
	}
	
	public boolean equals(Card card) 
	{
		// kartlarýn eþitliðini kontrol etmek için.
		// her kartýn eþsiz bir ID'si olduðundan, eþitliði kontrol etmek için ID kullanýlýyor.
		return ID == card.getID();
		// ID == card.getID() karþýlaþtýrmasýnýn sonucu true ise true döner, false ise false döner.
	}
	
}

import java.io.Serializable;

public class Card implements Serializable
{
	// Card class�, oyundaki �ans kartlar�n� temsil ediyor.
	// Kartlar� Game class� i�erisinde Card objeleri olu�turma yoluyla tan�ml�yoruz.
	
	
	private String title;
	private String description;
	private String type;
	private static int idCounter = 1; // kartlar�n ID numaralar�n� tan�mlarken otomatik olarak art�rmak i�in kullan�l�yor.
	// b�ylece d��ar�dan bir ID vermeden tan�mlanan her Card i�in otomatik olarak 1'den ba�lay�p artacak �ekilde ID'ler belirlemi� oluyoruz
	private int ID;
	
	public Card(String title, String description) 
	{	
		// Bu Constructor title ve description al�yor. Bu title ve description, �ans kart� �ekildi�inde kart�n i�eri�ini oyunculara g�stermek i�in Game class�nda kullan�lacak.
		this.description = description;
		this.title = title;
		this.ID = idCounter;
		this.type = "standard";
		// �ki tip kart�m�z var, biri normal olan, �ekildi�inde kendi i�inde herhangi bir i�lem yapmayan "standard" kart tipi.
		// Standart olmayan kartlar ise her �ekildi�inde farkl� bir description ile kar��m�za ��k�yor.
		// Standart olmayan kartlara TriggeredCard dedik. Bu isim, �uradan geliyor: kart her �ekildi�inde kart objesi trigger edilmelidir.
		// TriggeredCard'lar�n trigger metodu var ve bu metod i�erisinde kart�n �zelliklerini de�i�tiriyor. Daha �ok bilgi i�in bkz: TriggeredCard.java, MoveCard.java, LoseCard.java
		/* 
		 * Game.java'dan:
		 * if (!picked.getType().equals("standard")) 
		 *	{ 
		 *		((TriggeredCard) picked).trigger();
		 *	}
		 * Mesela bu k�s�mda, e�er se�ilen kart standard tip de�ilse, kart trigger ediliyor.
		 *  
		 */ 
		// Mesela, LoseCard'� ele al�rsak; kart� bir �eki�te $150, bir ba�ka �eki�te $300 kaybettirebilir.
		// MoveCard da ayn� �ekilde, bir �eki�te 5 ad�m ileri g�t�r�rken, bir ba�ka �eki�te 7 ad�m geri g�t�rebilir.
		// Bu type de�i�keni, Game class�nda hangi kart normal kart, hangi kart Triggered bunu belirlememize yar�yor.
		// TriggeredCard'lar�n tipine de "triggered" dedik (bkz. TriggeredCard.java)
		
		
		idCounter++;
		// constructor her �a�r�ld���nda idCounter bir art�r�l�yor.
		// Bu sayede bir sonraki tan�mlanacak kart�n ID'si �imdikinin bir fazlas� olacak.
		// B�ylece tan�mlanan kartlar�n ID'leri 1, 2, 3, 4, 5, 6, ... diye gidiyor.
		
		
		
	}
	
	public Card(String title, String description, String type) 
	{
		// Kartlar� tan�mlarken type'� da belirleyerek tan�m yapabiliyoruz bu constructor sayesinde. (bkz. TriggeredCard.java)
		// e�er type belirlenmezse, otomatik olarak "standard" tipi atan�yor. (bkz. Card(String title, String description) constructor'�ndaki this.type = "standard"; sat�r�)
		
		
		this(title, description);
		// Card(String title, String description) constructor'�na title ve description bilgisi g�nderilerek �al��t�r�l�yor.
		// Bu sayede Card(String title, String description) constructor'� �al��t�r�l�p title, description, ID bilgileri belirlenmi� oluyor.
		// Ayn� zamanda this.type = "standard"; sat�r� da �al��t�r�lm�� oluyor fakat zaten sonras�nda a�a��da, bu constructorda, this.type = type; diyerek kart�n tipinin belirtilen tip olmas�n� sa�l�yoruz.
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
		// Bu metodu, Monopoly.java i�erisinde, yeni bir oyun ba�lat�rken; ID'yi 1'e e�itlemek i�in kullan�yoruz. // bkz. Card.setIDCounter(1); (Monopoly.java)
		// ��nk� kartlar Game.java class�nda ID'lerine g�re tan�n�yor 
		/*
		 * bkz.
		 * int id = picked.getID();
		 * switch(id) k�sm�. (Game.java) 
		 */
		// E�er her oyun ba�lang�c�na bu class�n idCounter'�n� tekrardan 1'e e�itlemezsek Game class�nda kartlar tan�namaz ve kartlar�n �zerinde yazan i�lemler uygulanamaz.
	}
	
	public boolean equals(Card card) 
	{
		// kartlar�n e�itli�ini kontrol etmek i�in.
		// her kart�n e�siz bir ID'si oldu�undan, e�itli�i kontrol etmek i�in ID kullan�l�yor.
		return ID == card.getID();
		// ID == card.getID() kar��la�t�rmas�n�n sonucu true ise true d�ner, false ise false d�ner.
	}
	
}

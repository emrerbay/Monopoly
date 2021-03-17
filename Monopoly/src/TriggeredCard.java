
public abstract class TriggeredCard extends Card implements Triggerable
{

	// Herhangi bir yerde bu classýn objesi yaratýlmadýðý için bu class abstract
	// Triggerable interface'ini implement ediyor (bkz. Triggerable.java)
	// Bu nedenle bu classýn subclasslarý trigger metodunu tanýmlamak zorunda.
	
	public TriggeredCard(String title) 
	{
		// Card classýnýn constructorýna aþaðýdaki veriler gönderiliyor. (bkz. Card.java constructor)
		super(title, "", "triggered");
		// description boþ olarak ayarlanýyor ve type triggered olarak ayarlanýyor.
		// description henüz boþ çünkü TriggeredCard'larýn subclasslarý descriptionlarýný trigger edildiklerinde tanýmlayacaklar.
		// TriggeredCard'ýn subclassý olan kartlarýn descriptionlarý sabit kalmýyor, her trigger edildiklerinde deðiþiyor.
	}

	
}

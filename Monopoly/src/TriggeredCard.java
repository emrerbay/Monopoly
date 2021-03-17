
public abstract class TriggeredCard extends Card implements Triggerable
{

	// Herhangi bir yerde bu class�n objesi yarat�lmad��� i�in bu class abstract
	// Triggerable interface'ini implement ediyor (bkz. Triggerable.java)
	// Bu nedenle bu class�n subclasslar� trigger metodunu tan�mlamak zorunda.
	
	public TriggeredCard(String title) 
	{
		// Card class�n�n constructor�na a�a��daki veriler g�nderiliyor. (bkz. Card.java constructor)
		super(title, "", "triggered");
		// description bo� olarak ayarlan�yor ve type triggered olarak ayarlan�yor.
		// description hen�z bo� ��nk� TriggeredCard'lar�n subclasslar� descriptionlar�n� trigger edildiklerinde tan�mlayacaklar.
		// TriggeredCard'�n subclass� olan kartlar�n descriptionlar� sabit kalm�yor, her trigger edildiklerinde de�i�iyor.
	}

	
}

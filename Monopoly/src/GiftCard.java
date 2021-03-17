import java.util.Random;

public class GiftCard extends TriggeredCard
{
	//bkz. Card.java dosyas�ndaki a��klamalar (sat�r 23-39 aras�)
	
	// Bu class her trigger edildi�inde, amounts arrayi i�erisinden rastgele bir de�er se�er
	// ve kart�n �zerindeki a��klamay� ve giftAmount de�i�kenini se�ilen de�ere g�re g�nceller. (bkz. trigger() metodu)
	
	private double giftAmount = 0;
	private double [] amounts;
	
	private final int NUMBER_OF_AMOUNTS = 12; // amounts listesi i�erisinde toplamda 12 tane farkl� hediye miktar� olacak.
	private final double INITIAL_AMOUNT = 125.0; // amounts listesindeki ilk para miktar� $125 olacak.
	private final double INCREASE_BY = INITIAL_AMOUNT; // amounts listesindeki para miktarlar� 125'er 125'er artt�r�lacak.
	
	public GiftCard() 
	{
		super("Gift from the Bank!");
		// �st class: TriggeredCard class�.
		// Card class�n�n constructor'� �a�r�l�yor ve b�ylelikle ba�l�k "Gift from the Bank!" oluyor.
		// Ayn� zamanda description ba�lang��ta "" olarak tan�mlanm�� oluyor.
		// ve kart tipi "triggered" olarak belirleniyor.
		// son 2 yorum sat�r� i�in bkz. TriggeredCard.java constructor tan�m�.
		
		setType("gift"); // type gift olarak ayarlan�yor. Fakat bu yap�lmasayd� ve kart tipi triggered olarak kalsayd� yine sorun olmazd�.
		// sembolik...
		
		
		amounts = new double[NUMBER_OF_AMOUNTS]; //NUMBER_OF_AMOUNTS kadar elemanl�k bir array yarat�l�p amounts'a atan�yor. Bu durumda 12 elemanl�k.
		double initial = INITIAL_AMOUNT; // initial miktar INITIAL_AMOUNT olarak belirleniyor. Bu durumda 125.0
		for (int i = 0; i < amounts.length; i++) 
		{
			//for'un ilk iteration'unda:
			// i = 0: amounts[0] = 125;
			// initial'�n de�eri INCREASE_BY kadar art�r�l�yor. Bu durumda, initial = initial + 125. Yani initial 250 oldu.
			
			// ikinci iteration:
			// i = 1: amounts[1] = 250; (��nk� son iterationda initial 125 art�r�larak 250'ye ��kar�lnm��t�.)
			// ard�ndan yine initial 125 art�r�l�yor. B�yle gide gide toplamda amounts arrayine 12 eleman konmu� oluyor.
			
			// amounts arrayi sonu� olarak ��yle oluyor: {125, 250, 375, 500, 625, 750, 875, 1000, 1125, 1250, 1375, 1500}
			amounts[i] = initial;
			initial += INCREASE_BY;	
		}
		
	}
	
	public void trigger() 
	{
		Random generator = new Random();
		int randInt = generator.nextInt(amounts.length);
		// bu komutla 0 ile 11 aras�nda bir say� se�ilip randInt'e atan�yor.
		
		
		// ard�ndan giftAmount = amounts[randInt] komutuyla amounts
		// arrayinden rastgele bir de�er se�ilip o de�er giftAmount'a atanm�� oluyor.
		giftAmount = amounts[randInt];
		super.setDescription("You are the lucky one to win the $" + giftAmount + " gift given by the bank!");
		// ve yeni giftAmount de�i�keni kullan�larak kart�n description'� ayarlan�yor.
		// burada ayarlanan description, oyun s�ras�nda kart �ekildi�inde ekranda g�z�ken yaz�da olacak.
		
	}
	
	public double getGiftAmount() 
	{
		return giftAmount;
	}

}

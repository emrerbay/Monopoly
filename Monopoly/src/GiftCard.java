import java.util.Random;

public class GiftCard extends TriggeredCard
{
	//bkz. Card.java dosyasýndaki açýklamalar (satýr 23-39 arasý)
	
	// Bu class her trigger edildiðinde, amounts arrayi içerisinden rastgele bir deðer seçer
	// ve kartýn üzerindeki açýklamayý ve giftAmount deðiþkenini seçilen deðere göre günceller. (bkz. trigger() metodu)
	
	private double giftAmount = 0;
	private double [] amounts;
	
	private final int NUMBER_OF_AMOUNTS = 12; // amounts listesi içerisinde toplamda 12 tane farklý hediye miktarý olacak.
	private final double INITIAL_AMOUNT = 125.0; // amounts listesindeki ilk para miktarý $125 olacak.
	private final double INCREASE_BY = INITIAL_AMOUNT; // amounts listesindeki para miktarlarý 125'er 125'er arttýrýlacak.
	
	public GiftCard() 
	{
		super("Gift from the Bank!");
		// üst class: TriggeredCard classý.
		// Card classýnýn constructor'ý çaðrýlýyor ve böylelikle baþlýk "Gift from the Bank!" oluyor.
		// Ayný zamanda description baþlangýçta "" olarak tanýmlanmýþ oluyor.
		// ve kart tipi "triggered" olarak belirleniyor.
		// son 2 yorum satýrý için bkz. TriggeredCard.java constructor tanýmý.
		
		setType("gift"); // type gift olarak ayarlanýyor. Fakat bu yapýlmasaydý ve kart tipi triggered olarak kalsaydý yine sorun olmazdý.
		// sembolik...
		
		
		amounts = new double[NUMBER_OF_AMOUNTS]; //NUMBER_OF_AMOUNTS kadar elemanlýk bir array yaratýlýp amounts'a atanýyor. Bu durumda 12 elemanlýk.
		double initial = INITIAL_AMOUNT; // initial miktar INITIAL_AMOUNT olarak belirleniyor. Bu durumda 125.0
		for (int i = 0; i < amounts.length; i++) 
		{
			//for'un ilk iteration'unda:
			// i = 0: amounts[0] = 125;
			// initial'ýn deðeri INCREASE_BY kadar artýrýlýyor. Bu durumda, initial = initial + 125. Yani initial 250 oldu.
			
			// ikinci iteration:
			// i = 1: amounts[1] = 250; (çünkü son iterationda initial 125 artýrýlarak 250'ye çýkarýlnmýþtý.)
			// ardýndan yine initial 125 artýrýlýyor. Böyle gide gide toplamda amounts arrayine 12 eleman konmuþ oluyor.
			
			// amounts arrayi sonuç olarak þöyle oluyor: {125, 250, 375, 500, 625, 750, 875, 1000, 1125, 1250, 1375, 1500}
			amounts[i] = initial;
			initial += INCREASE_BY;	
		}
		
	}
	
	public void trigger() 
	{
		Random generator = new Random();
		int randInt = generator.nextInt(amounts.length);
		// bu komutla 0 ile 11 arasýnda bir sayý seçilip randInt'e atanýyor.
		
		
		// ardýndan giftAmount = amounts[randInt] komutuyla amounts
		// arrayinden rastgele bir deðer seçilip o deðer giftAmount'a atanmýþ oluyor.
		giftAmount = amounts[randInt];
		super.setDescription("You are the lucky one to win the $" + giftAmount + " gift given by the bank!");
		// ve yeni giftAmount deðiþkeni kullanýlarak kartýn description'ý ayarlanýyor.
		// burada ayarlanan description, oyun sýrasýnda kart çekildiðinde ekranda gözüken yazýda olacak.
		
	}
	
	public double getGiftAmount() 
	{
		return giftAmount;
	}

}

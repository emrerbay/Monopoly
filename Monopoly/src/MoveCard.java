import java.util.Random;

public class MoveCard extends TriggeredCard
{
	
	// Þans karesine gelindiðinde çekilen hareket etme kartýdýr. (Örnek: Go 6 steps forward.)
	
	private int moveAmount = 0;
	// kartýn kaç birim hareket ettireceðini belirtmek için
	// -12 ile +12 arasýnda (inclusive) deðer alabilir.
	// 12'nin sebebi: zar atarken de en fazla 12 gelebiliyor.
	
	private String direction;
	// kartta ileri git mi geri git mi yazacaðý bu deðiþkende tutulacak.
	// iki deðerden birini alýr: forward ya da backward.
	
	public MoveCard() 
	{
		super("Move!");	
		// TriggeredCard'ýn constructor'ýna yukarýdaki veri gönderilir.
		// bkz. TriggeredCard.java constructor.
		setType("move");
		// kartýn tipi "move" olarak ayarlanýr.
	}
	
	public void trigger() 
	{
		//trigger metodu çaðrýldýðýnda -12 ile +12 arasýnda bir tam sayý üretilir ve bu üretilen tam sayýya göre kartýn açýklamasý ve moveAmount deðiþkeni ayarlanýr.
		
		Random generator = new Random();
		do {
			moveAmount = generator.nextInt(25) - 12;
			// generator.nextInt(25) ile belirtilen aralýk: 0....25 (25 dahil deðil, 0 dahil)
			// 12 çýkarýnca aralýk: -12...13 (-12 dahil, 13 dahil deðil)
		} while(moveAmount == 0); // eðer moveAmount þans eseri 0 çýkarsa, tekrardan moveAmount'u generate ediyoruz. Çünkü kartta 0 çýkmasýný istemiyoruz.
		
		int absoluteVal = Math.abs(moveAmount);
		// kartýn description'ýný ayarlarken Go -6 steps backward gibi bir description yerine Go 6 steps backward þeklinde bir description elde edebilmek için moveAmount'un mutlak deðerini alýyoruz.
		
		
		// eðer üretilen moveAmount 0'dan büyükse yön forward olur, deðilse backward olur.
		if (moveAmount > 0)
			direction = "forward";
		else
			direction = "backward";
		
		//direction = moveAmount > 0 ? "forward" : "backward";
		
		super.setDescription("Go " + absoluteVal + " steps " + direction);
		// yukarýda belirlenen deðiþkenler kullanýlarak kartýn descriptioný ayarlanýr.
		// Örnek: moveAmount = -4 olsun.
			// bu durumda description þu olur: Go 4 steps backward.
		// Örnek: moveAmount = 12 olsun.
			// bu durumda description þu olur: Go 12 steps forward.
		
		// Game classýnda, burada üretilen moveAmount kullanýlarak piyonun uygun yönde uygun miktarda hareketi gerçekleþtirilir.
	}
	
	
	public String getDirection() 
	{
		return direction;
	}
	
	public int getMoveAmount() 
	{
		return moveAmount;
	}
	
	public void setDirection(String direction) 
	{
		this.direction = direction;
	}
	
	public void setMoveAmount(int moveAmount) 
	{
		this.moveAmount = moveAmount;
	}
}

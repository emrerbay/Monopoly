import java.util.Random;

public class MoveCard extends TriggeredCard
{
	
	// �ans karesine gelindi�inde �ekilen hareket etme kart�d�r. (�rnek: Go 6 steps forward.)
	
	private int moveAmount = 0;
	// kart�n ka� birim hareket ettirece�ini belirtmek i�in
	// -12 ile +12 aras�nda (inclusive) de�er alabilir.
	// 12'nin sebebi: zar atarken de en fazla 12 gelebiliyor.
	
	private String direction;
	// kartta ileri git mi geri git mi yazaca�� bu de�i�kende tutulacak.
	// iki de�erden birini al�r: forward ya da backward.
	
	public MoveCard() 
	{
		super("Move!");	
		// TriggeredCard'�n constructor'�na yukar�daki veri g�nderilir.
		// bkz. TriggeredCard.java constructor.
		setType("move");
		// kart�n tipi "move" olarak ayarlan�r.
	}
	
	public void trigger() 
	{
		//trigger metodu �a�r�ld���nda -12 ile +12 aras�nda bir tam say� �retilir ve bu �retilen tam say�ya g�re kart�n a��klamas� ve moveAmount de�i�keni ayarlan�r.
		
		Random generator = new Random();
		do {
			moveAmount = generator.nextInt(25) - 12;
			// generator.nextInt(25) ile belirtilen aral�k: 0....25 (25 dahil de�il, 0 dahil)
			// 12 ��kar�nca aral�k: -12...13 (-12 dahil, 13 dahil de�il)
		} while(moveAmount == 0); // e�er moveAmount �ans eseri 0 ��karsa, tekrardan moveAmount'u generate ediyoruz. ��nk� kartta 0 ��kmas�n� istemiyoruz.
		
		int absoluteVal = Math.abs(moveAmount);
		// kart�n description'�n� ayarlarken Go -6 steps backward gibi bir description yerine Go 6 steps backward �eklinde bir description elde edebilmek i�in moveAmount'un mutlak de�erini al�yoruz.
		
		
		// e�er �retilen moveAmount 0'dan b�y�kse y�n forward olur, de�ilse backward olur.
		if (moveAmount > 0)
			direction = "forward";
		else
			direction = "backward";
		
		//direction = moveAmount > 0 ? "forward" : "backward";
		
		super.setDescription("Go " + absoluteVal + " steps " + direction);
		// yukar�da belirlenen de�i�kenler kullan�larak kart�n description� ayarlan�r.
		// �rnek: moveAmount = -4 olsun.
			// bu durumda description �u olur: Go 4 steps backward.
		// �rnek: moveAmount = 12 olsun.
			// bu durumda description �u olur: Go 12 steps forward.
		
		// Game class�nda, burada �retilen moveAmount kullan�larak piyonun uygun y�nde uygun miktarda hareketi ger�ekle�tirilir.
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

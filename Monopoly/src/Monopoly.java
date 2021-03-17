import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Monopoly {

	
	// oyunun driver class�d�r.
	// main metodu bu classta bulunur.
	
    public static void main(String[] args) 
    {
    	//program ilk ba�lat�ld���nda, firstInteraction() metodu �al��t�r�l�r.
    	firstInteraction();
    }
    
    public static void firstInteraction() 
    {
    	// ka� oyuncu oldu�unu soran ekran a��l�r.
    	// UserCountInput penceresinde OK'a t�kland���nda, bu class�n startTheGame metodu �al��t�r�l�r.
    	UserCountInput uci = new UserCountInput();
        uci.pack();
        uci.setVisible(true);
    }
    
    public static void startTheGame(int count)
    {
    	// bu metod, UserCountInput class�ndan �a��r�l�r.
    	// oyunda ka� oyuncu olaca��n� parametre olarak al�r ve buna g�re oyuncular�n ismini sorar.
    	
    	// Player class�n�n ve Card class�n�n static de�i�kenleri olan idCounter de�i�kenleri,
    	// a�a��daki iki sat�rla resetlenir.
    	// Oyun new game butonu ile tekrardan �al��t�r�labildi�inden, yeni oyun ba�larken bu idCounterlar s�f�rlanmal�d�r.
    	// Player'�n idCounter'lar� resetlenmezse, mesela ilk oyunda 3 oyuncu varsa new game ile ikinci oyun ba�lat�ld���nda oyuncular�n id numaralar� 4'ten ba�lar.
    	// bu durumu engellemek i�in Player class�n�n idCounter'� resetleniyor.
    	// Card class�n�n idCounter'�n�n resetlenmesinin sebebi i�in bkz. Card class� setIDCounter metodu i�indeki a��klamalar.
    	Player.setIDCounter(1);
    	Card.setIDCounter(1);
        //System.out.println(count);
        
        ArrayList<Player> players = new ArrayList<Player>();
        // Player ArrayListi olu�turulur.
        // Bu arrayliste bu classta yarat�lacak olan oyuncu objeleri eklenecek ve bu players arraylisti Game class�na verilecek.
        
        // t�m oyuncular�n isimlerini soruyoruz.
        for (int i = 0; i < count; i++) 
        {
        	String name;
        	boolean firstTry = true;
        	// ilk soru�ta a�a��daki hata mesaj�n� g�stermemek i�in.
        	do {
        		
        		if (!firstTry) // e�er bu ilk soru� de�ilse a�a��daki hata mesaj�n� g�ster.
        		{
        			JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Invalid name!", JOptionPane.ERROR_MESSAGE);
        		}
        		name = JOptionPane.showInputDialog(null, String.format("Name of Player%d", (i+1)), "Name", JOptionPane.QUESTION_MESSAGE);
        		// e�er name = null ise, �arp�ya bas�lm�� demektir.
        		// �arp�ya bas�l�rsa ya da kutu bo� b�rak�l�rsa tekrar soruyoruz (bkz. while k�sm�n�n i�i)
        		// e�er name "      " olarak girilmi� ise, name.trim() bo� string d�nd�r�r.
        		// name.trim.equals("") k�sm� sayesinde s�rf space'lerden olu�an bir isim girildi�inde bu ismi kabul etmeyip ayn� oyuncunun ismini tekrar soruyoruz.
        		firstTry = false;
        		// ilk soru�u yapt�ktan sonra firstTry = false diyoruz b�ylece bundan sonraki soru�larda hata mesaj� da g�sterilecek.
        		// zaten e�er bu k�sma 2. kere giriliyorsa oyuncu ismi do�ru girilmemi�tir.
        	} while (name == null || name.trim().equals(""));
        	
        	// belirtilen adda Player yarat�l�p players'a ekleniyor.
        	players.add(new Player(name));
        }
        
        boolean err = false;
        double initial = 0.0;
        
        // a�a��daki do-while d�ng�s�nde ba�lang�� paras� soruluyor.
        do {
        	if (err) // e�er son soru�ta hata olu�mu�sa hata mesaj� g�steriyoruz.
        	{
        		err = false;
        		// e�er err resetlenmezse, err true olarak kal�r ve bir kez hata olu�tuktan sonra s�rekli hata varm�� gibi alg�lan�r, d�ng�den ��k�lamaz.
        		// Bu nedenle e�er err true ise, err'i false yap�yoruz. E�er hata ��karsa a�a��da zaten err true olacak. E�er hata olu�mazsa, err false olarak kalacak ve d�ng�den ��k�lacak.
    			
        		// hata mesaj� g�sterilir:
        		JOptionPane.showMessageDialog(null, "Please enter a valid amount for initial money.", "Invalid amount!", JOptionPane.ERROR_MESSAGE);
        	}
        	// ba�lang�� paras�n� soran diyalog penceresi g�sterilir. 
        	String amount = JOptionPane.showInputDialog(null, String.format("Initial Money"), "Money", JOptionPane.QUESTION_MESSAGE);
        	
        	// Java'da virg�ll� say�lar . kullan�larak ifade edilir.
        	// e�er kullan�c� ba�lang�� paras� miktar�n� virg�l kullanarak girmi�se bu virg�ll� say�n�n Java taraf�ndan hatal� olarak alg�lanmamas� i�in virg�l� nokta ile de�i�tiriyoruz.
        	amount = amount.replace(",", ".");
        	
        	// kullan�c�n�n girdi�i miktar �u anda String, bu Stringi double'a �evirmeye �al���yoruz.
        	// e�er girilen stringin format� double'a �evrilmeye m�sait de�ilse NumberFormatException ile kar��la��r�z.
        	// catch k�sm�nda bu exception'� handle edip err'i true olarak ayarl�yoruz.
        	// B�ylece hata mesaj� g�sterilecek ve kullan�c�ya ba�lang�� paras� tekrar sorulacak.
        	try 
        	{
        		initial = Double.parseDouble(amount);
        	}
        	catch (NumberFormatException e) 
        	{
        		err = true;
        	}
        } while (err); // hata olu�tu�u s�rece ayn� soruyu tekrar soruyoruz.

        // yeni bir Game objesi yarat�l�r, bu Game objesine bu classta olu�turulmu� olan oyuncular listesi ve ba�lang�� paras� miktar� verilir.
        Game session = new Game(players, initial);
        session.start(); // oyun ba�lat�l�r.
        
        
    }

}

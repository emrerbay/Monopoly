import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Monopoly {

	
	// oyunun driver classýdýr.
	// main metodu bu classta bulunur.
	
    public static void main(String[] args) 
    {
    	//program ilk baþlatýldýðýnda, firstInteraction() metodu çalýþtýrýlýr.
    	firstInteraction();
    }
    
    public static void firstInteraction() 
    {
    	// kaç oyuncu olduðunu soran ekran açýlýr.
    	// UserCountInput penceresinde OK'a týklandýðýnda, bu classýn startTheGame metodu çalýþtýrýlýr.
    	UserCountInput uci = new UserCountInput();
        uci.pack();
        uci.setVisible(true);
    }
    
    public static void startTheGame(int count)
    {
    	// bu metod, UserCountInput classýndan çaðýrýlýr.
    	// oyunda kaç oyuncu olacaðýný parametre olarak alýr ve buna göre oyuncularýn ismini sorar.
    	
    	// Player classýnýn ve Card classýnýn static deðiþkenleri olan idCounter deðiþkenleri,
    	// aþaðýdaki iki satýrla resetlenir.
    	// Oyun new game butonu ile tekrardan çalýþtýrýlabildiðinden, yeni oyun baþlarken bu idCounterlar sýfýrlanmalýdýr.
    	// Player'ýn idCounter'larý resetlenmezse, mesela ilk oyunda 3 oyuncu varsa new game ile ikinci oyun baþlatýldýðýnda oyuncularýn id numaralarý 4'ten baþlar.
    	// bu durumu engellemek için Player classýnýn idCounter'ý resetleniyor.
    	// Card classýnýn idCounter'ýnýn resetlenmesinin sebebi için bkz. Card classý setIDCounter metodu içindeki açýklamalar.
    	Player.setIDCounter(1);
    	Card.setIDCounter(1);
        //System.out.println(count);
        
        ArrayList<Player> players = new ArrayList<Player>();
        // Player ArrayListi oluþturulur.
        // Bu arrayliste bu classta yaratýlacak olan oyuncu objeleri eklenecek ve bu players arraylisti Game classýna verilecek.
        
        // tüm oyuncularýn isimlerini soruyoruz.
        for (int i = 0; i < count; i++) 
        {
        	String name;
        	boolean firstTry = true;
        	// ilk soruþta aþaðýdaki hata mesajýný göstermemek için.
        	do {
        		
        		if (!firstTry) // eðer bu ilk soruþ deðilse aþaðýdaki hata mesajýný göster.
        		{
        			JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Invalid name!", JOptionPane.ERROR_MESSAGE);
        		}
        		name = JOptionPane.showInputDialog(null, String.format("Name of Player%d", (i+1)), "Name", JOptionPane.QUESTION_MESSAGE);
        		// eðer name = null ise, çarpýya basýlmýþ demektir.
        		// çarpýya basýlýrsa ya da kutu boþ býrakýlýrsa tekrar soruyoruz (bkz. while kýsmýnýn içi)
        		// eðer name "      " olarak girilmiþ ise, name.trim() boþ string döndürür.
        		// name.trim.equals("") kýsmý sayesinde sýrf space'lerden oluþan bir isim girildiðinde bu ismi kabul etmeyip ayný oyuncunun ismini tekrar soruyoruz.
        		firstTry = false;
        		// ilk soruþu yaptýktan sonra firstTry = false diyoruz böylece bundan sonraki soruþlarda hata mesajý da gösterilecek.
        		// zaten eðer bu kýsma 2. kere giriliyorsa oyuncu ismi doðru girilmemiþtir.
        	} while (name == null || name.trim().equals(""));
        	
        	// belirtilen adda Player yaratýlýp players'a ekleniyor.
        	players.add(new Player(name));
        }
        
        boolean err = false;
        double initial = 0.0;
        
        // aþaðýdaki do-while döngüsünde baþlangýç parasý soruluyor.
        do {
        	if (err) // eðer son soruþta hata oluþmuþsa hata mesajý gösteriyoruz.
        	{
        		err = false;
        		// eðer err resetlenmezse, err true olarak kalýr ve bir kez hata oluþtuktan sonra sürekli hata varmýþ gibi algýlanýr, döngüden çýkýlamaz.
        		// Bu nedenle eðer err true ise, err'i false yapýyoruz. Eðer hata çýkarsa aþaðýda zaten err true olacak. Eðer hata oluþmazsa, err false olarak kalacak ve döngüden çýkýlacak.
    			
        		// hata mesajý gösterilir:
        		JOptionPane.showMessageDialog(null, "Please enter a valid amount for initial money.", "Invalid amount!", JOptionPane.ERROR_MESSAGE);
        	}
        	// baþlangýç parasýný soran diyalog penceresi gösterilir. 
        	String amount = JOptionPane.showInputDialog(null, String.format("Initial Money"), "Money", JOptionPane.QUESTION_MESSAGE);
        	
        	// Java'da virgüllü sayýlar . kullanýlarak ifade edilir.
        	// eðer kullanýcý baþlangýç parasý miktarýný virgül kullanarak girmiþse bu virgüllü sayýnýn Java tarafýndan hatalý olarak algýlanmamasý için virgülü nokta ile deðiþtiriyoruz.
        	amount = amount.replace(",", ".");
        	
        	// kullanýcýnýn girdiði miktar þu anda String, bu Stringi double'a çevirmeye çalýþýyoruz.
        	// eðer girilen stringin formatý double'a çevrilmeye müsait deðilse NumberFormatException ile karþýlaþýrýz.
        	// catch kýsmýnda bu exception'ý handle edip err'i true olarak ayarlýyoruz.
        	// Böylece hata mesajý gösterilecek ve kullanýcýya baþlangýç parasý tekrar sorulacak.
        	try 
        	{
        		initial = Double.parseDouble(amount);
        	}
        	catch (NumberFormatException e) 
        	{
        		err = true;
        	}
        } while (err); // hata oluþtuðu sürece ayný soruyu tekrar soruyoruz.

        // yeni bir Game objesi yaratýlýr, bu Game objesine bu classta oluþturulmuþ olan oyuncular listesi ve baþlangýç parasý miktarý verilir.
        Game session = new Game(players, initial);
        session.start(); // oyun baþlatýlýr.
        
        
    }

}

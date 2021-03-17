
public class ComboBoxItem 
{
	//Bu class, TradeWindow classýnda, combobox'a eklenecek olan nesneler olarak kullanýlmýþtýr.
	// key: oyuncu ismi
	// value: oyuncu ID'si
	private String key;
	private int value;
	
	public ComboBoxItem(String key, int value) 
	{
		this.key = key;
		this.value = value;
	}
	
	public String toString() 
	{
		return key;
	}
	
	public int getValue() 
	{
		return value;
	}
	
	public void setValue(int value) 
	{
		this.value = value;
	}
	
	public String getKey() 
	{
		return key;
	}
	
	public void setKey(String key) 
	{
		this.key = key;
	}

}

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TradeWindow extends javax.swing.JFrame {

	private JButton jButton1;
    private JComboBox<Object> jComboBox1;
    private JComboBox<Object> jComboBox2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JTable jTable1;  
    private Game gameDriver;
    private boolean firstRun = true;
    private Player currentSeller;
    private Player currentBuyer;
    private ArrayList<Player> playersInGame;
    
    public TradeWindow(Game gameDriver) 
    {
    	this.gameDriver = gameDriver;
    	
    	playersInGame = gameDriver.getPlayersInGame();
    	currentSeller = playersInGame.get(0);
    	currentBuyer = playersInGame.get(1);
        initComponents();
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fillComboBoxes(this.gameDriver.getPlayers().get(0));
        updateWindow();
    }
    
    private void updateWindow() 
    {
    	currentSeller = getPlayerFromID(getCurrentSelectionValue(jComboBox1));
    	currentBuyer = getPlayerFromID(getCurrentSelectionValue(jComboBox2));
    	
    	System.out.println("Current seller is " + currentSeller);
    	System.out.println("Current buyer is " + currentBuyer);
    	
    	DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
    	tableModel.setRowCount(0);
    	
    	if (currentSeller != null)
    	{
    		this.jLabel3.setText("Properties of " + currentSeller + ":");
	    	for (Land l: currentSeller.getLands()) 
	    	{
	    		Object[] elements = new Object[3];
	    		elements[0] = "Land";
	    		elements[1] = l.getDescription();
	    		elements[2] = "$" + l.getPrice();
	    		tableModel.addRow(elements);
	    	}
	    	
	    	for (Public p: currentSeller.getPublicFields()) 
	    	{
	    		Object[] elements = new Object[3];
	    		elements[0] = "Public Service";
	    		elements[1] = p.getTitle();
	    		elements[2] = "$" + p.getPrice();
	    		tableModel.addRow(elements);
	    	}
    	}
    	else 
    	{
    		this.jLabel3.setText("Properties: ");
    	}
    	
    	
    }
    

    private void fillComboBoxes(Player seller) 
    {
    	jComboBox1.removeAllItems();
    	jComboBox2.removeAllItems();
    	playersInGame = gameDriver.getPlayersInGame();
    	for (Player p: playersInGame) 
    	{
    		ComboBoxItem item = new ComboBoxItem(p.toString(), p.getID());
    		jComboBox1.addItem(item);
    		
    		if (seller == null)
    			jComboBox2.addItem(item);
    		else if (p.getID() != seller.getID())
    			jComboBox2.addItem(item);
    	}
    }
    
                         
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Seller: ");
        jLabel2.setText("Buyer: ");


        
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, 260, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel3.setText("Properties: ");

        jTable1.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type", "Name", "Suggested Price"
            }
        ) {
			boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
        );

        jButton1.setText("Buy & Sell");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }                   

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) 
    {                                         
    	
    	int selectedRow = jTable1.getSelectedRow();
    	
    	if (selectedRow != -1) 
    	{
    		String type = (String) (jTable1.getValueAt(selectedRow, 0));
    		String name = (String) (jTable1.getValueAt(selectedRow, 1));
    		String suggested = (String) (jTable1.getValueAt(selectedRow, 2));
    		double suggestedAmount = Double.parseDouble(suggested.substring(1));
    		
    		
    		System.out.println("type: " + type + " name : " + name + " suggested : " + suggested+ "\n");
    		Property found = null;
    		for (Property p: currentSeller.getProperties()) 
    		{
    			System.out.println(p);
    			if (type.equals("Public Service")) 
    			{
    				if (p.getType().equals("public") && p.getTitle().equals(name)) 
    				{
    					found = p;
    				}
    			}
    			else 
    			{
    				if (p.getType().equals("land") && p.getDescription().equals(name)) 
    				{
    					found = p;
    				}
    			}
    		}
    		
    		if (found != null) 
    		{
    			boolean err, cancel = false;
    			boolean firstTry = true;
    			double amount = 0;
    			do 
    			{
    				err = false;
    				if (!firstTry) 
    				{
    					JOptionPane.showMessageDialog(null, "Please enter a valid amount for price.", "Invalid amount!", JOptionPane.ERROR_MESSAGE);
    				}
    				
    				try 
    				{
    					String str_amount  = "";
    					Object answer = JOptionPane.showInputDialog(null, String.format("Price for %s: ", name), "Price", JOptionPane.QUESTION_MESSAGE, null, null, String.valueOf(suggestedAmount));
    					if (answer == null) 
    					{
    						cancel = true;
    					}
    					else {
    						str_amount = answer.toString();
    						str_amount = str_amount.replace(",", ".");
    						amount = Double.parseDouble(str_amount);
    					}
    				} catch(NumberFormatException e) 
    				{
    					err = true;
    				}
    				catch (NullPointerException e) 
    				{
    					err = true;
    				}
    				
    				if (firstTry) firstTry = false;
    			}while (err);
    			
    			if (!cancel) 
    			{
    				if (!currentBuyer.canAfford(amount)) 
    				{
    					JOptionPane.showMessageDialog(null, currentBuyer + " cannot afford $" + amount + "!", "Too much!", JOptionPane.ERROR_MESSAGE);
    				}
    				else 
    				{
    					System.out.println(found.getInformation());
    					System.out.println(found.getType());
    					currentSeller.sellPropertyTo(found, currentBuyer, amount);
    					gameDriver.addMessageToGameWindow(currentSeller + " sold " + found + " to " + currentBuyer + " for $" + amount + "!\n");
    					gameDriver.updatePlayerTable();
    					gameDriver.playMoneySound();
    					updateWindow();
    				}
    				System.out.println(amount);
    				
    			}
    		}

    	}
    	else 
    	{
    		JOptionPane.showMessageDialog(null, "There is no property selected to sell!", "No property selected!", JOptionPane.ERROR_MESSAGE);
    	}
    }                                        

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {  
    	playersInGame = gameDriver.getPlayersInGame();
    	if (!firstRun)
    	{
	    	int index = jComboBox1.getSelectedIndex();
	    	int ID = getCurrentSelectionValue(jComboBox1);
	    	jComboBox1.removeAllItems();
	    	jComboBox2.removeAllItems();

	        fillComboBoxes(getPlayerFromID(ID));
	        jComboBox1.setSelectedIndex(index);
	        updateWindow();
    	}
    	
    	else 
    	{
    		firstRun = false;
    	}
    }
    
    private int getCurrentSelectionValue (JComboBox<Object> comboBox) 
    {
    
    	int r;
    	
    	try 
    	{
    		r = ((ComboBoxItem)(comboBox.getItemAt(comboBox.getSelectedIndex()))).getValue();
    	} 
    	
    	catch(NullPointerException e) 
    	{
    		r = -1;
    	}
    	
    	return r;
    }

    private Player getPlayerFromID(int playerID) 
    {
    	Player r = null;
    	if (playerID != -1) 
    	{
	    	for (Player p: playersInGame) 
	    	{
	    		if (p.getID() == playerID) 
	    		{
	    			r = p;
	    		}
	    	}
    	}
    	return r;
    }

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {   
    	updateWindow();
    }                                          
        
    public JComboBox<Object> getSellerComboBox() 
    {
    	return jComboBox1;
    }
    
    public JComboBox<Object> getBuyerComboBox() 
    {
    	return jComboBox2;
    }
    
    public JButton getBuySellButton() 
    {
    	return jButton1;
    }
         
}

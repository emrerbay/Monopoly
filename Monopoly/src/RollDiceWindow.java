import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

class DicePanel extends JPanel 
{

	private int counter = -1;
	private int limit;
	private String[] paths;
	private int die1, die2;
	boolean cont = true;
	
	public DicePanel(int limit, String[] paths, int die1, int die2) 
	{
		super();
		this.limit = limit;
		this.paths = paths;
		this.die1 = die1;
		this.die2 = die2;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		Random generator = new Random();
		Image die1, die2;
		
		if (counter != limit) 
		{
			String die1Path = paths[generator.nextInt(6)];
			String die2Path = paths[generator.nextInt(6)];
			
			die1 = new ImageIcon(die1Path).getImage();
			die2 = new ImageIcon(die2Path).getImage();
			
			counter++;
		}
		else 
		{
			String die1Path = paths[this.die1-1];
			String die2Path = paths[this.die2-1];
			
			die1 = new ImageIcon(die1Path).getImage();
			die2 = new ImageIcon(die2Path).getImage();
			
			counter = 0;
			cont = false;
		}

	    g.drawImage(die1, 40, 60, null);
	    g.drawImage(die2, 160, 60, null);
	}
	
	public boolean isContinuing() 
	{
		return cont;
	}
	
	public int getCounter() 
	{
		return counter;
	}
	

} 

public class RollDiceWindow extends javax.swing.JDialog {
                
    private JButton jButton1;
    private JLabel jLabel1;
    private DicePanel jPanel1;
    private JPanel jPanel2;    
    private String[] dieFacePathNames;
    private int die1Value, die2Value;
	
    public RollDiceWindow(JFrame parent, boolean modal, int die1Value, int die2Value, String[] dieFacePathNames) 
    {
        super(parent, modal);
        
        this.die1Value = die1Value;
        this.die2Value = die2Value;
        this.dieFacePathNames = dieFacePathNames;
        
        initComponents();
    }
    
    public void startAnimating() 
    {
    	
    	
    	Timer timer = new Timer(50, new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if (!jPanel1.isContinuing()) 
		    	{
		    		((Timer)e.getSource()).stop();	
		    		setLabel();
		    	}	
		    	else 
		    	{
		    		jPanel1.repaint();
		    	}
		    }
		});

		timer.setInitialDelay(100);
		timer.start();
		this.setVisible(true);
		
    }
    
    private void setLabel() 
    {
    	int total = die1Value + die2Value;
    	jLabel1.setText("You rolled " + total + "!");
    }
    
                      
    private void initComponents() 
    {
    	this.setResizable(false);
        jPanel1 = new DicePanel(5, dieFacePathNames, die1Value, die2Value);
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 211, Short.MAX_VALUE)
        );

        jLabel1.setText("You rolled");

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        
    }                   

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.setVisible(false);
        this.dispose();
    }                                        

    
             
}

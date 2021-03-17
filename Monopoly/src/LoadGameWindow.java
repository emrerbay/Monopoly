import java.io.File;

import javax.swing.*;

public class LoadGameWindow extends javax.swing.JFrame {

    private JButton jButton1;
    private JLabel jLabel1;
    private JList<String> jList1;
    private JPanel jPanel1;
    private JScrollPane jScrollPane2;   
    private String savesFolder, ext;
    private Game gameDriver;

    public LoadGameWindow(Game gameDriver, String savesFolder, String ext) {
    	this.gameDriver = gameDriver;
        this.savesFolder = savesFolder;
    	this.ext = ext;
    	
        initComponents();
        this.setResizable(false);
        
        updateList();
    }
    
    private void updateList() 
    {
    	
    	File saves = new File(savesFolder);
    	String[] filenames = saves.list();
    	DefaultListModel<String> model = (DefaultListModel<String>) (jList1.getModel());
    	for (String name: filenames) 
    	{
    		String[] s = name.split("\\.");
    		if (s.length > 1 && s[s.length-1].equals(ext)) 
    		{
    			model.addElement(s[0]);
    		}
    	}
    	
    }
    
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DefaultListModel<String> model = new DefaultListModel<>();
        jList1 = new javax.swing.JList<>(model);
        jButton1 = new javax.swing.JButton();


        jLabel1.setText("Available Saves:");

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jList1);

        jButton1.setText("Load");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                   

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String selected = jList1.getSelectedValue();
        if (selected != null) 
        {
        	this.setVisible(false);
        	this.dispose();
        	gameDriver.loadGame(selected);
        }
        
    }                                        
                 
}

public class InformationWindow extends javax.swing.JFrame {

	// Bu class, Game classýnda kullanýlýr.
	// Oyuncularýn mülklerinin listelendiði pencerenin çaðýrýlmasýnda ve karelerin üzerine
	// týklandýðýnda açýlan bilgi pencerelerinin yaratýlmasýnda kullanýlýr.
	
	private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
	
    public InformationWindow() 
    {
        initComponents();
    }
                 
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextArea1.setEditable(false);
        jButton1 = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }
    
    public void addTextToTextArea (String t) 
    {	
    	// Buraya verilen string, bu pencerenin yazý alanýna eklenir.
    	jTextArea1.setText(jTextArea1.getText() + t);
    }
    
    public void setTextOfTextArea (String t) 
    {
    	// Buraya verilen string, bu pencerede gösterilen string olarak ayarlanýr.
    	jTextArea1.setText(t);
    }
    
    public String getTextOfTextArea () 
    {
    	// Bu pencerede gösterilen yazýyý döndürür.
    	return jTextArea1.getText();
    }
    

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	// OK butonuna basýldýðýnda pencere görünülmez kýlýnýr (this.setVisible(false)),
    	// ardýndan yok edilir. (this.dispose())
    	this.setVisible(false);
    	this.dispose();
    }                                        
                  
          
}

import javax.swing.JOptionPane;
import javax.swing.*;

public class UserCountInput extends javax.swing.JFrame {
	
    private JButton jButton1;
    private JLabel jLabel1;
    private JSpinner jSpinner1;
    
    public UserCountInput() 
    {
        initComponents();
    }

                       
    private void initComponents() 
    {

        jLabel1 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Number of Players:");

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                   

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        boolean error = false;
        try {
            jSpinner1.commitEdit();
        } catch (Exception e) {
            error = true;
            System.out.println(e.getMessage());
        }
        
        if (!error) {
            int count = (Integer) jSpinner1.getValue();
            if (count < 2)
                JOptionPane.showMessageDialog(this, "This game is played with at least 2 players.",
               "Error!", JOptionPane.ERROR_MESSAGE);
            else if (count > 8)
                JOptionPane.showMessageDialog(this, "At most 8 players are allowed.");
            else
            { 
                Monopoly.startTheGame(this, count);
                this.setVisible(false);
                this.dispose();
            }
        }
    }                                        
              
}

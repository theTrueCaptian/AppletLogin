/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;


/*
 * ServerClientChatPanel.java
 *
 * Created on Feb 20, 2011, 4:38:21 PM
 */


public class ServerClientChatPanel extends javax.swing.JPanel implements Runnable {
    String clientMessage = "";
    JClient client;
    BufferedReader reader;
    PrintWriter writer;
    boolean refresh = false;
    /** Creates new form ServerClientChatPanel */
    public ServerClientChatPanel(JClient client, BufferedReader reader, PrintWriter writer) {
        this.client = client;
        this.reader = reader;
        this.writer = writer;

        setSize(500, 500);
        initComponents();

        
        new Thread(this).start();

        
    }

    public void run() {

        while(true){
            //System.out.println("hello!");
            try {
                //String message = "KLAdkal";
                String message = reader.readLine();
                System.out.println("message:"+message);
                
                if(!refresh){
                    clientMessage = clientMessage +"\n"+ message;
                    displayChatField.setText(clientMessage);
                }else{
                    String[] listUsers = message.split(",");
                    String toDisplay ="";
                    for(int i = 0; i <listUsers.length;i++){
                        toDisplay = toDisplay + listUsers[i]+ "\n";
                    }
                    
                    onlineUsers.setText(toDisplay);
                    
                    refresh = false;
                }
            } catch (IOException ex) {
            }
        }
    }
    //this is the refresh button on the side. u may delete it if u want
    //u can remove the big textfield above the refresh button
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //add code here
        writer.println("/listusers ");
        refresh = true;
        /*String allOnlineUsers = "";
        try{
            allOnlineUsers = reader.readLine();
        }catch(IOException ex){
            
        }    
        onlineUsers.setText(allOnlineUsers);*/
             

    }

    private void chatFieldActionPerformed(java.awt.event.ActionEvent evt) {
         //this isn't supposed to do anything
    }

    private void sendButtonActionPerformed(ActionEvent evt){
        //send the message in the chatField to server
        if(chatField.getText().equals("/logout")){
            writer.println(chatField.getText());
            System.exit(0);
        }else
            writer.println("/m "+chatField.getText());
        //display it in displayChatField
        //clientMessage = clientMessage +"\n"+client.getClientUser()+": "+ chatField.getText();
        //display it
        //displayChatField.setText(clientMessage);
        //clear field
        chatField.setText("");
        

    }

    //this is automatically generated stuff
    //warning: please try not to google anything in here
    private void initComponents() {

        chatField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayChatField = new javax.swing.JTextArea();
        TitleLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        onlineUsers = new javax.swing.JTextArea();
        refreshButton = new javax.swing.JButton();
        TextField = new javax.swing.JTextField();
        Button = new javax.swing.JButton();

        chatField.setText("Enter Chat Here");
        chatField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatFieldActionPerformed(evt);
            }
        });

        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        sendButton.setText("Send");
        
        displayChatField.setColumns(20);
        displayChatField.setRows(5);
        displayChatField.setEnabled(false);
        jScrollPane1.setViewportView(displayChatField);

        TitleLabel.setFont(new java.awt.Font("Arial", 0, 18));
        TitleLabel.setText("Chat with Server");

        onlineUsers.setColumns(20);
        onlineUsers.setRows(5);
        jScrollPane2.setViewportView(onlineUsers);
      

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    
                    .addComponent(chatField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sendButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(refreshButton)
                        .addGap(99, 99, 99))
                    ))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(TitleLabel)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton)
                    .addComponent(refreshButton))
                
                .addContainerGap(183, Short.MAX_VALUE))
        );
        System.out.println("done with init:)");
    }// </editor-fold>
   


    private javax.swing.JLabel TitleLabel;
    private javax.swing.JTextField chatField;
    private javax.swing.JTextArea displayChatField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton sendButton;
    private JTextArea onlineUsers;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton Button;
    private javax.swing.JTextField TextField;


    // End of variables declaration

}

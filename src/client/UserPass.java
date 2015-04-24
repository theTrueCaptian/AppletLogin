/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

/**
 *
 * @author user
 */
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author maeda hanafi
 */
enum UserPassState{REQUEST_USER_NAME_PASSWORD, AUTHENTICATING, ERROR_USER_PASS, SUCCESS}
public class UserPass extends JPanel{
    JTextField user;
    JPasswordField pass;
    JLabel userLabel;
    JLabel passLabel;
    JLabel title;
    JButton loginButton;
    JClient client;
    Socket socket;
    UserPassState state  = UserPassState.REQUEST_USER_NAME_PASSWORD;
    String enteredUser = null;
    String enteredPass = null;
    boolean clickedButton = false;
    boolean success = false;
    BufferedReader reader;
    PrintWriter writer;

    public UserPass( JClient client, BufferedReader reader, PrintWriter writer){
        this.client = client;
        this.reader = reader;
        this.writer = writer;

        setBorder(new LineBorder(Color.green, 1));

        user = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        userLabel = new javax.swing.JLabel("Username");
        passLabel = new javax.swing.JLabel("Password");
        loginButton = new javax.swing.JButton("Enter");

        setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginButton)
                    .addComponent(pass, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                    .addComponent(user, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel)
                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(loginButton)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        loginButton.addActionListener(new loginListener());
        initialize();

    }

    //won work
    @Override
    public void paintComponent(Graphics g){
        if(state==UserPassState.AUTHENTICATING){
            System.out.println("Please wait...");
            g.drawString("Please wait...", 150, 150);
        }
        //g.drawString("Hello", 100, 100);
    }

    class loginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Login!");
            enteredUser = user.getText();
            client.setClientUser(enteredUser);
            enteredPass = pass.getText();
            if(!enteredUser.equals("") || !enteredPass.equals(""))
                authenticate();
            else{
                System.out.println("Please enter something");
            }
        }
    }

    public void authenticate(){
        try {



            writer.println("/login " + enteredUser + " " + enteredPass);

            state = UserPassState.AUTHENTICATING;
            initialize();
            String result;
            result = null;
            result = "success";
            result = reader.readLine();
            System.out.println("result: " + result);

            if (result != null) {
                if (result.equalsIgnoreCase("login_success")) {
                    state = UserPassState.SUCCESS;
                } else {
                    state = UserPassState.ERROR_USER_PASS;
                    System.out.println("ERORUSERPASS");
                }
            } else {
                state = UserPassState.ERROR_USER_PASS;
                System.out.println("ERORUSERPASS");
            }
            initialize();
        } catch (IOException ex) {
            Logger.getLogger(UserPass.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    private void initialize(){
        if(state == UserPassState.REQUEST_USER_NAME_PASSWORD || state == UserPassState.ERROR_USER_PASS){
            loginButton.setEnabled(true);
            loginButton.setText("Enter");

        }else if(state == UserPassState.AUTHENTICATING){
            loginButton.setText("Please Wait");
            loginButton.setEnabled(false);
            System.out.println("please wait");
        }else if(state == UserPassState.SUCCESS){
            loginButton.setEnabled(false);
            loginButton.setText("Success!");
            success = true;
            client.startChat(this);

        }
    }

    public UserPassState getState(){
        return state;
    }

    public void removeComponents(){
        user.setVisible(false);
        user = null;
        pass.setVisible(false);
        userLabel.setVisible(false);
        passLabel.setVisible(false);
        title.setVisible(false);
        title = null;
        loginButton.setVisible(false);
    }
}

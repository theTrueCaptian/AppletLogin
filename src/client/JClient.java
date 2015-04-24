/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.JApplet;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import javax.swing.JLabel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author user
 */
public class JClient extends JApplet {
    private String clientUser = null;
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;
    public void init() {
        try {
            socket = new Socket("10.72.15.4", 4006);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
        }
        //setSize(700, 700);
        //add(new JLabel("Welcome to JChat!", JLabel.CENTER));
        UserPass userPass = new UserPass(this, reader, writer);
        add(userPass);

    }

    public void startChat(UserPass userPass){
        //userPass.removeComponents();
        remove(userPass);
        
        ServerClientChatPanel chat = new ServerClientChatPanel(this, reader, writer);
        add(chat);

    }
    public String getClientUser(){
        return clientUser;
    }
    public void setClientUser(String name){
        clientUser = name;
    }

}

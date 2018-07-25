/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jchat;

import java.awt.BorderLayout;
import static java.awt.PageAttributes.MediaType.B;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

/**
 *
 * @author LENOVO
 */
public class JChat extends JFrame implements ActionListener {

  
		String name;
		InetAddress iadr;
		int port;
		MulticastSocket so;
		JTextArea txt = new JTextArea();
		JScrollPane sp = new JScrollPane(txt);
		JTextField write = new JTextField();
		JButton quit = new JButton("Go Offline");
		public JChat(String username, String groupAdr, int portNr) throws IOException {
			name = username;
			iadr = InetAddress.getByName(groupAdr);
			port = portNr;
			
			so = new MulticastSocket(port);
			so.joinGroup(iadr);
			new Reciever(so,txt) {};
			sendMess("Online");
			setTitle("Chatting with "+ name);
			txt.setEditable(true);
			add(quit,BorderLayout.NORTH);
			add(sp,BorderLayout.CENTER);
			add(write,BorderLayout.SOUTH);
			quit.addActionListener(this);
			write.addActionListener(this);
			setSize(400,250);
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
                public void sendMess(String s) {
			byte[] data = (name + ": " + s).getBytes();
			DatagramPacket packet = new DatagramPacket(data,data.length,iadr,port);
			try {
				so.send(packet);
			}
			catch(IOException ie) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Data overflow !");
			}
		}
                @Override
                public void actionPerformed(ActionEvent e) {
		if(e.getSource()==write) {
			sendMess(write.getText());
			write.setText("");
			}
		else if(e.getSource()==quit) {
			sendMess("Offline");
			try {
				so.leaveGroup(iadr);
			}
			catch(IOException ie) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Data overflow, connection error !");
				}
			so.close();
			dispose();
			System.exit(0);
			}
		}
    public static void main(String[] arg) throws IOException{
        String in = JOptionPane.showInputDialog(null,"What's your name?");
		if(arg.length>0) 
			in = arg[0];
		new JChat(in,"Add a D-Class IP",9876);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import javax.swing.JTextArea;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

/**
 *
 * @author LENOVO
 */
public class Reciever implements Runnable{
    
    Thread activity = new Thread(this);
		MulticastSocket so;
		JTextArea txt;
		
	Reciever(MulticastSocket sock, JTextArea txtAr) {
		so = sock;
		txt = txtAr;
		activity.start();
		}
        public void run() {
			byte[] data = new byte[1024];
			while(true)
				try {
					DatagramPacket packet = new DatagramPacket(data,data.length);
					so.receive(packet);
					String mess = new String(data,0,packet.getLength());
					txt.append(mess+ "\n");
				}
				catch(IOException e) {
			break;
		}
	}
}

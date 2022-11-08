/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class UDPService {
    public static byte[] serialize(Serializable obj){
        ObjectOutputStream os = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            os = new ObjectOutputStream(out);
            os.writeObject(obj);
            return out.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(UDPService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(UDPService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    public static Object deserialize(byte[] data){
        ObjectInputStream is = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException ex) {
            Logger.getLogger(UDPService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UDPService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(UDPService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static void send(DatagramSocket sender, InetAddress receiverIP, int receiverPort, Serializable object){
        try {
            byte[] data = serialize(object);
            DatagramPacket packet = new DatagramPacket(data, data.length, receiverIP, receiverPort);
            sender.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(UDPService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Object receive(DatagramSocket receiver){
        try {
            byte[] data = new byte[5000];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            receiver.receive(packet);
            return deserialize(packet.getData());
        } catch (IOException ex) {
            Logger.getLogger(UDPService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

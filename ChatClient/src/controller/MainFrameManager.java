/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app.CONFIG;
import app.ClientApp;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.DTO.Message;
import model.DTO.User;
import model.DTO.MessageAddress;
import model.DTO.MessageBox;
import model.DTO.MessageRepository;
import service.TCPService;
import service.UDPService;
import view.MainFrame;

/**
 *
 * @author ADMIN
 */
public class MainFrameManager extends FrameController<MainFrame> {
    MessageRepository messageRepository;
    List <MessageAddress> chatBoxs;
    MessageAddress chatTarget;
    InteractiveThread interactiveThread;

    public MainFrameManager() {
        super(new MainFrame());
        chatBoxs = new ArrayList<>();
    }

    private void getMessageRepository() {
        messageRepository = (MessageRepository) TCPService.receive(ClientApp.navigator.client.getSocket());
    }

    private void getServerDatagramSocketPort() {
        ClientApp.navigator.client.setServerDatagramSocketPort(
                (int) TCPService.receive(
                            ClientApp.navigator.client.getSocket()));
    }
    
    private void sendClientDatagramSocketPort(){
        TCPService.send(
                ClientApp.navigator.client.getSocket(),
                ClientApp.navigator.interactiveThread.datagramSocket.getLocalPort()
        );
    }
    public void sendMessage() {
        Message message = new Message(
                ClientApp.navigator.client.getUser(),
                chatTarget,
                Timestamp.valueOf(LocalDateTime.now()),
                frame.getMessageArea().getText()
        );
        ClientApp.navigator.interactiveThread.sendRequestType(CONFIG.REQUEST_TYPE.MESSAGE);
        ClientApp.navigator.interactiveThread.sendToServer(message);
        frame.getMessageArea().setText("");
        messageRepository.getMessageBoxs().get(chatTarget.getId()).getMessages().add(message);
        updateMessageTable();
    }
    
    @Override
    boolean run() {
        frame.getUserLabel().setText("Hi, " + ClientApp.navigator.client.getUser().getName());
        getMessageRepository();
        getServerDatagramSocketPort();
        sendClientDatagramSocketPort();
        interactiveThread = new InteractiveThread();
        interactiveThread.start();
        updateChatBoxsTable();
        showFrame();
        return true;
    }
    
    public void updateChatBoxsTable(){
        DefaultTableModel dtm = (DefaultTableModel) frame.getChatBoxsTable().getModel();
        dtm.getDataVector().removeAllElements();
        chatBoxs.clear();
        for (MessageBox mb:messageRepository.getMessageBoxs().values()) {
            String row = String.format("%s", mb.getReceiver().getName());
//            row += mb.isSeen()?"":" - has new messages!!!";
            dtm.addRow(new Object[]{row});
            chatBoxs.add(mb.getReceiver());
        }
    }
    public void updateMessageTable() {
        frame.getSendButton().setEnabled(true);
        chatTarget = chatBoxs.get(frame.getChatBoxsTable().getSelectedRow());
        DefaultTableModel dtm = (DefaultTableModel) frame.getMessageTable().getModel();
        dtm.getDataVector().removeAllElements();
        frame.getMessageTable().getTableHeader().getColumnModel().getColumn(0).setHeaderValue(chatTarget.getName());
        for (Message message : messageRepository.getMessageBoxs().get(chatTarget.getId()).getMessages()) {
            dtm.addRow(new Object[]{String.format("%s (%s): %s", message.getSender().getName(), message.getTime(), message.getContent())});
        }
    }
    
    public void logOut(){
        System.exit(0);
    }
}

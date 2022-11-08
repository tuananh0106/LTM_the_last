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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DTO.Group;
import model.DTO.Message;
import model.DTO.MessageBox;
import model.DTO.User;
import service.UDPService;

/**
 *
 * @author ADMIN
 */
public class InteractiveThread extends Thread{
    DatagramSocket datagramSocket;
    
    public InteractiveThread(){
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(InteractiveThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendRequestType(CONFIG.REQUEST_TYPE type){
        sendToServer(type);
    }
    
    public void sendToServer(Serializable object){
        UDPService.send(
                datagramSocket,
                ClientApp.navigator.client.getSocket().getInetAddress(),
                ClientApp.navigator.client.getServerDatagramSocketPort(),
                object
        );
    }
    
    @Override
    public void run() {
        while(true){
            CONFIG.RESPONSE_TYPE response_type = (CONFIG.RESPONSE_TYPE) UDPService.receive(datagramSocket);
            Object data = UDPService.receive(datagramSocket);
            switch(response_type){
                case DIRECT_MESSAGE:
                    handleReceivedDriectMessage(data);
                    break;
                case GROUP_MESSAGE:
                    handleReceivedGroupMessage(data);
                    break;
                case SEARCH_RESULT:
                    handleReceivedSearchResult(data);
                    break;
                case ADD_FRIEND_REQUEST_RESULT:
                    if (((String) data).equals(CONFIG.SERVER_RESPONSE.SUCCESS)){
                        createLocalMessageBoxWithNewFriend();
                    }
                    break;
                case FRIENDS_LIST:
                    handleReceivedFriendsList(data);
                    break;
                case NEW_GROUP_REQUEST_RESULT:
                    if (((String) data).equals(CONFIG.SERVER_RESPONSE.SUCCESS)){
                        createLocalGroupMessageBox();
                    }
                    break;
                default:
                    System.out.println("Unknown response type: " + response_type);
                    break;
            }
        }
    }
    
    public void handleReceivedGroupMessage(Object data){
        Message message = (Message) data;
        ClientApp.navigator.mainFrameManager.messageRepository
                .getMessageBoxs().get(message.getReceiver().getId())
                .getMessages().add(message);
        ClientApp.navigator.mainFrameManager.updateMessageTable();
    }
    
    public void handleReceivedDriectMessage(Object data){
        Message message = (Message) data;
        ClientApp.navigator.mainFrameManager.messageRepository
                .getMessageBoxs().get(message.getSender().getId())
                .getMessages().add(message);
        ClientApp.navigator.mainFrameManager.updateMessageTable();
    }
    
    public void handleReceivedSearchResult(Object data){
        ClientApp.navigator.addFriendRequester.recommendUsers = (List <User>) data;
        ClientApp.navigator.addFriendRequester.updateSearchResultTable();
    }
    
    public void handleReceivedFriendsList(Object data){
        ClientApp.navigator.newGroupRequester.friendsList = (List <User>) data;
        ClientApp.navigator.newGroupRequester.updateFriendsListTable();
    }
    
    public void createLocalMessageBoxWithNewFriend(){
        User newFriend = (User) UDPService.receive(datagramSocket);
        ClientApp.navigator.mainFrameManager.messageRepository
                .getMessageBoxs().put(
                        newFriend.getId(), new MessageBox(ClientApp.navigator.client.getUser(), newFriend, false));
        ClientApp.navigator.mainFrameManager.updateChatBoxsTable();
    }
    
    public void createLocalGroupMessageBox(){
        Group newGroup = (Group) UDPService.receive(datagramSocket);
        ClientApp.navigator.mainFrameManager.messageRepository
                .getMessageBoxs().put(
                        newGroup.getId(), new MessageBox(ClientApp.navigator.client.getUser(), newGroup, false));
        ClientApp.navigator.mainFrameManager.updateChatBoxsTable();
    }    
}

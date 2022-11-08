/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app.CONFIG;
import app.ClientApp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.DTO.User;
import view.NewGroupFrame;

/**
 *
 * @author ADMIN
 */
public class NewGroupRequester extends FrameController<NewGroupFrame>{
    List <User> friendsList;

    public NewGroupRequester() {
        super(new NewGroupFrame());
    }

    @Override
    boolean run() {
        showFrame();
        sendRequestToGetFriendsList();
        return true;
    }
    
    private void sendRequestToGetFriendsList(){
        ClientApp.navigator.interactiveThread.sendRequestType(CONFIG.REQUEST_TYPE.GET_FRIENDS_LIST);
        ClientApp.navigator.interactiveThread.sendToServer(CONFIG.NOTHING);
    }
    public void sendNewGroupRequest(){
        if (frame.getGroupNameField().getText().trim().equals("")){
            JOptionPane.showMessageDialog(frame, "Name the group, please!");
            return;
        }
        if (frame.getFriendListTable().getSelectedRowCount() == 0){
            JOptionPane.showMessageDialog(frame, "Select members, please!");
            return;
        }
        ClientApp.navigator.interactiveThread.sendRequestType(CONFIG.REQUEST_TYPE.NEW_GROUP);
        ClientApp.navigator.interactiveThread.sendToServer(frame.getGroupNameField().getText().trim());
        ClientApp.navigator.interactiveThread.sendToServer((Serializable) getMembersOfNewGroup());
    }
    private List <User> getMembersOfNewGroup(){
        List <User> members = new ArrayList<>();
        DefaultTableModel dtm = (DefaultTableModel) frame.getFriendListTable().getModel();
        for(int i:frame.getFriendListTable().getSelectedRows()){
            members.add(friendsList.get(i));
        }
        members.add(ClientApp.navigator.client.getUser());
        return members;
    }
    public void updateFriendsListTable(){
        DefaultTableModel dtm = (DefaultTableModel) frame.getFriendListTable().getModel();
        dtm.getDataVector().removeAllElements();
        for (User u:friendsList) {
            dtm.addRow(new Object[]{u.getUsername(), u.getName()});
        }
    }
}

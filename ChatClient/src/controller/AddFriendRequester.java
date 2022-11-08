/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app.CONFIG;
import app.ClientApp;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.DTO.User;
import service.UDPService;
import view.AddFriendFrame;

/**
 *
 * @author ADMIN
 */
public class AddFriendRequester extends FrameController<AddFriendFrame>{
    List <User> recommendUsers;

    public void setRecommendUsers(List<User> recommendUsers) {
        this.recommendUsers = recommendUsers;
    }
    
    public AddFriendRequester() {
        super(new AddFriendFrame());
    }

    @Override
    boolean run() {
        sendSearchRequest();
        showFrame();
        return true;
    }
    
    public void sendSearchRequest(){
        ClientApp.navigator.interactiveThread.sendRequestType(CONFIG.REQUEST_TYPE.SEARCH_USERS);
        ClientApp.navigator.interactiveThread.sendToServer(frame.getSearchField().getText().trim());
    }
    
    public void sendAddFriendRequest(){
        ClientApp.navigator.interactiveThread.sendRequestType(CONFIG.REQUEST_TYPE.ADD_FRIEND);
        ClientApp.navigator.interactiveThread.sendToServer(recommendUsers.get(frame.getSearchResultTable().getSelectedRow()));
    }
    
    public void updateSearchResultTable(){
        DefaultTableModel dtm = (DefaultTableModel) frame.getSearchResultTable().getModel();
        dtm.getDataVector().removeAllElements();
        for (User u:recommendUsers) {
            dtm.addRow(new Object[]{u.getUsername(), u.getName()});
        }
    }
}

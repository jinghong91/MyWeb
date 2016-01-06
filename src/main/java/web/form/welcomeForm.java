package web.form;


import web.model.User;

import java.util.ArrayList;
import java.util.List;

public class welcomeForm {
    private List<User> userList = new ArrayList<User>();

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}

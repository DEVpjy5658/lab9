package services;

import dataaccess.UserDB;
import java.util.HashMap;
import models.User;

public class AccountService {
    
    public User login(String email, String password) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                return user;
            }
        } catch (Exception e) {
        }
        
        return null;
    }

    public boolean forgotPassword(String email, String path) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            if (user != null) {
                
                String to = user.getEmail();
                String subject = "Forgot password";
                String template = path + "/emailtemplates/forgotpassword.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("email", user.getEmail());
                tags.put("password", user.getPassword());
                
                GmailService.sendMail(to, subject, template, tags);
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

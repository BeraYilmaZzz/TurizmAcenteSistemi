package com.tourismagencysystem.View;
import com.tourismagencysystem.Core.Helper;
import com.tourismagencysystem.Model.User;

import javax.swing.*;

public class LoginGUI extends Layout {
    private JPanel wrapper;
    private JPanel w_top;
    private JPanel w_bottom;
    private JTextField fld_uname;
    private JPasswordField fld_pass;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_pass;
    private JButton btn_quit;
    private User user;

    public LoginGUI() {
        this.add(wrapper);
        this.guiInitilaze(400,400);

        // Giriş Yap butonu
        btn_login.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_uname) || Helper.isFieldEmpty(fld_pass)){
                Helper.showMsg("fill");
            }else{
                User u = User.getFetch(fld_uname.getText(),fld_pass.getText()); // 8 NUMARALI FORM
                if(u == null){
                    Helper.showMsg("Kullanıcı bulunamadı !");
                    fld_uname.setText(null);
                    fld_pass.setText(null);
                }else{
                    JFrame userGUI = null;
                    switch (u.getType()) {
                        case "Admin":
                            userGUI = new AdminGUI(u);
                            break;
                        case "Acente":
                            userGUI = new AgencyGUI(u);
                            break;
                    }
                    dispose();
                }
            }
        });
        //** Giriş Yap Son

        btn_quit.addActionListener(e -> {
            dispose();
        });
    }
    public static void main(String[] args) {
        LoginGUI loginGUI = new LoginGUI();
    }
}

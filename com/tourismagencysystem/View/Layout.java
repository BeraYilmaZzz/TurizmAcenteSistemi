package com.tourismagencysystem.View;

import com.tourismagencysystem.Core.Helper;

import javax.swing.*;

import static com.tourismagencysystem.Core.Config.PROJECT_TITLE;

public class Layout extends JFrame {
    public void guiInitilaze(int weidth,int height) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(PROJECT_TITLE);
        this.setSize(weidth, height);
        this.setVisible(true);
        this.setLocation(Helper.getLocationPoint("x", this.getSize()), Helper.getLocationPoint("y", this.getSize()));
    }
}

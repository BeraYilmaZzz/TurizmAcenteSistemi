package com.tourismagencysystem.View;

import com.tourismagencysystem.Core.DBConnector;
import com.tourismagencysystem.Model.Hotel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateHotelPropertysGUI extends Layout {
    private Hotel hotel;
    private JPanel wrapper;
    private JPanel wtop;
    private JRadioButton rb_freePark;
    private JRadioButton rb_freewifi;
    private JRadioButton rb_swimPool;
    private JRadioButton rbn_fitness;
    private JRadioButton rb_servant;
    private JRadioButton rb_spa;
    private JRadioButton rb_allDayService;
    private JButton btn_propertys_update;
    private JPopupMenu propMenu;

    public UpdateHotelPropertysGUI(Hotel hotel) {
        this.hotel = hotel;
        this.add(wrapper);
        this.guiInitilaze(700, 500);

        // Her JRadioButton'a feature_id atama
        rb_freePark.setActionCommand("1");
        rb_freewifi.setActionCommand("2");
        rb_swimPool.setActionCommand("3");
        rbn_fitness.setActionCommand("4");
        rb_servant.setActionCommand("5");
        rb_spa.setActionCommand("6");
        rb_allDayService.setActionCommand("7");
        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFeatureIDs = e.getActionCommand();
            }
        };

        rb_freePark.addActionListener(radioButtonListener);
        rb_freewifi.addActionListener(radioButtonListener);
        rb_swimPool.addActionListener(radioButtonListener);
        rbn_fitness.addActionListener(radioButtonListener);
        rb_servant.addActionListener(radioButtonListener);
        rb_spa.addActionListener(radioButtonListener);
        rb_allDayService.addActionListener(radioButtonListener);
        btn_propertys_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selectedFeatureIDs = getSelectedFeatureIDs();
                int hotelID = hotel.getId();

                boolean success = upsertHotelPropertys(hotelID, selectedFeatureIDs);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Özellikler güncellendi.");
                } else {
                    JOptionPane.showMessageDialog(null, "Özellikler güncellenirken hata oluştu.");
                }
            }
        });
    }
    private List<String> getSelectedFeatureIDs() {
        List<String> selectedFeatureIDs = new ArrayList<>();
        if (rb_freePark.isSelected()) {
            selectedFeatureIDs.add(rb_freePark.getActionCommand());
        }
        if (rb_freewifi.isSelected()) {
            selectedFeatureIDs.add(rb_freewifi.getActionCommand());
        }
        if (rb_swimPool.isSelected()) {
            selectedFeatureIDs.add(rb_swimPool.getActionCommand());
        }
        if (rbn_fitness.isSelected()) {
            selectedFeatureIDs.add(rbn_fitness.getActionCommand());
        }
        if (rb_servant.isSelected()) {
            selectedFeatureIDs.add(rb_servant.getActionCommand());
        }
        if (rb_spa.isSelected()) {
            selectedFeatureIDs.add(rb_spa.getActionCommand());
        }
        if (rb_allDayService.isSelected()) {
            selectedFeatureIDs.add(rb_allDayService.getActionCommand());
        }
        return selectedFeatureIDs;
    }

    public boolean upsertHotelPropertys(int hotel_id, List<String> selectedFeatureIDs) {
        try {
            String selectQuery = "SELECT feature_id FROM hotel_prop_relations WHERE hotel_id = ?";
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(selectQuery);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();

            List<Integer> existingFeatureIDs = new ArrayList<>();
            while (((ResultSet) rs).next()) {
                existingFeatureIDs.add(rs.getInt("feature_id"));
            }

            String upsertQuery = "INSERT INTO hotel_prop_relations (hotel_id, feature_id) VALUES (?, ?)";
            PreparedStatement prs = DBConnector.getInstance().prepareStatement(upsertQuery);

            for (String featureID : selectedFeatureIDs) {
                int featureIDInt = Integer.parseInt(featureID);

                if (!existingFeatureIDs.contains(featureIDInt)) {
                    prs.setInt(1, hotel_id);
                    prs.setInt(2, featureIDInt);
                    prs.addBatch();
                }
            }
            int[] rowsUpdated = prs.executeBatch();
            for (int updated : rowsUpdated) {
                if (updated > 0) {
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
}
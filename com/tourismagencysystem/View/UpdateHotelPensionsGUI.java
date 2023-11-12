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

public class UpdateHotelPensionsGUI extends Layout{
    private Hotel hotel;
    private JPanel wrapper;
    private JRadioButton rb_ultra_allin;
    private JRadioButton rb_allin;
    private JRadioButton rb_room_breakfast;
    private JRadioButton rb_full_pension;
    private JRadioButton rb_half_pension;
    private JRadioButton rb_justbed;
    private JRadioButton rb_except_alcohol;
    private JButton btn_update_Hpensions;

    public UpdateHotelPensionsGUI (Hotel hotel) {
        this.hotel = hotel;
        this.add(wrapper);
        this.guiInitilaze(700, 500);

        // her radio button'a pansiyon atama
        rb_ultra_allin.setActionCommand("1");
        rb_allin.setActionCommand("2");
        rb_room_breakfast.setActionCommand("3");
        rb_full_pension.setActionCommand("4");
        rb_half_pension.setActionCommand("5");
        rb_justbed.setActionCommand("6");
        rb_except_alcohol.setActionCommand("7");
        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPensionIDs = e.getActionCommand();
            }
        };
        rb_ultra_allin.addActionListener(radioButtonListener);
        rb_allin.addActionListener(radioButtonListener);
        rb_room_breakfast.addActionListener(radioButtonListener);
        rb_full_pension.addActionListener(radioButtonListener);
        rb_half_pension.addActionListener(radioButtonListener);
        rb_justbed.addActionListener(radioButtonListener);
        rb_except_alcohol.addActionListener(radioButtonListener);
        btn_update_Hpensions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selectedPensionsIDS = getSelectedPensionID();
                int hotelID = hotel.getId();
                boolean success = upsertHotelPensions(hotelID, selectedPensionsIDS);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Özellikler güncellendi.");
                } else {
                    JOptionPane.showMessageDialog(null, "Özellikler güncellenirken hata oluştu.");
                }
            }
        });
    }
        public boolean upsertHotelPensions(int hotel_id, List <String> selectedPensionIDs) {
            try {
                String selectQuery = "SELECT name_pensions_id FROM hotel_pensions_relations WHERE hotel_id = ?";
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(selectQuery);
                pr.setInt(1, hotel_id);
                ResultSet rs = pr.executeQuery();
                List<Integer> existingPensionIDs = new ArrayList<>();
                while (((ResultSet) rs).next()) {
                    existingPensionIDs.add(rs.getInt("name_pensions_id"));
                }
                String upsertQuery = "INSERT INTO hotel_pensions_relations (hotel_id, name_pensions_id) VALUES (?, ?)";
                PreparedStatement prs = DBConnector.getInstance().prepareStatement(upsertQuery);

                for (String pensionID : selectedPensionIDs) {
                    int pensionIDInt = Integer.parseInt(pensionID);
                    if (!existingPensionIDs.contains(pensionIDInt)) {
                        prs.setInt(1, hotel_id);
                        prs.setInt(2, pensionIDInt);
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

    private List<String> getSelectedPensionID() {
        List<String> selectedPensionIDs = new ArrayList<>();
        if (rb_ultra_allin.isSelected()) {
            selectedPensionIDs.add(rb_ultra_allin.getActionCommand());
        }
        if (rb_allin.isSelected()) {
            selectedPensionIDs.add(rb_allin.getActionCommand());
        }
        if (rb_room_breakfast.isSelected()) {
            selectedPensionIDs.add(rb_room_breakfast.getActionCommand());
        }
        if (rb_full_pension.isSelected()) {
            selectedPensionIDs.add(rb_full_pension.getActionCommand());
        }
        if (rb_half_pension.isSelected()) {
            selectedPensionIDs.add(rb_half_pension.getActionCommand());
        }
        if (rb_justbed.isSelected()) {
            selectedPensionIDs.add(rb_justbed.getActionCommand());
        }
        if (rb_except_alcohol.isSelected()) {
            selectedPensionIDs.add(rb_except_alcohol.getActionCommand());
        }
        return selectedPensionIDs;
    }
}

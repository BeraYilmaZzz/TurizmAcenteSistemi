package com.tourismagencysystem.View;

import com.tourismagencysystem.Core.DBConnector;
import com.tourismagencysystem.Core.Helper;
import com.tourismagencysystem.Model.Period;
import com.tourismagencysystem.Model.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomGUI extends Layout {
    private JTextField fld_room_name;
    private final int hotel_id;
    private JPanel wrapper;
    private JPanel wtop;
    private JTable tbl_room_list;
    private JButton btn_back;
    JTextField fld_room_hotel_id;
    private JTextField fld_room_stok;
    private JTextField fld_room_propertys;
    private JButton btn_room_add;
    private JTextField fld_strt_date;
    private JTextField fld_fnsh_date;
    private JTextField fld_room_id;
    private JButton btn_room_delete;
    private JButton btn_update_child_price;
    private JTextField fld_room_bed;
    private JCheckBox cb_isTV;
    private JCheckBox cb_isMinibar;
    private JCheckBox cb_isGameConsole;
    private JTextField fld_room_msq;
    private JCheckBox cb_isSafe;
    private JCheckBox cb_isProjection;
    private JComboBox cmb_room_hPeriod;
    private JComboBox cmb_room_hPensions;
    private JTextField fld_adult_price;
    private JTextField fld_child_price;
    private JButton btn_update_adult_price;
    private JTextField fld_new_adult_price;
    private JTextField fld_new_child_price;
    private JTabbedPane tabbedPane1;
    private JScrollPane pnl_room;
    private JButton btn_update_room_period_pension;
    private JTextField fld_selected_room_id;
    private DefaultTableModel mdl_room_list;
    private Object[] row_room_list;
    private JComboBox getCmb_room_hPensions;
    private DefaultTableModel mdl_period_room_list;
    private Object[] row_period_room_list;
    private Period period;

    public RoomGUI(int hotel_id) {
        this.hotel_id = hotel_id;
        this.add(wrapper);
        this.guiInitilaze(1400, 1300);

        // ODALAR TABLOSU
        mdl_room_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_room_list = {"Otel ID","Oda ID", "Oda Adı","Oda Stok Miktarı","Dönem Başlangıç","Dönem Bitiş","Oda Tipi","Yatak Sayısı","Televizyon","Minibar","Oyun Konsolu","Metrekare","Kasa","Projeksiyon","Yetişkin Fiyat","Çocuk Fiyat"};
        mdl_room_list.setColumnIdentifiers(col_room_list);
        row_room_list = new Object[col_room_list.length];
        loadRoomModel();
        tbl_room_list.setModel(mdl_room_list);
        tbl_room_list.getTableHeader().setReorderingAllowed(false);
        loadPensionsBYhotelID();
        loadPeriodsByHotelID();
        tbl_room_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_hotel_id = tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 1).toString();
                fld_room_id.setText(select_hotel_id);
            } catch (Exception ex) {
            }
        });

        // ODALAR TABLOSU BİTİŞ

        btn_back.addActionListener(e -> {
            dispose();
        });

        // ODA EKLEME BUTONU
        btn_room_add.addActionListener(e -> { // 11 VE 12 NUMARALI FORM
            if (Helper.isFieldEmpty(fld_room_name) || Helper.isFieldEmpty(fld_room_stok)) {
                Helper.showMsg("fill");
            } else {
                String name = fld_room_name.getText();
                int stock = Integer.parseInt(fld_room_stok.getText());
                String periodSelection = (String) cmb_room_hPeriod.getSelectedItem();
                String pension = (String) cmb_room_hPensions.getSelectedItem();
                int bedValue = Integer.parseInt(fld_room_bed.getText());
                boolean tv = cb_isTV.isSelected();
                boolean minibar = cb_isMinibar.isSelected();
                boolean gameConsole = cb_isGameConsole.isSelected();
                int msquare = Integer.parseInt(fld_room_msq.getText());
                boolean safe = cb_isSafe.isSelected();
                boolean projector = cb_isProjection.isSelected();
                int adultPrice = Integer.parseInt(fld_adult_price.getText());
                int childPrice = Integer.parseInt(fld_child_price.getText());


                if (periodSelection != null && pension != null) {
                    // Tarih aralığını ayırın
                    String[] periodParts = periodSelection.split(" - ");
                    if (periodParts.length == 2) {
                        String periodStart = periodParts[0];
                        String periodFinish = periodParts[1];
                        if (Room.add(hotel_id, name, stock, periodStart, periodFinish, pension, bedValue, tv, minibar, gameConsole, msquare, safe, projector,adultPrice,childPrice)) {
                            Helper.showMsg("done");
                        }
                        loadRoomModel();
                    } else {
                        Helper.showMsg("Geçersiz Dönem Seçtiniz");
                    }
                } else {
                    Helper.showMsg("Lütfen bir dönem ve periyod seçiniz !");
                }
            }
        });

        // ODA EKLEME BUTONU BİTİŞ

        // ODA SİLME BUTONU
        btn_room_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_room_id)) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("ODA SİLİNECEK EMİN MİSİN ?")) {
                    int room_id = Integer.parseInt(fld_room_id.getText());
                    if (Room.delete(room_id)) {
                        Helper.showMsg("BAŞARILI");
                        loadRoomModel();
                        fld_room_id.setText(null);
                    } else {
                        Helper.showMsg("ODA SİLME GERÇEKLEŞTİRİLEMEDİ");
                    }
                }
            }
        });

        // ODA SİLME BUTONU BİTİŞ

        // YETİŞKİN FİYATI GÜNCELLEME
        btn_update_adult_price.addActionListener(e -> {
            int selectedRoom = Integer.parseInt(fld_room_id.getText());
            int newAdultPrice = Integer.parseInt(fld_new_adult_price.getText());
            if(Room.priceUpdate(selectedRoom,newAdultPrice)){
                loadRoomModel();
                Helper.showMsg("done");
            }else{
                Helper.showMsg("ODA FİYATI GÜNCELLENİRKEN HATA OLUŞTU");
            }
        });

        // YETİŞKİN FİYATI GÜNCELLEME BİTİŞ

        // ÇOCUK FİYATI GÜNCELLEME
        btn_update_child_price.addActionListener(e -> {
            int selectedRoom = Integer.parseInt(fld_room_id.getText());
            int newChildPrice = Integer.parseInt(fld_new_child_price.getText());
            if(Room.priceCupdate(selectedRoom,newChildPrice)){
                loadRoomModel();
                Helper.showMsg("done");
            }else{
                Helper.showMsg("ODA FİYATI GÜNCELLENİRKEN HATA OLUŞTU");
            }
        });

        // ÇOCUK FİYATI GÜNCELLEME BİTİŞ
    }
    public void loadRoomModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        mdl_room_list.setRowCount(0);

        for (Room room : Room.getRoomsByHotelID(hotel_id)) {
            int i = 0;
            row_room_list[i++] = room.getHotel_id();
            row_room_list[i++] = room.getId();
            row_room_list[i++] = room.getName();
            row_room_list[i++] = room.getStock();
            row_room_list[i++] = room.getPeriodStart();
            row_room_list[i++] = room.getPeriodFinish();
            row_room_list[i++] = room.getPension();
            row_room_list[i++] = room.getBedValue();
            row_room_list[i++] = room.isTv() ? "Var" : "Yok";
            row_room_list[i++] = room.isMinibar() ? "Var" : "Yok";
            row_room_list[i++] = room.isGameConsole() ? "Var" : "Yok";
            row_room_list[i++] = room.getMsquare();
            row_room_list[i++] = room.isSafe() ? "Var" : "Yok";
            row_room_list[i++] = room.isProjector() ? "Var" : "Yok";
            row_room_list [i++] = room.getAdultPrice();
            row_room_list[i++] = room.getChildPrice();
            mdl_room_list.addRow(row_room_list);
        }
    }
    private void loadPeriodsByHotelID() {
        ArrayList<String> periodList = new ArrayList<>();
        String query = "SELECT p.id, p.startDate, p.finishDate " +
                "FROM periods p " +
                "INNER JOIN period_hotel_relations phr ON p.id = phr.period_id " +
                "WHERE phr.hotel_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int periodID = rs.getInt("id");
                String startDate = rs.getString("startDate");
                String finishDate = rs.getString("finishDate");
                String combinedDate = startDate + " - " + finishDate;
                periodList.add(combinedDate);
            }
            cmb_room_hPeriod.removeAllItems(); //
            cmb_room_hPeriod.setModel(new DefaultComboBoxModel<>(periodList.toArray(new String[0])));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadPensionsBYhotelID() {
        ArrayList<String> pensionList = new ArrayList<>();
        String query = "SELECT hr.name_pensions_id, p.name " +
                "FROM hotel_pensions_relations hr " +
                "INNER JOIN hotel_pensions p ON hr.name_pensions_id = p.id " +
                "WHERE hr.hotel_id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                String pensionName = rs.getString("name");
                pensionList.add(pensionName);
            }

            JComboBox<String> comboBox = new JComboBox<String>();
            cmb_room_hPensions.removeAllItems(); //
            cmb_room_hPensions.setModel(new DefaultComboBoxModel<>(pensionList.toArray(new String[0])));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
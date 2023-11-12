package com.tourismagencysystem.View;
import com.tourismagencysystem.Core.DBConnector;
import com.tourismagencysystem.Core.Helper;
import com.tourismagencysystem.Model.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tourismagencysystem.Model.Reservation;
public class AgencyGUI extends Layout{
    private Reservation reservation;
    private JPanel wtop;
    private Hotel hotel;
    private JPanel wrapper;
    private JLabel lbl_welcome;
    private JButton btn_quit;
    private JTable tbl_hotel_list;
    private JTextField fld_sh_hotel_name;
    private JTextField fld_sh_hotel_city;
    private JButton btn_sh_hotel;
    private JTextField fld_sh_hotel_region;
    private JButton btn_hotel_delete;
    private JTextField fld_hotel_id;
    private JTextField fld_hotel_name;
    private JTextField fld_hotel_city;
    private JTextField fld_hotel_region;
    private JTextField fld_hotel_adress;
    private JTextField fld_hotel_email;
    private JTextField fld_hotel_phone;
    private JComboBox cmb_hotel_star;
    private JButton btn_hotal_add;
    private JButton btn_go_room;
    private JCheckBox cb_freePark;
    private JCheckBox cb_freeWifi;
    private JCheckBox cb_swimPool;
    private JCheckBox cb_fitness;
    private JCheckBox cb_servant;
    private JCheckBox cb_spa;
    private JCheckBox cb_allDaySwervice;
    private JCheckBox cb_ultra_allin;
    private JCheckBox cb_allin;
    private JCheckBox cb_room_breakfast;
    private JCheckBox cb_full_pension;
    private JCheckBox cb_half_pension;
    private JCheckBox cb_justBed;
    private JCheckBox cb_except_alcohol;
    private JTable tbl_period_list;
    private JButton btn_period_add;
    private JTextField fld_in_startDate;
    private JTextField fld_in_finishDate;
    private JTextField fld_period_id;
    private JButton btn_match_period_hotel;
    private JTable tbl_all_room_list;
    private JTabbedPane pane_system;
    private JScrollPane pnl_hotel;
    private JTextField fld_sh_hotel_region_city;
    private JTextField fld_sh_in_date;
    private JTextField fld_sh_out_date;
    private JTextField fld_sh_adult_num;
    private JTextField fld_sh_child_num;
    private JTextField fld_selected_room;
    private JButton btn_sh_room;
    private JPanel Rezervasyonlar;
    private JTable tbl_rez_list;
    private JTextField fld_rezerv_name;
    private JTextField fld_rezerv_note;
    private JLabel fld_rezerv_phone;
    private JButton btn_rezerved;
    private JPanel Rezervasyon;
    private JTextField fld_rez_phone;
    private JTextField fld_total_adult;
    private JTextField fld_total_child;
    private JTextField fld_plan_night;
    private JTextField fld_selected_rez;
    private JButton btn_delete_rez;
    private JComboBox cmb_period;
    private JTable tbl_selected_periods;
    private JButton btn_getPeriod_hotel;
    private JButton seçilenDönemiSilButton;
    private User user;
    private DefaultTableModel mdl_hotel_list;
    private Object[] row_hotel_list;
    private JPopupMenu hotelMenu;
    private DefaultTableModel mdl_period_list;
    private Object[] row_period_list;
    private DefaultTableModel mdl_sh_room_list;
    private Object[] row_sh_room_list;
    private DefaultTableModel mdl_sh_hotel_list;
    private Object[] row_sh_room_hotels;
    private DefaultTableModel mdl_rez_list;
    private Object[] row_rez_list;
    private DefaultTableModel mdl_hotel_periods_list;
    private Object[] row_hotel_periods_list;
    public AgencyGUI(User user) {
        this.user = user;
        this.add(wrapper);
        this.guiInitilaze(1200, 650);

        // EŞLEŞMİŞ PERİODLAR LİSTESİ
        mdl_hotel_periods_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object [] col_hotel_periods_list = {"Otel Adı","Period Başlangıç","Period Bitiş"};
        mdl_hotel_periods_list.setColumnIdentifiers(col_hotel_periods_list);
        row_hotel_periods_list = new Object[col_hotel_periods_list.length];
        tbl_selected_periods.setModel(mdl_hotel_periods_list);
        tbl_selected_periods.getTableHeader().setReorderingAllowed(false);

        // EŞLEŞMİŞ PERİOD BİTİŞ
        btn_getPeriod_hotel.addActionListener(e -> {
            loadHotelPeriods(Integer.valueOf(fld_hotel_id.getText()));
        });

        //REZERVASYON TABLOSU
        mdl_rez_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object [] col_rez_list = {"ID","Ad Soyad", "Rezervasyon Notu", "Telefon No","Oda ID","Oda Adı","Pansiyon tipi","Otel Adı","Giriş Tarihi","Çıkış Tarihi","Yetişkin sayısı","Total Yetişkin Ücret","Çocuk sayısı","Total Çocuk ücret"};
        mdl_rez_list.setColumnIdentifiers(col_rez_list);
        row_rez_list = new Object[col_rez_list.length];
        loadReservations();
        tbl_rez_list.setModel(mdl_rez_list);
        tbl_rez_list.getTableHeader().setReorderingAllowed(false);
        tbl_rez_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                int selected_rez_id = Integer.parseInt(tbl_rez_list.getValueAt(tbl_rez_list.getSelectedRow(),0).toString());
                fld_selected_rez.setText(String.valueOf(selected_rez_id));
            } catch (Exception ex) {
            }
        });
        btn_delete_rez.addActionListener(e -> { // 18 NUMARALI FORM
            String selectedRezText = fld_selected_rez.getText();
            if (Helper.isFieldEmpty(fld_selected_rez)) {
                Helper.showMsg("fill");
                return;
            }
            try {
                int selected_rez_id = Integer.parseInt(selectedRezText);
                int selected_room_id = Integer.parseInt(tbl_rez_list.getValueAt(tbl_rez_list.getSelectedRow(), 4).toString());

                if (Helper.confirm("sure")) {
                    Reservation.updatePlus(selected_room_id);
                    if (Reservation.delete(selected_rez_id)) {
                        Helper.showMsg("done");
                        loadReservations();
                        fld_selected_rez.setText(null);
                    } else {
                        Helper.showMsg("error");
                    }
                }
            } catch (NumberFormatException ex) {
                Helper.showMsg("Invalid reservation ID. Please enter a valid number.");
            }
        });
        btn_rezerved.addActionListener(e -> { // 16 VE 17 NUMARALI FORM
            String periodIdText = fld_selected_room.getText().trim();
            if (!periodIdText.isEmpty()) {
                try {
                    int selectedRoomId = Integer.parseInt(periodIdText);
                    String startDate = fld_sh_in_date.getText();
                    String finishDate = fld_sh_out_date.getText();
                    int adultNum = Integer.parseInt(fld_sh_adult_num.getText());
                    int childNum = Integer.parseInt(fld_sh_child_num.getText());
                    String name = fld_rezerv_name.getText();
                    String note = fld_rezerv_note.getText();
                    String phone = fld_rez_phone.getText();
                    String input_start_date = fld_sh_in_date.getText();
                    String input_finish_date = fld_sh_out_date.getText();
                    int nightValue = calculateNights(input_start_date, input_finish_date);
                    boolean success = Reservation.addReservation(selectedRoomId, name, note, phone , startDate, finishDate, adultNum, childNum,nightValue); // 16 VE 17 NUMARALI FORM
                    if(success){
                        loadReservations();
                        Reservation.updateMinus(selectedRoomId); // 16 VE 17 NUMARALI FORM
                    }

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Geçersiz oda ID. Lütfen geçerli bir sayı girin.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Oda ID boş olamaz. Lütfen bir değer girin.");
            }
        });

        //REZERVASYON TABLOSU BİTİŞ

        // DÖNEM TABLOSU
        mdl_period_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object [] col_period_list = {"Period ID", "Başlangıç Tarihi", "Bitiş Tarihi"};
        mdl_period_list.setColumnIdentifiers(col_period_list);
        row_period_list = new Object[col_period_list.length];
        loadPeriodModel();
        loadPeriodCheckBox();
        tbl_period_list.setModel(mdl_period_list);
        tbl_period_list.getTableHeader().setReorderingAllowed(false);
        tbl_period_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_period_id = tbl_period_list.getValueAt(tbl_period_list.getSelectedRow(), 0).toString();
                fld_period_id.setText(selected_period_id);
            } catch (Exception ex) {
            }
        });

        // DÖNEM TABLOSU BİTİŞ

        // ODA ARAMA TABLOSU
        mdl_sh_room_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object [] col_sh_room_list = {"Oda ID", "Oda Adı", "Stock","Giriş Tarihi","Çıkış Tarihi","Pansiyon tipi","Yetişkin Fiyat","Çocuk Fiyat","Otel Adı","Otel Adresi","Yıldız","Otel Özellikleri"};
        mdl_sh_room_list.setColumnIdentifiers(col_sh_room_list);
        row_sh_room_list = new Object[col_sh_room_list.length];
        tbl_all_room_list.setModel(mdl_sh_room_list);
        tbl_all_room_list.getTableHeader().setReorderingAllowed(false);
        tbl_all_room_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                int selectedRow = tbl_all_room_list.getSelectedRow();

                if (selectedRow != -1) {
                    String selected_room_id = tbl_all_room_list.getValueAt(selectedRow, 0).toString();
                    fld_selected_room.setText(selected_room_id);
                    String input_start_date = fld_sh_in_date.getText();
                    String input_finish_date = fld_sh_out_date.getText();
                    int calculateDays = calculateNights(input_start_date, input_finish_date); // 15 NUMARALI FORM
                    int adultPrice = Integer.parseInt(tbl_all_room_list.getValueAt(selectedRow, 6).toString());
                    int adultNum = Integer.parseInt(fld_sh_adult_num.getText());
                    int totalAdultPrice = adultPrice * adultNum * calculateDays;
                    int childPrice = Integer.parseInt(tbl_all_room_list.getValueAt(selectedRow, 7).toString());
                    int childNum = Integer.parseInt(fld_sh_child_num.getText());
                    int totalChildPrice = childPrice * childNum * calculateDays; // 15 NUMARALI FORM

                    fld_total_child.setText(String.valueOf(totalChildPrice));
                    fld_total_adult.setText(String.valueOf(totalAdultPrice));
                    fld_plan_night.setText(String.valueOf(calculateDays));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // ODA ARAMA BUTONU----------------------------
        btn_sh_room.addActionListener(e -> { // 13 VE 14 NUMARALI FORM
            String hotelCityRegion = fld_sh_hotel_region_city.getText().trim();
            String rezStartDate = fld_sh_in_date.getText().trim();
            String rezFinishDate = fld_sh_out_date.getText().trim();
            String query = "SELECT r.id, r.name AS room_name, r.stock, r.period_start, r.period_finish, r.pension, r.adult_price, r.child_price,h.name AS hotel_name, h.adress AS hotel_address, h.star AS hotel_star," +
                    "GROUP_CONCAT(f.name SEPARATOR ', ') AS hotel_features FROM roomlist AS r JOIN hotel AS h ON r.hotel_id = h.id LEFT JOIN hotel_prop_relations " +
                    "AS hf ON h.id = hf.hotel_id LEFT JOIN oprop AS f ON hf.feature_id = f.id WHERE h.city LIKE ? OR h.region LIKE ? OR h.name LIKE ? OR (r.period_start <= ? AND r.period_finish >= ?) " +
                    "GROUP BY r.id, r.name, r.stock, r.period_start, r.period_finish, r.pension, r.adult_price, r.child_price,h.name, h.adress, h.star";
            List<Map<String, Object>> rooms = Room.searchRoom(query, hotelCityRegion, rezStartDate, rezFinishDate);
            loadList(rooms);
        });

        // ODA ARAMA BUTONU BİTİŞ ---------------------

        // ODA ARAMA TABLOSU BİTİŞ

        // OTELLER TABLOSU
        hotelMenu = new JPopupMenu();
        JMenuItem updateProp = new JMenuItem("Otel Özelliğini güncelle");
        JMenuItem updatePension = new JMenuItem("Pansiyon Türlerini güncelle");
        hotelMenu.add(updatePension);
        hotelMenu.add(updateProp);
        updatePension.addActionListener(e -> {
            try {
                int selectedHotelID = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString());
                UpdateHotelPensionsGUI updateHotelPensionsGUI = new UpdateHotelPensionsGUI(Hotel.getFetch(selectedHotelID));
                updateHotelPensionsGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadHotelModel();
                    }
                });
            } catch (NumberFormatException ex) {
                ex.printStackTrace(); // Hata durumunu kontrol etmek için
            }
        });
        updateProp.addActionListener(e -> {
            int select_hotel_id = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString());
            UpdateHotelPropertysGUI updateHotelPropertysGUI = new UpdateHotelPropertysGUI(Hotel.getFetch(select_hotel_id));
            updateHotelPropertysGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelModel();
                }
            });
        });
        this.lbl_welcome.setText("Hoşgeldiniz = " + this.user.getName());
        mdl_hotel_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Otel Adı", "Şehir", "Bölge","Adres", "E-posta", "Telefon", "Yıldız", "Otel Özellikleri", "Pansiyon"};
        mdl_hotel_list.setColumnIdentifiers(col_user_list);
        row_hotel_list = new Object[col_user_list.length];
        loadHotelModel();
        tbl_hotel_list.setModel(mdl_hotel_list);
        tbl_hotel_list.getTableHeader().setReorderingAllowed(false);
        tbl_hotel_list.setComponentPopupMenu(hotelMenu);
        tbl_hotel_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_hotel_id = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString();
                fld_hotel_id.setText(select_hotel_id);
            } catch (Exception ex) {
            }
        });
        tbl_hotel_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point =e.getPoint();
                int selected_row=tbl_hotel_list.rowAtPoint(point);
                tbl_hotel_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        tbl_hotel_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int id = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString());
                String name = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 1).toString();
                String city = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 2).toString();
                String region = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 3).toString();
                String address = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 4).toString();
                String email = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 5).toString();
                String phone = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 6).toString();
                String star = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 7).toString();
                String otel_ozellikleri = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 8).toString();

                if (Hotel.update(id, name, city, region, address, email, phone, star, otel_ozellikleri)) {
                    Helper.showMsg("done");
                }
                loadHotelModel();
            }
        });
        //OTELLER TABLOSU BİTİŞ

        // OTEL SİLME
        btn_hotel_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_id)) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int hotel_id = Integer.parseInt(fld_hotel_id.getText());
                    if (Hotel.delete(hotel_id)) {
                        Helper.showMsg("done");
                        loadHotelModel();
                        fld_hotel_id.setText(null);
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }

        });
        //OTEL SİLME BİTİŞ

        // ODALARA GİT BUTONU
        btn_go_room.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_id)) {
                Helper.showMsg("fill");
            } else {
                int hotel_id = Integer.parseInt(fld_hotel_id.getText());
                RoomGUI roomGUI = new RoomGUI(hotel_id);
            }
        });
        //ODALARA GİT BİTİŞ

        // HOTEL ÖZ.
        cb_freePark.setActionCommand("1");
        cb_freeWifi.setActionCommand("2");
        cb_swimPool.setActionCommand("3");
        cb_fitness.setActionCommand("4");
        cb_servant.setActionCommand("5");
        cb_spa.setActionCommand("6");
        cb_allDaySwervice.setActionCommand("7");
        // OTEL TÜR.
        cb_ultra_allin.setActionCommand("1");
        cb_allin.setActionCommand("2");
        cb_room_breakfast.setActionCommand("3");
        cb_full_pension.setActionCommand("4");
        cb_half_pension.setActionCommand("5");
        cb_justBed.setActionCommand("6");
        cb_except_alcohol.setActionCommand("7");

        // OTEL EKLEME
        btn_hotal_add.addActionListener(new ActionListener() { // 9 NUMARALI FORM
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = fld_hotel_name.getText();
                String city = fld_hotel_city.getText();
                String region = fld_hotel_region.getText();
                String period = (String) cmb_period.getSelectedItem();
                String adress = fld_hotel_adress.getText();
                String email = fld_hotel_email.getText();
                String phone = fld_hotel_phone.getText();
                String star = cmb_hotel_star.getSelectedItem().toString();
                // FEATURED ÖZELLİKLERİ TOPLADIĞIM DİZE
                ArrayList<String> selectedFeatureIDs = getSelectedFeatureID();
                //oda tipi topladığım dize
                ArrayList<String> selectedPensionIDs = getSelectedPensionsID();
                // bunları çalıştırdığım fonksiyon,
                //if (upsertHotelProperties(hotelID, selectedFeatureIDs) && upsertHotelPensions(hotelID, selectedPensionIDs))

                boolean addHotel = Hotel.hotelAdd(name, city, region,period, adress, email, phone, star);
                int hotelID = 0;
                if (addHotel) {
                    loadHotelModel();
                    String query = "SELECT MAX(id) FROM hotel";
                    try {
                        PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                        ResultSet resultSet = pr.executeQuery();
                        if (resultSet.next()) {
                            hotelID = resultSet.getInt(1);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (upsertHotelProperties(hotelID, selectedFeatureIDs) && upsertHotelPensions(hotelID, selectedPensionIDs)) {
                    loadHotelModel();
                }
                loadHotelModel();
            }
        });
        // OTEL EKLEME BİTİŞ

        // OTEL ARAMA
        btn_sh_hotel.addActionListener(e -> {
            String name = fld_sh_hotel_name.getText();
            String city = fld_sh_hotel_city.getText();
            String region = fld_sh_hotel_region.getText();
            loadHotelModel(Hotel.searchHotel(name, city, region));
            fld_sh_hotel_name.setText(null);
            fld_sh_hotel_city.setText(null);
            fld_sh_hotel_region.setText(null);
        });

        //OTEL ARAMA BİTİŞ

        //PERİOD EKLEME
        btn_period_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_in_startDate) || Helper.isFieldEmpty(fld_in_finishDate)) {
                Helper.showMsg("fill");
            } else {
                String startDate = fld_in_startDate.getText();
                String finishDate = fld_in_finishDate.getText();
                    if (Period.add(startDate, finishDate)) {
                        Helper.showMsg("done");
                        loadPeriodModel();
                        loadPeriodCheckBox();
                    }
            }
        });

        // OTEL VE PERİODU EŞLE
        btn_match_period_hotel.addActionListener(a -> { // 10 NUMARALI FORM
            tbl_hotel_list.getSelectionModel().addListSelectionListener(e -> {
                try {
                    String select_hotel_id = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString();
                    fld_hotel_id.setText(select_hotel_id);
                } catch (Exception ex) {
                }
            });
            tbl_period_list.getSelectionModel().addListSelectionListener(e -> {
                try {
                    String selected_period_id = tbl_period_list.getValueAt(tbl_period_list.getSelectedRow(), 0).toString();
                    fld_period_id.setText(selected_period_id);
                } catch (Exception ex) {
                }
            });
            if (Helper.isFieldEmpty(fld_hotel_id) || Helper.isFieldEmpty(fld_period_id)) {
                Helper.showMsg("fill");
            } else {
                // Dönem ve otel ID'lerini al
                int hotel_id = Integer.parseInt(fld_hotel_id.getText());
                int period_id = Integer.parseInt(fld_period_id.getText());
                // İlgili otel ve dönemi eşleştir
                if (matchHotelPeriodID(hotel_id, period_id)) {
                    Helper.showMsg("done");
                    fld_period_id.setText(null);
                    fld_hotel_id.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        // OTEL VE PERİODU EŞLE BİTİŞ

        // ÇIKIŞ İŞLEMİ
        btn_quit.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            dispose();
        });
        seçilenDönemiSilButton.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_period_id)) {
                Helper.showMsg("null");
            } else {
                int selectedPeriodID = Integer.parseInt(fld_period_id.getText());
                Period periodToDelete = Period.getPeriodByID(selectedPeriodID);
                if (periodToDelete != null) {
                    Period.delete(selectedPeriodID);
                    Helper.showMsg("Period Silinmiştir !!");
                    loadPeriodModel();
                    fld_period_id.setText(null);
                } else {
                    Helper.showMsg("Periyot Bulunamadı !!");
                }
            }
            loadPeriodModel();
        });
    }

    private void loadHotelPeriods(int hotel_id) {
        mdl_hotel_periods_list.setRowCount(0);
        for (String[] period : Period.getListBYhotelID(hotel_id)) {
            mdl_hotel_periods_list.addRow(period);
        }
    }

    private static int calculateNights(String inputStartDate, String inputFinishDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(inputStartDate, formatter);
        LocalDate finishDate = LocalDate.parse(inputFinishDate, formatter);
        long daysBetween = ChronoUnit.DAYS.between(startDate, finishDate);
        return Math.toIntExact(daysBetween);
    }


    private void loadList(List<Map<String, Object>> rooms) {
        DefaultTableModel mdl_sh_room_list = (DefaultTableModel) tbl_all_room_list.getModel();
        mdl_sh_room_list.setRowCount(0);
        for (Map<String, Object> room : rooms) {
            Object[] row_sh_room_list = new Object[12];

            row_sh_room_list[0] = room.get("id");
            row_sh_room_list[1] = room.get("room_name");
            row_sh_room_list[2] = room.get("stock");
            row_sh_room_list[3] = room.get("period_start");
            row_sh_room_list[4] = room.get("period_finish");
            row_sh_room_list[5] = room.get("pension");
            row_sh_room_list[6] = room.get("adult_price");
            row_sh_room_list[7] = room.get("child_price");
            row_sh_room_list[8] = room.get("hotel_name");
            row_sh_room_list[9] = room.get("hotel_address");
            row_sh_room_list[10] = room.get("hotel_star");
            row_sh_room_list[11] = room.get("hotel_features");

            mdl_sh_room_list.addRow(row_sh_room_list);
        }
    }
    private void loadReservations() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_rez_list.getModel();
        clearModel.setRowCount(0);
        for (Reservation obj : Reservation.getList()) {

            int i = 0;
            row_rez_list[i++] = obj.getId();
            row_rez_list[i++] = obj.getName();
            row_rez_list[i++] = obj.getNote();
            row_rez_list[i++] = obj.getPhone();
            row_rez_list[i++] = obj.getRoom_id();
            row_rez_list[i++] = obj.getRoomName();
            row_rez_list[i++] = obj.getPension();
            row_rez_list[i++] = obj.getHotelName();
            row_rez_list[i++] = obj.getStartDate();
            row_rez_list[i++] = obj.getFinishDate();
            row_rez_list[i++] = obj.getAdultNum();
            row_rez_list[i++] = obj.getAdultPrice();
            row_rez_list[i++] = obj.getChildNum();
            row_rez_list[i++] = obj.getChildPrice();

            mdl_rez_list.addRow(row_rez_list);
        }
    }
    private void loadHotelModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Hotel obj : Hotel.getList()) {
            Object[] row = new Object[9];
            i = 0;
            row_hotel_list[i++] = obj.getId();
            row_hotel_list[i++] = obj.getName();
            row_hotel_list[i++] = obj.getCity();
            row_hotel_list[i++] = obj.getRegion();
            row_hotel_list[i++] = obj.getAddress();
            row_hotel_list[i++] = obj.getEmail();
            row_hotel_list[i++] = obj.getPhone();
            row_hotel_list[i++] = obj.getStar();
            row_hotel_list[i++] = obj.getOtel_ozellikleri();
            row_hotel_list[i++] = obj.getHotel_pension();

            mdl_hotel_list.addRow(row_hotel_list);
        }
    }
    private boolean matchHotelPeriodID(int hotel_id, int period_id) { // 10 NUMARALI FORM
        String query = "INSERT INTO period_hotel_relations (hotel_id, period_id) VALUES (?, ?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            pr.setInt(2, period_id);
            int response = pr.executeUpdate();
            return response != -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void loadPeriodCheckBox(){
        ArrayList<String> periodlist = new ArrayList<>();
        String query = "SELECT * FROM periods";
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int periodID = rs.getInt("id");
                String startDate = rs.getString("startDate");
                String finishDate = rs.getString("finishDate");
                String combinedDate = startDate + " - " + finishDate;
                periodlist.add(combinedDate);
            }
            cmb_period.removeAllItems();
            cmb_period.setModel(new DefaultComboBoxModel<>(periodlist.toArray(new String[0])));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadPeriodModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_period_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Period obj : Period.getList()) {
            Object[] row = new Object[9];
            i = 0;
            row_period_list[i++] = obj.getId();
            row_period_list[i++] = obj.getStartDate();
            row_period_list[i++] = obj.getFinishDate();

            mdl_period_list.addRow(row_period_list);
        }
    }
    // Seçilen feature_id'leri almak için bir metod
    private ArrayList<String> getSelectedFeatureID() {
        ArrayList<String> selectedFeatureID = new ArrayList<>();
        if (cb_freePark.isSelected()) {
            selectedFeatureID.add(cb_freePark.getActionCommand());
        }
        if (cb_freeWifi.isSelected()) {
            selectedFeatureID.add(cb_freeWifi.getActionCommand());
        }
        if (cb_swimPool.isSelected()) {
            selectedFeatureID.add(cb_swimPool.getActionCommand());
        }
        if (cb_fitness.isSelected()) {
            selectedFeatureID.add(cb_fitness.getActionCommand());
        }
        if (cb_servant.isSelected()) {
            selectedFeatureID.add(cb_servant.getActionCommand());
        }
        if (cb_spa.isSelected()) {
            selectedFeatureID.add(cb_spa.getActionCommand());
        }
        if (cb_allDaySwervice.isSelected()) {
            selectedFeatureID.add(cb_allDaySwervice.getActionCommand());
        }
        return selectedFeatureID;
    }
    private ArrayList<String> getSelectedPensionsID() {
        ArrayList<String> selectedPensionsID = new ArrayList<>();

        if (cb_ultra_allin.isSelected()) {
            selectedPensionsID.add(cb_ultra_allin.getActionCommand());
        }
        if (cb_allin.isSelected()) {
            selectedPensionsID.add(cb_allin.getActionCommand());
        }
        if (cb_room_breakfast.isSelected()) {
            selectedPensionsID.add(cb_room_breakfast.getActionCommand());
        }
        if (cb_full_pension.isSelected()) {
            selectedPensionsID.add(cb_full_pension.getActionCommand());
        }
        if (cb_half_pension.isSelected()) {
            selectedPensionsID.add(cb_half_pension.getActionCommand());
        }
        if (cb_justBed.isSelected()) {
            selectedPensionsID.add(cb_justBed.getActionCommand());
        }
        if (cb_except_alcohol.isSelected()) {
            selectedPensionsID.add(cb_except_alcohol.getActionCommand());
        }
        return selectedPensionsID;
    }
    public boolean upsertHotelPensions(int hotelID, ArrayList<String> selectedPensionIDs) {
        try {
            String upsertQuery = "INSERT INTO hotel_pensions_relations (hotel_id, name_pensions_id) VALUES (?, ?)";
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(upsertQuery);
            List<Integer> existingPensionIDs = new ArrayList<>();
            for (String pensionID : selectedPensionIDs) {
                int pensionIDInt = Integer.parseInt(pensionID);
                existingPensionIDs.add(pensionIDInt);
                pr.setInt(1, hotelID);
                pr.setInt(2, pensionIDInt);
                pr.addBatch();
            }
            int[] rowsUpdated = pr.executeBatch();
            return rowsUpdated.length > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean upsertHotelProperties(int hotelID, ArrayList<String> selectedFeatureIDs) {
        try {
            String upsertQuery = "INSERT INTO hotel_prop_relations (hotel_id, feature_id) VALUES (?, ?)";
            PreparedStatement upsertStatement = DBConnector.getInstance().prepareStatement(upsertQuery);
            List<Integer> existingFeatureIDs = new ArrayList<>();
            for (String featureID : selectedFeatureIDs) {
                int featureIDInt = Integer.parseInt(featureID);
                existingFeatureIDs.add(featureIDInt);
                upsertStatement.setInt(1, hotelID);
                upsertStatement.setInt(2, featureIDInt);
                upsertStatement.addBatch();
            }
            int[] rowsUpdated = upsertStatement.executeBatch();
            return rowsUpdated.length > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void loadHotelModel (ArrayList < Hotel > List) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Hotel obj : List) {
            i = 0;
            row_hotel_list[i++] = obj.getId();
            row_hotel_list[i++] = obj.getName();
            row_hotel_list[i++] = obj.getCity();
            row_hotel_list[i++] = obj.getRegion();
            row_hotel_list[i++] = obj.getAddress();
            row_hotel_list[i++] = obj.getEmail();
            row_hotel_list[i++] = obj.getPhone();
            row_hotel_list[i++] = obj.getStar();
            row_hotel_list[i++] = obj.getOtel_ozellikleri();
            row_hotel_list[i++] = obj.getHotel_pension();
            mdl_hotel_list.addRow(row_hotel_list);
        }
    }
    private void showPopupMenu(MouseEvent e) {
        if (e.isPopupTrigger()) {
            int row = tbl_hotel_list.rowAtPoint(e.getPoint());
            int column = tbl_hotel_list.columnAtPoint(e.getPoint());
            if (row >= 0 && row < tbl_hotel_list.getRowCount()) {
                tbl_hotel_list.setRowSelectionInterval(row, row);
            } else {
                tbl_hotel_list.clearSelection();
            }
            hotelMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
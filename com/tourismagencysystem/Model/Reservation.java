package com.tourismagencysystem.Model;
import com.tourismagencysystem.Core.DBConnector;
import com.tourismagencysystem.Core.Helper;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Reservation {
    private int id;
    private String name;
    private String note;
    private String phone;
    private int room_id;
    private String startDate;
    private String finishDate;
    private String pension;
    private int adultNum;
    private int adultPrice;
    private int childNum;
    private int childPrice;
    private String roomName;
    private String hotelName;
    public Reservation(int id, String name, String note, String phone, int room_id, String roomName, String pension, String hotelName, String startDate, String finishDate, int adultNum, int adultPrice, int childNum, int childPrice) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.phone = phone;
        this.room_id = room_id;
        this.roomName = roomName;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.pension = pension;
        this.adultNum = adultNum;
        this.adultPrice = adultPrice;
        this.childNum = childNum;
        this.childPrice = childPrice;
        this.hotelName = hotelName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getPension() {
        return pension;
    }

    public void setPension(String pension) {
        this.pension = pension;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(int adultNum) {
        this.adultNum = adultNum;
    }

    public int getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(int adultPrice) {
        this.adultPrice = adultPrice;
    }

    public int getChildNum() {
        return childNum;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }

    public int getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(int childPrice) {
        this.childPrice = childPrice;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
    public static boolean updatePlus(int selectedRoomId) {
        String sql = "UPDATE roomlist SET stock = stock + 1 WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setInt(1, selectedRoomId);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean updateMinus(int selectedRoomId) {
        String sql = "UPDATE roomlist SET stock = stock - 1 WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setInt(1, selectedRoomId);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static ArrayList<Reservation> getList() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.id, r.name, r.note, r.phone, r.room_id, r.room_name, r.pension, r.hotel_name, r.start_rez, r.finish_rez, r.adult_num, r.total_adult_price, r.child_num, r.total_child_price, " +
                "rl.name AS room_name " +
                "FROM reservations r " +
                "JOIN roomlist rl ON r.room_id = rl.id";
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("note"),
                        rs.getString("phone"),
                        rs.getInt("room_id"),
                        rs.getString("room_name"),
                        rs.getString("pension"),
                        rs.getString("hotel_name"),
                        rs.getString("start_rez"),
                        rs.getString("finish_rez"),
                        rs.getInt("adult_num"),
                        rs.getInt("total_adult_price"),
                        rs.getInt("child_num"),
                        rs.getInt("total_child_price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // 16 NUMARALI FORM
    public static boolean addReservation(int selectedRoomId, String name, String note, String phone, String startDate, String finishDate, int adultNum, int childNum, int nightValue) {
        String insertQuery = "INSERT INTO reservations (name, note, phone, room_id, room_name, pension, start_rez, finish_rez, hotel_name, adult_num, total_adult_price, child_num, total_child_price)" +
                " SELECT ?, ?, ?, ? AS room_id, rl.name AS room_name, rl.pension, ?, ?, " +
                "(SELECT name FROM hotel WHERE id = rl.hotel_id) AS hotel_name, ?, ? * rl.adult_price * ? AS total_adult_price, " +
                "?, ? * rl.child_price * ? AS total_child_price FROM roomlist rl WHERE rl.id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(insertQuery);
            pr.setString(1, name);
            pr.setString(2, note);
            pr.setString(3, phone);
            pr.setInt(4, selectedRoomId);
            pr.setString(5, startDate);
            pr.setString(6, finishDate);
            pr.setInt(7, adultNum);
            pr.setInt(8, nightValue);
            pr.setInt(9, adultNum);
            pr.setInt(10, childNum);
            pr.setInt(11, nightValue);
            pr.setInt(12, childNum);
            pr.setInt(13,selectedRoomId);

            int affectedRows = pr.executeUpdate();
            if (affectedRows > 0) {
                Helper.showMsg("Rezervasyon Kaydı Başarılı");
                return true;
            } else {
                Helper.showMsg("Rezervasyon Oluşturulurken Bir Hata Meydana Geldi");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Rezervasyon eklenirken bir hata oluştu. Lütfen girdilerinizi kontrol edip tekrar deneyin.");
        }
        return false;
    }
    public static boolean delete(int rez_id){ // 18 NUMARALI FORM
        String sql ="DELETE FROM reservations WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setInt(1,rez_id);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
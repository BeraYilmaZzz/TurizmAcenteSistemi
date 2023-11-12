package com.tourismagencysystem.Model;
import com.tourismagencysystem.Core.DBConnector;
import com.tourismagencysystem.Core.Helper;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private int hotel_id;
    private int id;
    private String name;
    private int stock;
    private int bedValue;
    private boolean tv;
    private boolean minibar;
    private boolean gameConsole;
    private int msquare;
    private boolean safe;
    private boolean projector;
    private String periodStart;
    private String pension;
    private int adultPrice;
    private int childPrice;
    private String finishDate;
    public Room(int id, String name, int stock, String periodStart,String finishDate,String pension,int bedValue, boolean tv, boolean minibar, boolean gameConsole, int msquare, boolean safe, boolean projector,int adultPrice,int childPrice){
    }
    public Room (int id , String name , int stock,String periodStart,String finishDate,String pension,int adultPrice,int childPrice){
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.periodStart=periodStart;
        this.finishDate= finishDate;
        this.pension=pension;
        this.adultPrice =adultPrice;
        this.childPrice=childPrice;
    }
    public Room(int hotel_id, int id, String name, int stock,String periodStart,String finishDate,String pension, int bedValue, boolean tv, boolean minibar, boolean gameConsole, int msquare, boolean safe, boolean projector,int adultPrice,int childPrice) {
        this.hotel_id = hotel_id;
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.periodStart=periodStart;
        this.finishDate= finishDate;
        this.pension=pension;
        this.bedValue = bedValue;
        this.tv = tv;
        this.minibar = minibar;
        this.gameConsole = gameConsole;
        this.msquare = msquare;
        this.safe = safe;
        this.projector = projector;
        this.adultPrice =adultPrice;
        this.childPrice=childPrice;
    }

    public Room(int hotelId, int roomid, String name, int adultPrice, int childPrice, String pension) {
        this.hotel_id = hotel_id;
        this.name = name;
        this.stock = stock;
        this.adultPrice = this.adultPrice;
        this.childPrice=childPrice;
        this.pension=pension;
    }

    public Room(int adultPrice, int childPrice) {
        this.adultPrice =adultPrice;
        this.childPrice=childPrice;
    }

    public Room(int id, String name, int adultPrice, int childPrice, String pension, int hotelId) {
        this.id = id;
        this.name = name;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.pension = pension;
        this.hotel_id = hotel_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public String getPeriodFinish() {
        return finishDate;
    }

    public String getPension() {
        return pension;
    }

    public void setPension(String pension) {
        this.pension = pension;
    }

    public int getBedValue() {
        return bedValue;
    }

    public void setBedValue(int bedValue) {
        this.bedValue = bedValue;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isMinibar() {
        return minibar;
    }

    public void setMinibar(boolean minibar) {
        this.minibar = minibar;
    }

    public boolean isGameConsole() {
        return gameConsole;
    }

    public void setGameConsole(boolean gameConsole) {
        this.gameConsole = gameConsole;
    }

    public int getMsquare() {
        return msquare;
    }

    public void setMsquare(int msquare) {
        this.msquare = msquare;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public boolean isProjector() {
        return projector;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public int getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(int adultPrice) {
        this.adultPrice = adultPrice;
    }

    public int getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(int childPrice) {
        this.childPrice = childPrice;
    }
    public static List<Map<String, Object>> searchRoom(String query, String hotelCityRegion, String rezStartDate, String rezFinishDate) { // 13 VE 14 NUMARALI FORM
        List<Map<String, Object>> roomList = new ArrayList<>();
        try (PreparedStatement pr = DBConnector.getInstance().prepareStatement(query)) {
            pr.setString(1, "%" + hotelCityRegion + "%");
            pr.setString(2, "%" + hotelCityRegion + "%");
            pr.setString(3, "%" + hotelCityRegion + "%");
            pr.setString(4, rezStartDate);
            pr.setString(5, rezFinishDate);

            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Map<String, Object> roomMap = new HashMap<>();

                roomMap.put("id", rs.getInt("id"));
                roomMap.put("room_name", rs.getString("room_name"));
                roomMap.put("stock", rs.getInt("stock"));
                roomMap.put("period_start", rs.getString("period_start"));
                roomMap.put("period_finish", rs.getString("period_finish"));
                roomMap.put("pension", rs.getString("pension"));
                roomMap.put("adult_price", rs.getInt("adult_price"));
                roomMap.put("child_price", rs.getInt("child_price"));
                roomMap.put("hotel_name", rs.getString("hotel_name"));
                roomMap.put("hotel_address", rs.getString("hotel_address"));
                roomMap.put("hotel_star", rs.getInt("hotel_star"));
                roomMap.put("hotel_features", rs.getString("hotel_features"));

                roomList.add(roomMap);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomList;
    }
    public static ArrayList<Room> getRoomsByHotelID(int hotelID) {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM roomlist WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, hotelID);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int stock = rs.getInt("stock");
                String periodStart = rs.getString("period_start");
                String finishDate = rs.getString("period_finish");
                String pension = rs.getString("pension");
                int bedValue = rs.getInt("bed_num");
                boolean tv = rs.getBoolean("tv");
                boolean minibar = rs.getBoolean("minibar");
                boolean gameConsole = rs.getBoolean("playstation");
                int msquare = rs.getInt("msquare");
                boolean safe = rs.getBoolean("security_case");
                boolean projector = rs.getBoolean("projection");
                int adultPrice = rs.getInt("adult_price");
                int childPrice = rs.getInt("child_price");

                Room room = new Room(hotelID, id, name, stock,periodStart,finishDate,pension,bedValue, tv, minibar, gameConsole, msquare, safe, projector,adultPrice,childPrice);
                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }
    // 11 VE 12 NUMARALI FORM
    public static boolean add(int hotel_id, String name, int stock, String periodStart,String finishDate,String pension,int bedValue, boolean tv, boolean minibar, boolean gameConsole, int msquare, boolean safe, boolean projector, int adultPrice, int childPrice) {
        String query = "INSERT INTO roomlist (hotel_id, name, stock,period_start,period_finish,pension, bed_num, tv, minibar, playstation, msquare, security_case, projection,adult_price,child_price) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            pr.setString(2, name);
            pr.setInt(3, stock);
            pr.setString(4,periodStart);
            pr.setString(5,finishDate);
            pr.setString(6,pension);
            pr.setInt(7, bedValue);
            pr.setBoolean(8, tv);
            pr.setBoolean(9, minibar);
            pr.setBoolean(10, gameConsole);
            pr.setInt(11, msquare);
            pr.setBoolean(12, safe);
            pr.setBoolean(13, projector);
            pr.setInt(14,adultPrice);
            pr.setInt(15,childPrice);
            int response = pr.executeUpdate();
            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }  // 11 VE 12 NUMARALI FORM
public static boolean delete(int room_id) {
    String query = "DELETE FROM roomlist WHERE id = ?";
    try {
        PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
        pr.setInt(1, room_id);

        int result = pr.executeUpdate();
        return result > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public static boolean priceUpdate(int selectedRoom, int newAdultPrice) {
        String query = "UPDATE roomlist SET adult_price = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, newAdultPrice);
            preparedStatement.setInt(2, selectedRoom);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean priceCupdate(int selectedRoom, int newChildPrice) {
        String query = "UPDATE roomlist SET child_price = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, newChildPrice);
            preparedStatement.setInt(2, selectedRoom);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
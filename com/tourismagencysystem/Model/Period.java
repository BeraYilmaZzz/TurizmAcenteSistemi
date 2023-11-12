package com.tourismagencysystem.Model;

import com.tourismagencysystem.Core.DBConnector;
import com.tourismagencysystem.Core.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Period {
    private int id;
    private String startDate;
    private String finishDate;
    private String hotelName;

    public Period(int id, String startDate, String finishDate) {
        this.id=id;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Period(String hotelName, String startDate, String finishDate) {
        this.hotelName=hotelName;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public String[] split(String s) {
        return s.split(" - ");
    }
    public static void delete(int selectedPeriodID) {
        String deleteRelationsSql = "DELETE FROM period_hotel_relations WHERE period_id = ?";
        String deletePeriodSql = "DELETE FROM periods WHERE id = ?";
        String deleteRoomsSql = "DELETE FROM roomlist WHERE period_start = ? AND period_finish = ?";

        try (
                PreparedStatement prRelations = DBConnector.getInstance().prepareStatement(deleteRelationsSql);
                PreparedStatement prPeriods = DBConnector.getInstance().prepareStatement(deletePeriodSql);
                PreparedStatement prRooms = DBConnector.getInstance().prepareStatement(deleteRoomsSql)
        ) {
            // period_hotel_relations tablosundan ilgili period_id'yi sildim
            prRelations.setInt(1, selectedPeriodID);
            prRelations.executeUpdate();

            // periods tablosundan ilgili id'yi sildim
            prPeriods.setInt(1, selectedPeriodID);
            prPeriods.executeUpdate();

            // roomlist tablosundan ilgili startDate ve finishDate'e sahip odaları sildim
            Period periodToDelete = getPeriodByID(selectedPeriodID);
            prRooms.setString(1, periodToDelete.getStartDate());
            prRooms.setString(2, periodToDelete.getFinishDate());
            prRooms.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Period getPeriodByID(int periodID) {
        String sql = "SELECT * FROM periods WHERE id = ?";
        try (PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql)) {
            pr.setInt(1, periodID);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String startDate = rs.getString("startDate");
                String finishDate = rs.getString("finishDate");
                return new Period(id, startDate, finishDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static boolean add(String startDate, String finishDate) {
        String query = "INSERT INTO periods (startDate, finishDate) VALUES (?, ?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, startDate);
            pr.setString(2, finishDate);
            int response = pr.executeUpdate();

            if (response == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Helper.showMsg("error");
            return false;
        }
        return true;
    }
    public static ArrayList<String[]> getListBYhotelID(int hotel_id) {
        ArrayList<String[]> dateRanges = new ArrayList<>();
        String sql = "SELECT p.startDate, p.finishDate, h.name AS hotelName " +
                "FROM periods p " +
                "INNER JOIN period_hotel_relations phr ON p.id = phr.period_id " +
                "INNER JOIN hotel h ON phr.hotel_id = h.id " +
                "WHERE phr.hotel_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                String hotelName = rs.getString("hotelName");
                String startDate = rs.getString("startDate");
                String finishDate = rs.getString("finishDate");
                dateRanges.add(new String[]{hotelName, startDate, finishDate});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dateRanges;
    }
    public static ArrayList<Period> getList() {
        ArrayList<Period> dateRanges = new ArrayList<>();
        String sql = "SELECT * FROM periods";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String startDate = rs.getString("startDate");
                String finishDate = rs.getString("finishDate");
                dateRanges.add(new Period(id,startDate, finishDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dateRanges;
    }
}
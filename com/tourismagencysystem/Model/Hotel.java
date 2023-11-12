package com.tourismagencysystem.Model;
import com.tourismagencysystem.Core.DBConnector;
import com.tourismagencysystem.Core.Helper;

import java.sql.*;
import java.util.ArrayList;
public class Hotel {
    private int id;
    private String name;
    private String city;
    private String region;
    private String address;
    private String email;
    private String phone;
    private String star;
    private String otel_ozellikleri;
    private String start_date;
    private String hotel_pensions;
    public Hotel(int id, String name, String city, String region ,String address, String email, String phone, String star, String otel_ozellikleri, String hotel_pensions) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.region = region;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.star = star;
        this.otel_ozellikleri = otel_ozellikleri;
        this.hotel_pensions = hotel_pensions;
    }
    public Hotel(int id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getOtel_ozellikleri() {
        return String.valueOf(otel_ozellikleri);
    }

    public String getHotel_pension() {
        return String.valueOf(hotel_pensions);
    }
    public static ArrayList<Hotel> searchHotel(String name, String city, String region) {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String query = "SELECT h.id, h.name, h.city, h.region, h.adress, h.email, h.phone, h.star, " +
                "GROUP_CONCAT(DISTINCT f.name ORDER BY f.name ASC SEPARATOR ', ') AS otel_ozellikleri, " +
                "GROUP_CONCAT(DISTINCT n.name ORDER BY n.name ASC SEPARATOR ', ') AS hotel_pension " +
                "FROM hotel AS h " +
                "LEFT JOIN hotel_prop_relations AS hf ON h.id = hf.hotel_id " +
                "LEFT JOIN oprop AS f ON hf.feature_id = f.id " +
                "LEFT JOIN hotel_pensions_relations AS hpn ON h.id = hpn.hotel_id " +
                "LEFT JOIN hotel_pensions AS n ON hpn.name_pensions_id = n.id " +
                "WHERE h.name LIKE ? AND h.city LIKE ? AND h.region LIKE ? " +
                "GROUP BY h.id";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, "%" + name + "%");
            pr.setString(2, "%" + city + "%");
            pr.setString(3, "%" + region + "%");

            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Hotel obj = new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("region"),
                        rs.getString("adress"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("star"),
                        rs.getString("otel_ozellikleri"),
                        rs.getString("hotel_pension")
                );
                hotelList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelList;
    }
    public static boolean delete(int hotel_id) {
        try {
            String query1 = "DELETE FROM hotel_prop_relations WHERE hotel_id = ?";
            PreparedStatement pr1 = DBConnector.getInstance().prepareStatement(query1);
            pr1.setInt(1, hotel_id);
            pr1.executeUpdate();

            // Daha sonra oteli hotel tablosundan siliyoruz
            String query = "DELETE FROM hotel WHERE id = ?";
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean hotelAdd(String name, String city, String region, String period, String adress, String email, String phone, String star) { // 9 NUMARALI FORM
        String hotelSql = "INSERT INTO hotel (name, city, region, adress, email, phone, star) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            // Oteli ekleyelim
            PreparedStatement prHotel = DBConnector.getInstance().prepareStatement(hotelSql, Statement.RETURN_GENERATED_KEYS);
            prHotel.setString(1, name);
            prHotel.setString(2, city);
            prHotel.setString(3, region);
            prHotel.setString(4, adress);
            prHotel.setString(5, email);
            prHotel.setString(6, phone);
            prHotel.setString(7, star);
            int affectedRows = prHotel.executeUpdate();
            if (affectedRows > 0) {
                // Eklenen otelin ID'sini alalım
                ResultSet generatedKeys = prHotel.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int hotelId = generatedKeys.getInt(1);
                    int periodId = getPeriodIdByDateRange(period);
                    boolean periodHotelRelationResult = addPeriodHotelRelation(hotelId, periodId);

                    if (periodHotelRelationResult) {
                        System.out.println("Hotel and relation added successfully.");
                        return true;
                    } else {
                        System.out.println("Error adding relation.");
                    }
                } else {
                    System.out.println("Error getting hotel ID.");
                }
            } else {
                System.out.println("Hotel addition failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static int getPeriodIdByDateRange(String period) {
        String[] periodParts = split(period);
        if (periodParts.length != 2) {
            return -1;
        }
        String startDate = periodParts[0];
        String finishDate = periodParts[1];
        String sql = "SELECT id FROM periods WHERE startDate = ? AND finishDate = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setString(1, startDate);
            pr.setString(2, finishDate);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    // Period'u ayrıştıran metod
    public static String[] split(String s) {
        return s.split(" - ");
    }
    private static boolean addPeriodHotelRelation(int hotelId, int periodId) {
        String relationSql = "INSERT INTO period_hotel_relations (hotel_id, period_id) VALUES (?, ?)";
        try {

            PreparedStatement prRelation = DBConnector.getInstance().prepareStatement(relationSql);
            prRelation.setInt(1, hotelId);
            prRelation.setInt(2, periodId);
            return prRelation.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(int id, String name, String city, String region, String adress, String email, String phone, String star, String otel_ozellikleri) {
        String query = "UPDATE hotel AS h " +
                "LEFT JOIN hotel_prop_relations AS hf ON h.id = hf.hotel_id " +
                "LEFT JOIN oprop AS f ON hf.feature_id = f.id " +
                "SET h.name = ?, h.city = ?, h.region = ?, h.adress = ?, h.email = ?, h.phone = ?, h.star = ?, h.otel_ozellikleri = GROUP_CONCAT(f.name SEPARATOR ',') " +
                "WHERE h.id = ?";
        User findUser = User.getFetch(name);
        if (findUser != null && findUser.getId() != id) {
            Helper.showMsg("Girdiğiniz kullanıcı adı kullanılmaktadır");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, city);
            pr.setString(3, region);
            pr.setString(4, adress);
            pr.setString(5, email);
            pr.setString(6, phone);
            pr.setString(7, star);
            pr.setInt(8, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Hotel> getList() {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        Hotel obj;

        String sql = "SELECT h.id, h.name,h.adress, h.city, h.region, h.email, h.phone, h.star,\n" +
                "       GROUP_CONCAT(DISTINCT f.name SEPARATOR ', ') AS otel_ozellikleri,\n" +
                "       GROUP_CONCAT(DISTINCT n.name SEPARATOR ', ') AS hotel_pension\n" +
                "FROM hotel AS h\n" +
                "LEFT JOIN hotel_prop_relations AS hf ON h.id = hf.hotel_id\n" +
                "LEFT JOIN oprop AS f ON hf.feature_id = f.id \n" +
                "LEFT JOIN hotel_pensions_relations AS hpn ON h.id = hpn.hotel_id\n" +
                "LEFT JOIN hotel_pensions AS n ON hpn.name_pensions_id = n.id \n" +
                "GROUP BY h.id";
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                obj = new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("region"),
                        rs.getString("adress"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("star"),
                        rs.getString("otel_ozellikleri"),
                        rs.getString("hotel_pension")
                );
                hotelList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelList;
    }
    public static Hotel getFetch(int select_hotel_id) {
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE id = ? ";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,select_hotel_id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = new Hotel(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return obj;
    }
}
package com.tourismagencysystem.Model;

import com.tourismagencysystem.Core.DBConnector;
import com.tourismagencysystem.Core.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class User {
    private int id;
    private String name;
    private String uname;
    private String pass;
    private String type;
    public User(){

    }
    public User(int id, String name, String uname, String pass, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.type = type;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public static ArrayList<User> getList(){
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        User obj ;
        try{
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return userList;
    }
    public static boolean add(String name, String uname, String pass, String type) {
        String query ="INSERT INTO user (name,uname,pass,type) VALUES (?,?,?,?)";
        User findUser = User.getFetch(uname);
        if(findUser != null){
            Helper.showMsg("Girdiğiniz kullanıcı adı kullanılmaktadır");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            int response =pr.executeUpdate();
            if(response == -1){
                Helper.showMsg("error");
            }
            return response != -1 ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    public static User getFetch(String uname){
        User obj = null;
        String query = "SELECT * FROM user WHERE uname = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,uname);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return obj;
    }
    public static User getFetch(String uname, String pass){ // 8 NUMARALI FORM
        User obj = null;
        String query = "SELECT * FROM user WHERE uname = ? AND pass = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,uname);
            pr.setString(2,pass);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                switch (rs.getString("type")){
                    case "Admin":
                        obj =  new Admin();
                        break;
                    default:
                        obj = new User();
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean update(int id, String name, String uname, String pass, String type){
        String query = "UPDATE user SET name=?,uname=?,pass=?,type=? WHERE id=?";
        User findUser = User.getFetch(uname);
        if(findUser != null && findUser.getId() != id){
            Helper.showMsg("Girdiğiniz kullanıcı adı kullanılmaktadır");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList = new ArrayList<>();
        User obj ;
        try{
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return userList;
    }
    public static String searchQuery(String uname, String name, String type) {
        String query = "SELECT * FROM user WHERE uname LIKE '%" + uname + "%' AND name LIKE '%" + name + "%'";
        if (type != null && !type.isEmpty()) {
            query += " AND type = '" + type + "'";
        }

        return query;
    }
    public static boolean delete(int id){
        String query ="DELETE FROM user WHERE id = ?";
        ArrayList<User> userList = User.getListByUser(id);
        for(User c : userList){
            User.delete(c.getId());
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);


            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static ArrayList<User> getListByUser(int user_id) {
        ArrayList<User> courseList = new ArrayList<>();
        User obj;
        Statement st = null;
        try {
            st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM user WHERE id =" + user_id);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String uname=rs.getString("uname");
                String pass = rs.getString("pass");
                String type = rs.getString("type");
                obj =new User();
                courseList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;

/**
 *
 * @author mohan
 */
@ManagedBean
@RequestScoped
public class SellerActions {

    /**
     * Creates a new instance of SellerActions
     */
    //item 
    private int itemid;
    private String category;
    private String itemname;
    private String sellerId;
    private int itemsAvailable;
    private int keepAliveCount;

//    public SellerActions() {
//    }
    public String additems(String id) {
        sellerId = id;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return ("Internal error.");
        }

        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/mohantya5485";
            //connect to the database with user name and password
            conn = DriverManager.getConnection(DB_URL,
                    "mohantya5485", "1549512");
            stat = conn.createStatement();
            //to search if the id already exists

            rs = stat.executeQuery("select nextId from nextitemid");
            if (rs.next()) {
                itemid = rs.getInt(1);
            }
            int s = stat.executeUpdate("update nextitemid set nextId = '" + (rs.getInt(1) + 1) + "'");

            int r = stat.executeUpdate("insert into item values('" + itemid + "','" + category + "','" + itemname + "','" + sellerId + "','" + itemsAvailable + "','" + keepAliveCount + "')");
            
            return("items added successfully.return to your account");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal Error! Please try again later.");
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
    public String view(){
         try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return ("Internal error.");
        }

        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/mohantya5485";
            //connect to the database with user name and password
            conn = DriverManager.getConnection(DB_URL,
                    "mohantya5485", "1549512");
            stat = conn.createStatement();
            rs= stat.executeQuery("Select * from item");
            if(rs.next()){
             //   while(rs.next()){
                return ("you have item actegory as"+"\t"+rs.getString("Category")+"\t"+"that hold "+"\t"+rs.getString("itemName")+"\t"+"having quantity of" +"\t"+rs.getInt("itemAvailable")+"\t"+"and keep alive count of"+"\t"+rs.getInt("keepAliveCount"));
                //return("<tr><td>"+rs.getString("Category")+"</td><td>"+rs.getString("itemName")+"</td><td>"+rs.getInt("itemAvailable")+"</td><td>"+rs.getInt("keepAliveCount")+"</td></tr>");
              //  }
            }
            else{
                return ("there are no items present to view");
            }
            
            } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal Error! Please try again later.");
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    
        
        
        
        
        
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public int getItemsAvailable() {
        return itemsAvailable;
    }

    public void setItemsAvailable(int itemsAvailable) {
        this.itemsAvailable = itemsAvailable;
    }

    public int getKeepAliveCount() {
        return keepAliveCount;
    }

    public void setKeepAliveCount(int keepAliveCount) {
        this.keepAliveCount = keepAliveCount;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;

/**
 *
 * @author mohan
 */
@ManagedBean
@RequestScoped
public class CustomerActions {

    private int itemid;
    private int orderid;
    private String sellerId;
    private int keepCount;

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    /**
     * Creates a new instance of CustomerActions
     */
    public String display() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return ("internal erroe exists");
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

            rs = stat.executeQuery("select * from item");

            if (rs.next()) {
                return (rs.getInt("itemId") + "\t" + rs.getString("Category") + "\t" + rs.getString("itemName") + "\t" + rs.getInt("itemAvailable") + "\t" + rs.getInt("keepAliveCount"));
            } else {
                return ("there are no items");
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

    public String buy(String custid) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return ("internal error exists");
        }
        String cid = custid;
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet seller = null;
        ResultSet keep = null;

        try {
            final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/mohantya5485";
            //connect to the database with user name and password
            conn = DriverManager.getConnection(DB_URL,
                    "mohantya5485", "1549512");
            stat = conn.createStatement();

            rs = stat.executeQuery("SELECT * FROM `order` WHERE customerId = '" + cid + "' and itemId='" + itemid + "'");
            //keep=stat.executeQuery("SELECT `keepAliveCount` FROM `item` WHERE itemId = '"+itemid+"' ");
            if (rs.next()) {
                return ("you already bought this item");
            } else {
                //getting the next order number
                rs2 = stat.executeQuery("select nextOrdernumber from nextordernumber");
                //   keep=stat.executeQuery("select keepAliveCount from item");
                if (rs2.next()) {
                    orderid = rs2.getInt(1);
                }
                System.out.println("hello after order retrieval");
                int updateorder = stat.executeUpdate("update nextordernumber set nextOrdernumber ='" + (rs2.getInt(1) + 1) + "'");

                seller = stat.executeQuery("select sellerId from item where itemId = '" + itemid + "'");
                System.out.println("hello after seller retrieval");
                if (seller.next()) {
                    sellerId = seller.getString(1);
                    keep = stat.executeQuery("SELECT `keepAliveCount` FROM `item` WHERE itemId = '" + itemid + "' ");
                    if (keep.next()) {
                        keepCount = keep.getInt(1);
                        System.out.print(keep.getInt(1));
                        int updatecount = stat.executeUpdate("UPDATE `item` SET `keepAliveCount`='" + (keepCount - 1) + "' WHERE itemId = '" + itemid + "'");
                    }
                        if (keepCount <= 0) {
                            int r = stat.executeUpdate("INSERT INTO `order`(`orderNum`, `sellerId`, `customerId`, `itemId`, `orderStatus`) VALUES ('" + orderid + "','" + sellerId + "','" + cid + "','" + itemid + "','confirmed')");
                            return ("order placed");
                        } else {
                            int r = stat.executeUpdate("INSERT INTO `order`(`orderNum`, `sellerId`, `customerId`, `itemId`, `orderStatus`) VALUES ('" + orderid + "','" + sellerId + "','" + cid + "','" + itemid + "','pending')");
                            return ("order is pending");
                        }
//                    } else {
//                        return "get out";
//                    }
                } else {
                    return "that item id is not present in the list";
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal Error! Please try again later.");

        } finally {
            try {
                rs.close();
                rs2.close();
                seller.close();
                stat.close();
                conn.close();
                keep.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

//    public CustomerActions() {
//    }
}

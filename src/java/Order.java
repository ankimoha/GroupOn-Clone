/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohan
 */


import javax.inject.Named;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
import javax.faces.context.FacesContext;

@ManagedBean
@Named(value = "login")
@SessionScoped

public class Order implements Serializable{
    private int ordernum;
    private int sellerid;
    private int custid;
    private int itemid;
    private String orderstatus;
    
    public String view(String loginId){
        String cid=loginId;
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e){
            return "Internal error exists";
        }
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/mohantya5485";
        
        Connection conn= null;
        Statement stat=null;
        ResultSet rs= null;
        
         try{
            conn=DriverManager.getConnection(DB_URL,"mohantya5485","1549512");
            stat=conn.createStatement();
            
            rs=stat.executeQuery("SELECT * FROM `order` WHERE customerId='"+cid+"'");
            
            if(rs.next()){
                return ("your order number of" +"\t" +rs.getInt(1)+"\t"+ "for item id" +"\t" +rs.getInt(4)+"\t"+"is having the status"+ "\t" +rs.getString(5));
                
            }
            else{
                return "you dont have any order on your name";
            }
         }
        catch(SQLException e){
            e.printStackTrace();
            return ("internalError");
        }
        finally
        {
            try
            {
                rs.close();
                stat.close();
                conn.close();
                 
            }
            catch (Exception e)
            {
                e.printStackTrace();    
            }
        }
        
        
        
    }

    public int getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(int ordernum) {
        this.ordernum = ordernum;
    }

    public int getSellerid() {
        return sellerid;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    public int getCustid() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }
    
    
}

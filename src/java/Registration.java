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
public class Registration {

    private String loginId;
    private String password;
    private boolean isSeller;
    private boolean isCustomer;
    
    private int seller;
    private int customer;

//    public Registration(String loginId, String password, boolean isSeller, boolean isCustomer) {
//        this.loginId = loginId;
//        this.password = password;
//        this.isSeller = isSeller;
//        this.isCustomer = isCustomer;
//    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsSeller() {
        return isSeller;
    }

    public void setIsSeller(boolean isSeller) {
        this.isSeller = isSeller;
    }

    public boolean isIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(boolean isCustomer) {
        this.isCustomer = isCustomer;
    }
    
    public String register(){
        
        if(isSeller==true){
            seller=1;
            customer=0;
        }
        if(isCustomer==true){
            seller=0;
            customer=1;
        }
        //load the driver
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            return ("Internal Error! Please try again later.");
        }
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/mohantya5485";
            
            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL, 
                    "mohantya5485", "1549512");   
            statement = connection.createStatement();
            //to search if the id already exists
            resultSet = statement.executeQuery("Select * from useraccount "
                    + "where loginid = '" + 
                    loginId + "' " );
            
            
            if(resultSet.next())
            {
                 return("Either you have an account already "
                        + "or your login ID is not available to register");
            }
            else
            {
                int r = statement.executeUpdate("insert into useraccount "
                        + "values ('" + loginId + "', '" + password + "', '" 
                    + seller + "','"+customer +"')");
                
                return ("Registration Successful! Please "
                         + "return to login your account.");
                
            }   
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return ("Internal Error! Please try again later.");
             
        }
        finally
        {
            try
            {
                resultSet.close();
                statement.close();
                connection.close();
                
            }
            catch (Exception e)
            {
                 
                e.printStackTrace();
            }
        }
    }
}

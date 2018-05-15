/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
import javax.faces.context.FacesContext;

/**
 *
 * @author mohan
 */
@ManagedBean
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {
    private String loginId;
    private String password;

    public Login(){
        
    }
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
    
    public String login(){
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
            rs=stat.executeQuery("Select * from useraccount where loginid='"+loginId+"' and password='"+password+"'");
            if(rs.next()){
                if(rs.getBoolean("isSeller")){
                    return "welcomeSeller";
                }
                else{
                    return "welcomeCustomer";
                }
                
            }
            else{
                return "loginNotOK";
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
    public String signOut(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";
    }
}

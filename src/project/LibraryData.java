/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author hakan.sanli
 */
class LibraryData {
    
    private static Connection con = null;
    private static Statement stmt = null;
    private static String url ="jdbc:mysql://localhost:3306/login_register?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    private static final String USER ="root";
    private static final String PASS ="root";
    
    static{
        try{
            con = DriverManager.getConnection(url, USER, PASS);
            System.out.println("Connected");
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public static int getSize()
    {
        int i=1;
        try
        {
        stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM LibraryTable WHERE ID = '"+i+"'");
         while (res.next()) 
            {
                res = stmt.executeQuery("SELECT * FROM LibraryTable WHERE ID = '"+i+"'");
                i++;
            }
        }
        
        catch (SQLException e)
        {
            System.out.println(e);
        }
        
        return i-2;
    }
    
    public static int getID(String name)
    {       
        try 
        {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM LibraryTable WHERE name = '" + name + "'");
            if (res.next()) 
            { 
                return res.getInt(1);
            } 
            else 
                return -1;
        } 
       
        catch (SQLException e) 
        {
            System.out.println(e);
            return -1;
        }
    }
    
    public static String getName(String key) 
    {
        //returns the song name from the song ID
        
        try 
        {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM LibraryTable WHERE ID = '" + key + "'");
            if (res.next()) 
            { 
                return res.getString(4);
            } 
            else 
                return null;
        } 
       
        catch (SQLException e) 
        {
            System.out.println(e);
            return null;
        }
    }
    
    public static String getType(String key)
    {
        // returns the song type from the song ID
        try{
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM LibraryTable WHERE ID = '"+ key+ "'");
            if(res.next())
            {
                return res.getString(2);
            }
            else return null;
        }
        catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }

    public static String getArtist(String key) 
    {
      //returns the artist name when given the song ID
        try
        {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM LibraryTable WHERE ID = '" + key + "'");
            if (res.next())
                return res.getString(3);
            else
                return null;
        }
        
        catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }
    
    public static String[] listSongNames()
    {
        String[] songName = new String[10];
        try{
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("Select distinct name from librarytable");
            int i=0;
            while(res.next())
            {
                String name = res.getString("name");
                songName[i] = name;
                i++;
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return songName;
    }
    
    public static String[] listType()
    {
        String[] songType = new String[10];
        try{
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select distinct type from librarytable");
            int i=0;
            while(rs.next())
            {
                String type = rs.getString("type");
                songType[i] = type;
                i++;
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return songType;
    }
    
    public static String[] listArtist()
    {
        String[] songArtist = new String[10];
        try{
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("Select distinct artist from librarytable");
            int i=0;
            while(res.next())
            {
                String artist = res.getString("artist");
                songArtist[i] =artist;
                i++;
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return songArtist;
    }
    
    public static String[] listSelectedType(String key)
    {
        String[] selectedType = new String[10];
        try{
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("Select type from librarytable where type ='" + key + "'");
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return selectedType;
    }
    
    public static void close() 
    {
        //closes the database
        try 
        {
            con.close();
        } 
        
        catch (SQLException e) 
        {
            System.out.println(e);
        }
    }
}

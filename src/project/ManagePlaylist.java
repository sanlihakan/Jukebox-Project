/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author hakan.sanli
 */
class ManagePlaylist {
    
    private static Connection con = null;
    private static Statement statement = null;
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
    
    public static String[] playAll()
    {
        String[] allSongs = new String[getSize()+1];
        try{
            int i=0;
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * from PlaylistTable");
            
            while(rs.next())
            {
                allSongs[i] = rs.getString(4);
                i++;
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return allSongs;
    }
    
    public static void addSong(String key)
    {
        //adds song to playlist sql table
        try
        {
            String test=getName(key);
            if (test == null)
            {   
                String query = "INSERT INTO playlisttable SELECT * FROM LibraryTable WHERE id='" +key+"'";
                con = DriverManager.getConnection(url,"root","root");
                statement.executeUpdate(query);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error, please select a "
                        + "track that is not already in the playlist");
            }
        }catch (HeadlessException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
    
    public static void clearList()
    {
        //clears the sql playlist table
        try
        {
            statement = con.createStatement();
            statement.executeUpdate("DELETE FROM PlaylistTable");
           
        }
        catch (SQLException e)
        {
            System.out.println("Error in clearing list, ManagePlaylist Class: "+e);
        }
    }
    
    public static void removeSong(String key)
    {
        //removes song from playlist sql table
        try
        {
            statement = con.createStatement();
            statement.executeUpdate("DELETE FROM PlaylistTable WHERE id='"+key+"'");
        }
        
        catch (SQLException e)
        {
            System.out.println("Error in Removing Song, ManagePlaylist class: "+e);
        }
       
    }
    
    public static int getSize()
    {
        //returns the amount of songs in the table, used to control loops
        int i=1;
        try
        {
        statement = con.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM PlaylistTable");
         while (res.next()) 
            {
                i++;
            }
        }
        
        catch (SQLException e)
        {
            System.out.println(e);
        }
        
        return i-1;
    }
    
    public static int getID(String name)
    {
        //given a song name this method returns the Song ID
        try 
        {
            statement = con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM PlaylistTable WHERE name = '" + name + "'");
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
        //given the ID, returns the song name
        try 
        {
            statement = con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM PlaylistTable WHERE ID = '" + key + "'");
            
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
            statement = con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM LibraryTable WHERE ID = '"+ key+ "'");
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
        //given the ID returns the artist
        try
        {
            statement = con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM PlaylistTable WHERE ID = '" + key + "'");
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
            statement = con.createStatement();
            ResultSet res = statement.executeQuery("Select distinct name from playlisttable");
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
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select distinct type from playlisttable");
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
            statement = con.createStatement();
            ResultSet res = statement.executeQuery("Select distinct artist from playlisttable");
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
    
    public static void close() 
    {
        //closes the database
        
        try 
        {
            con.close();
        }
        
        catch (SQLException e) 
        {
            System.out.println("Error closing database!!: "+e);
        }
    }
    
    
}

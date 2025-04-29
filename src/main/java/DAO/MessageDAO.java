package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class MessageDAO {
    
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), 
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return messages;
    }

    public List<Message> getMessagesFromUser(int posted_by) { 
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setInt(1, posted_by);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return messages;
    }

    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()) {
                int generated_Id = rs.getInt(1);
                return new Message(generated_Id, message.getPosted_by(), 
                message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Message deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message message = null;
    
        try {
            String sSql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement sPs = connection.prepareStatement(sSql);
            sPs.setInt(1, id);
            ResultSet rs = sPs.executeQuery();
    
            if (rs.next()) {
                message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
    
                String dSql = "DELETE FROM message WHERE message_id = ?;";
                PreparedStatement dPs = connection.prepareStatement(dSql);
                dPs.setInt(1, id);
                dPs.executeUpdate();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return message;
    }
    
    public Message updateMessage(int id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message_text);
            ps.setInt(2, id);
            int updatingMessage = ps.executeUpdate();
            
            if(updatingMessage == 1) {
                String getSql = "SELECT * FROM message WHERE message_id = ?;";
                PreparedStatement getPs = connection.prepareStatement(getSql);
                getPs.setInt(1, id);
                ResultSet rs = getPs.executeQuery();

                if(rs.next()) {
                    return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
    public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
}

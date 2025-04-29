package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class AccountDAO {

    // Implementation to get all accounts
    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM account;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"),
                rs.getString("username"), 
                rs.getString("password"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // Implementation to add an account
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username, password) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()) {
                int generated_Id = rs.getInt(1); 
                return new Account(generated_Id, account.getUsername(), account.getPassword());
            }
        } 
        catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    // Implementation to get an account by the user's information
    public Account getUserAccount(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return new Account(rs.getInt("account_id"),
                rs.getString("username"), rs.getString("password"));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
}

package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.*;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // Returns all the accounts
    public List<Account> getAccounts() {
        return accountDAO.getAllAccounts();
    }

    // Adds account if they meet requirements
    public Account addAccount(Account account) {
        if(account.getUsername() == null || 
        account.getUsername().isBlank() || 
        account.getPassword() == null ||
        account.getPassword().length() < 4 ||
        accountDAO.getUserAccount(account.getUsername()) != null) {         // requirement has been check added
            return null;
        }   
        return accountDAO.insertAccount(account);
    }
    
    // Verifies if the account information matches
    public Account verifyAccount(String username, String password) {
        List<Account> accounts = getAccounts();
        for(Account a : accounts) {
            if(a.getUsername().equals(username) && 
            a.getPassword().equals(password)) {
                return a;
            }
        }
        return null;
    }
}

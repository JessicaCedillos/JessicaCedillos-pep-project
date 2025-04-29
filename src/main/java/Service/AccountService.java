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

    public List<Account> getAccounts() {
        return accountDAO.getAllAccounts();
    }

    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }
    
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

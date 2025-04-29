package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/accounts", this::getAllAccountsHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::retrieveFromUserhandler);
        app.get("/messages/{message_id}", this::retrieveMessageByIdHandler);
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::createMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        return app;
    }

    // Handler that gets all accounts
    private void getAllAccountsHandler(Context context) {
        List<Account> accounts = accountService.getAccounts();
        context.json(accounts);
    }

    // Handler that gets all messages
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getMessages();
        context.json(messages);
    }

    // Handler that registers a new user account
    private void registerAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account createdAccount = accountService.addAccount(account);

        if(createdAccount != null) {
            context.json(createdAccount);
        }
        else {
            context.status(400);
        }
    }

    // Handler that validates the user's login information
    private void loginAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account accountCred = mapper.readValue(context.body(), Account.class);
        Account verifyAccount = accountService.verifyAccount(accountCred.getUsername(), accountCred.getPassword());
        
        if(verifyAccount != null) {
            context.json(verifyAccount);
        }
        else {
            context.status(401);
        }
    }

    // Handler that creates a message
    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message messageCreated = messageService.addMessage(message);

        if (messageCreated != null) {
            context.json(messageCreated);
        }
        else {
            context.status(400);
        }
    }
    
    // Handler that gets a message by its message id
    private void retrieveMessageByIdHandler(Context context) throws JsonProcessingException {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message != null) {
            context.json(message);
        } 
        else {
            context.status(200);
        }
    }

    // Handler that gets a message by a user's id
    private void retrieveFromUserhandler(Context context) {
        int userId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessageByUser(userId);
        context.json(messages);
    }

    // Handler that updates a message by its message id
    private void updateMessageHandler(Context context) throws JsonProcessingException {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message messages = mapper.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.updateTheMessage(messageId, messages.getMessage_text());

        if(updatedMessage != null) {
            context.json(updatedMessage);
        }
        else {
            context.status(400);
        }
    }

    // Handler that deletes a message by its message id
    private void deleteMessageHandler(Context context) throws JsonProcessingException {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message messageDeleted = messageService.removeMessage(messageId);

        if(messageDeleted != null) {
            context.json(messageDeleted);
        }
        else {
            context.status(200);
        }
    }
}
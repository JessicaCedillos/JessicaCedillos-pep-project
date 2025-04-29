package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.*;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Returns all messages
    public List<Message> getMessages() {
        return messageDAO.getAllMessages();
    }

    // Adds message if they meet requirements
    public Message addMessage(Message message) {
        if(message.message_text == null || 
        message.message_text.isBlank() || 
        message.getMessage_text().length() > 255 || 
        message.getPosted_by() != message.posted_by) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    // Removes message by its message id
    public Message removeMessage(int id) {
        return messageDAO.deleteMessage(id);
    }
    
    // Updates message if they meet requirements
    public Message updateTheMessage(int id, String message_text) {
        if(message_text.isBlank() || 
        message_text.length() > 255 || 
        message_text == null) {
            return null;
        }
        return messageDAO.updateMessage(id, message_text);
    }

    // Retrieves message by the user's id
    public List<Message> getMessageByUser(int id) {
        return messageDAO.getMessagesFromUser(id);
    }

    // Retrieves message by its message id
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }
}
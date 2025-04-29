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

    public List<Message> getMessages() {
        return messageDAO.getAllMessages();
    }

    public Message addMessage(Message message) {
        return messageDAO.insertMessage(message);
    }

    public Message removeMessage(int id) {
        return messageDAO.deleteMessage(id);
    }
    
    public Message updateTheMessage(int id, String message_text) {
        return messageDAO.updateMessage(id, message_text);
    }

    public List<Message> getMessageByUser(int id) {
        return messageDAO.getMessagesFromUser(id);
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }
}
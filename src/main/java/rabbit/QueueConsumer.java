package rabbit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.TransactionService;

@Component
public class QueueConsumer {

    @Autowired
    TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    public void receiveMessage(String message) {
        System.out.println(("Received (String) " + message));
        processMessage(message);
    }
    public void receiveMessage(byte[] message) {
        String strMessage = new String(message);
        System.out.println(("Received (No String) " + strMessage));
        processMessage(strMessage);
    }
    private void processMessage(String message) {
        try {
            Transaction transaction = objectMapper.readValue(message, Transaction.class);
            transactionService.addTransaction(transaction);
        } catch (JsonParseException e) {
            System.out.println(("Bad JSON in message: " + message));
        } catch (JsonMappingException e) {
            System.out.println(("cannot map JSON to Transaction: " + message));
        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }
    }
}
import app.MoneyPersistenceApplication;
import model.Person;
import model.Transaction;
import model.TransactionReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import service.TransactionService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneyPersistenceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransactionPersistenceResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private Transaction transaction = new Transaction();

    @Test
    public void test_getTransaction_successful() throws Exception {
        transaction.setDescription("mockDescription");
        when(transactionService.getTransaction(anyInt())).thenReturn(Optional.of(transaction));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/0").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("mockDescription"));
    }

    @Test
    public void test_getTransaction_404() throws Exception {
        when(transactionService.getTransaction(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_getAllTransactions_successful() throws Exception {
        transaction.setDescription("mockDescriptionInList");
        when(transactionService.getAllTransactions()).thenReturn(Arrays.asList(transaction));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("mockDescriptionInList"));
    }

    @Test
    public void test_getAllTransactions_404() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void test_addTransaction() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/transaction")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"amount\": 10,\"description\": \"descriere\", \"payee\": {\"cnp\": 12345678,\"iban\": \"123456789\",\"name\": \"ion\"\n},\"payer\": {\"cnp\": 23456789,\"iban\": \"234567890\",\"name\": \"vasile\"},\"transactionType\": \"IBAN_TO_IBAN\"}")
                .contentType(MediaType.APPLICATION_JSON);

       mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
    }

    @Test
    public void test_deleteTransaction() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/transaction/0")).andExpect(status().isNoContent());
    }

    @Test
    public void test_getReport_successful() throws Exception {
        Person person = new Person();
        person.setIban("mockIban");
        Transaction ibanToIbanTransaction = new Transaction();
        ibanToIbanTransaction.setDescription("mockIbanToIbanTransaction");
        Transaction walletToWalletTransaction = new Transaction();
        walletToWalletTransaction.setDescription("mockWalletToWalletTransaction");
        TransactionReport transactionReport = new TransactionReport();
        transactionReport.setPerson(person);
        transactionReport.setIbanToIban(Arrays.asList(ibanToIbanTransaction));
        transactionReport.setWalletToWallet(Arrays.asList(walletToWalletTransaction));

        when(transactionService.getReportForCnp(anyLong())).thenReturn(transactionReport);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/report/1234").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("mockIban"));
        assertTrue(result.getResponse().getContentAsString().contains("mockIbanToIbanTransaction"));
        assertTrue(result.getResponse().getContentAsString().contains("mockWalletToWalletTransaction"));
    }

    @Test
    public void test_getReport_404() throws Exception {
        when(transactionService.getReportForCnp(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/report/9999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

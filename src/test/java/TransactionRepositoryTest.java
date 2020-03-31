import app.MoneyPersistenceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneyPersistenceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TransactionRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_getTransaction() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/2").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("tranz2"));
        assertTrue(result.getResponse().getContentAsString().contains("1900205385534"));
        assertTrue(result.getResponse().getContentAsString().contains("1930709385573"));
    }

    @Test
    public void test_getAllTransactions() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("tranz2"));
        assertTrue(result.getResponse().getContentAsString().contains("tranz3"));
        assertTrue(result.getResponse().getContentAsString().contains("tranz4"));
    }

    @Test
    public void test_addTransaction() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/transaction")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"amount\": 30,\"description\": \"tranz5\", \"payee\": {\"cnp\": 1900205385534,\"iban\": \"43512583465\",\"name\": \"ion\"\n},\"payer\": {\"cnp\": 1920322385574,\"iban\": \"234567890\",\"name\": \"vasile\"},\"transactionType\": \"IBAN_TO_IBAN\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("tranz5"));
    }

    @Test
    public void test_deleteTransaction() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/transaction/1")).andExpect(status().isNoContent());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertFalse(result.getResponse().getContentAsString().contains("tranz1"));
    }

    @Test
    public void test_generateReport() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/report/1920322385574").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("1920322385574"));
        assertTrue(result.getResponse().getContentAsString().contains("52345334634"));
        assertTrue(result.getResponse().getContentAsString().contains("Narcis"));
        assertTrue(result.getResponse().getContentAsString().contains("1910103385576"));
        assertTrue(result.getResponse().getContentAsString().contains("1900205385534"));
        assertTrue(result.getResponse().getContentAsString().contains("IBAN_TO_WALLET"));
    }
}

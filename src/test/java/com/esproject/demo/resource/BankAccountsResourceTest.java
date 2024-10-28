package com.esproject.demo.resource;

import com.esproject.demo.service.BankAccountService;
import com.esproject.demo.util.BankAccountTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountsResource.class)
public class BankAccountsResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BankAccountService bankAccountsService;

    @Test
    void testGetBankAccounts() throws Exception {
        // mock response from service
        when(bankAccountsService.getBankAccountsByCriteria(any(), any(), any(), any())).thenReturn(BankAccountTestUtil.getBankAccountDetails());
        // request and test
        this.mockMvc.perform(get("/bank_accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value(157))
                .andExpect(jsonPath("$[0].balance").value(39868))
                .andExpect(jsonPath("$[1].accountNumber").value(215))
                .andExpect(jsonPath("$[1].balance").value(37427));
    }

    @Test
    void testGetBankAccountById() throws Exception {
        when(bankAccountsService.getAccountDetailsById(eq(157L))).thenReturn(BankAccountTestUtil.getBankAccountDetails().get(0));
        // request and test
        this.mockMvc.perform(get("/bank_accounts/157"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(157))
                .andExpect(jsonPath("$.balance").value(39868))
                .andExpect(jsonPath("$.firstname").value("Claudia"))
                .andExpect(jsonPath("$.lastname").value("Terry"))
                .andExpect(jsonPath("$.age").value(20));
    }
}

package com.esproject.demo.service;

import com.esproject.demo.interfaces.ESAppClient;
import com.esproject.demo.models.BankAccountDetails;
import com.esproject.demo.util.BankAccountTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class BankAccountServiceTest {
    @Mock
    private ESAppClient esAppClient;

    @InjectMocks
    private BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccountDetailsById() throws IOException {
        BankAccountDetails mockDetails = BankAccountTestUtil.getBankAccountDetails().get(0);
        when(esAppClient.getDocumentById("157", BankAccountDetails.class)).thenReturn(mockDetails);
        BankAccountDetails details = bankAccountService.getAccountDetailsById(157L);
        assertEquals(mockDetails, details);
    }

    @Test
    void testGetBankAccountsByCriteria() throws IOException {
        List<BankAccountDetails> mockDetails = BankAccountTestUtil.getBankAccountDetails();
        when(esAppClient.searchQuery(any(), any(), eq(BankAccountDetails.class))).thenReturn(mockDetails);
        List<BankAccountDetails> details = bankAccountService.getBankAccountsByCriteria("state", 20, 30, 1);
        assertEquals(mockDetails.size(), details.size());
        assertEquals(mockDetails, details);
    }
}

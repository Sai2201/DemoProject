package com.esproject.demo.resource;

import com.esproject.demo.models.BankAccountDetails;
import com.esproject.demo.service.BankAccountService;
import org.openapitools.api.BankAccountsApi;
import org.openapitools.model.BankAccountDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.esproject.demo.util.ConvertorUtil.convertToObject;
import static com.esproject.demo.util.ConvertorUtil.convertToObjectList;

@RestController
public class BankAccountsResource implements BankAccountsApi {
    private final BankAccountService bankAccountService;

    public BankAccountsResource(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public ResponseEntity<BankAccountDetail> getBankAccountById(Long accountId) {
        BankAccountDetails bankAccountDetail = bankAccountService.getAccountDetailsById(accountId);
        return ResponseEntity.ok(convertToObject(bankAccountDetail, BankAccountDetail.class));
    }

    @Override
    public ResponseEntity<List<BankAccountDetail>> getBankAccounts(String state, Integer startAge, Integer endAge, Integer limit) {
        List<BankAccountDetails> bankAccountDetails = bankAccountService.getBankAccountsByCriteria(state, startAge, endAge, limit);
        return ResponseEntity.ok(convertToObjectList(bankAccountDetails));
    }
}

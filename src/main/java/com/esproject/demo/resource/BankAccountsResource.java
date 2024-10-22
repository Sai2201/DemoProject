package com.esproject.demo.resource;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.esproject.demo.interfaces.ESAppClient;
import com.esproject.demo.models.BankAccountDetails;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.api.BankAccountsApi;
import org.openapitools.model.BankAccountDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.esproject.demo.util.ConvertorUtil.convertToObject;
import static com.esproject.demo.util.ConvertorUtil.convertToObjectList;
import static com.esproject.demo.util.ESUtil.*;

@RestController
public class BankAccountsResource implements BankAccountsApi {

    private final ESAppClient esAppClient;

    public BankAccountsResource(ESAppClient esAppClient) {
        this.esAppClient = esAppClient;
    }

    @Override
    public ResponseEntity<BankAccountDetail> getBankAccountById(Long accountId) {
        BankAccountDetails bankAccountDetail = esAppClient.getDocumentById(String.valueOf(accountId), BankAccountDetails.class);
        return ResponseEntity.ok(convertToObject(bankAccountDetail, BankAccountDetail.class));
    }

    @Override
    public ResponseEntity<List<BankAccountDetail>> getBankAccounts(String state, Integer startAge, Integer endAge, Integer limit) {
        // add parameters here
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
        if (StringUtils.isNotBlank(state)) {
            TermQuery stateQuery = buildTermQuery("state.keyword", state);
            boolQueryBuilder.must(q -> q.term(stateQuery));
        }

        // retrieve within range
        if (startAge != null || endAge != null) {
            RangeQuery rangeQuery = getRangeQuery(startAge, endAge);
            boolQueryBuilder.filter(q -> q.range(rangeQuery));
        }

        // build query
        Query query = new Query.Builder()
                .bool(boolQueryBuilder.build())
                .build();

        List<BankAccountDetails> bankAccountDetails = esAppClient.searchQuery(query, limit, BankAccountDetails.class);
        return ResponseEntity.ok(convertToObjectList(bankAccountDetails));
    }

}

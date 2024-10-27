package com.esproject.demo.service;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.esproject.demo.interfaces.ESAppClient;
import com.esproject.demo.models.BankAccountDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.esproject.demo.util.ESUtil.buildTermQuery;
import static com.esproject.demo.util.ESUtil.getRangeQuery;

@Service
public class BankAccountService {
    private final ESAppClient esAppClient;

    public BankAccountService(ESAppClient esAppClient) {
        this.esAppClient = esAppClient;
    }

    public BankAccountDetails getAccountDetailsById(Long accountId) {
        return esAppClient.getDocumentById(String.valueOf(accountId), BankAccountDetails.class);
    }

    // retrieve data from bank account client
    public List<BankAccountDetails> getBankAccountsByCriteria(String state, Integer startAge, Integer endAge, Integer limit) {
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

        return esAppClient.searchQuery(query, limit, BankAccountDetails.class);
    }
}

package com.esproject.demo.clients.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.esproject.demo.interfaces.ESAppClient;
import com.esproject.demo.models.BaseDataModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ESAppClientImpl implements ESAppClient {
    private static final int DEFAULT_LIMIT = 20;
    private final ElasticsearchClient esClient;

    public ESAppClientImpl(ElasticsearchClient elasticsearchClient) {
        this.esClient = elasticsearchClient;
    }

    @Override
    public <T extends BaseDataModel> T getDocumentById(String docId, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            String index = instance.getIndex();

            GetResponse<T> response = esClient.get(b -> b.index(index).id(docId), clazz);
            if (response.found()) {
                log.info("Document with :" + docId + " retrieved");
                return response.source();
            }
        } catch (Exception ex) {
            log.error("Error retrieving document from EsAppClient: ", ex);
        }
        log.warn("Document with :" + docId + " not found");
        return null;
    }

    @Override
    public <T extends BaseDataModel> List<T> searchQuery(Query query, Integer limit, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            String index = instance.getIndex();
            SearchRequest searchRequest = new SearchRequest.Builder()
                    .index(index)
                    .query(query)
                    .size(limit != null ? limit : DEFAULT_LIMIT)
                    .build();
            return searchQuery(searchRequest, clazz);
        } catch (Exception ex) {
            log.error("Error retrieving the searchQuery results");
        }
        return null;
    }

    public <T> List<T> searchQuery(Query query, Map<String, Aggregation> aggs, Class<T> clazz) {
        SearchRequest searchRequest = new SearchRequest.Builder()
                .query(query)
                .aggregations(aggs)
                .build();
        return searchQuery(searchRequest, clazz);
    }

    private <T> List<T> searchQuery(SearchRequest searchRequest, Class<T> clazz) {
        try {
            SearchResponse<T> searchResponse = esClient.search(searchRequest, clazz);
            if (isValidSearchResponse(searchResponse)) {
                log.info("Successfully executed searchQuery for request: " + searchRequest);
                return searchResponse.hits().hits().stream()
                        .map(Hit::source)
                        .collect(Collectors.toList());
            }
            log.info("Unable to execute search query");
        } catch (IOException ex) {
            log.error("Unable to search and find documents for the searchRequest: " + searchRequest);
        }
        return null;
    }

    private <T> boolean isValidSearchResponse(SearchResponse<T> searchResponse) {
        return searchResponse != null
                && searchResponse.hits() != null
                && CollectionUtils.isNotEmpty(searchResponse.hits().hits());
    }
}

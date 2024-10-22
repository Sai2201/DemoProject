package com.esproject.demo.interfaces;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.esproject.demo.models.BaseDataModel;

import java.util.List;

public interface ESAppClient {

    <T extends BaseDataModel> T getDocumentById(String docId, Class<T> clazz);

    /**
     * Searches and retrieves a list of objects implementing BaseDataModel
     */
    <T extends BaseDataModel> List<T> searchQuery(Query query, Integer limit, Class<T> clazz);
}

package com.esproject.demo.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NumberRangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;

public class ESUtil {
    public static RangeQuery getRangeQuery(Integer start, Integer end) {
        NumberRangeQuery.Builder numberQuery = new NumberRangeQuery.Builder().field("age");
        if (start != null) {
            numberQuery.gte(start.doubleValue());
        }
        if (end != null) {
            numberQuery.lte(end.doubleValue());
        }
        return buildNumberRangeQuery(numberQuery.build());
    }

    public static TermQuery buildTermQuery(String keyword, String value) {
        return new TermQuery.Builder()
                .field(keyword).value(value)
                .build();
    }

    private static RangeQuery buildNumberRangeQuery(NumberRangeQuery rangeQuery) {
        return new RangeQuery.Builder()
                .number(rangeQuery)
                .build();
    }

    private MatchQuery buildMatchQuery(String keyword, String value) {
        return new MatchQuery.Builder()
                .field(keyword).query(value)
                .build();
    }

}

package it.unibo.oop.myworkoutbuddy.controller.db.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import it.unibo.oop.myworkoutbuddy.controller.Builder;

public final class MongoDBUtils {

    public static <T> List<T> getAllDocuments(
            MongoCollection<Document> collection,
            Class<T> clazz) {
        final List<T> list = new ArrayList<>();
        collection
                .find()
                .forEach(toList(list, clazz));
        return list;
    }

    public static <T> List<T> getDocumentsByParams(
            MongoCollection<Document> collection,
            Map<String, Object> params,
            Class<T> clazz) {
        final List<T> list = new ArrayList<>();
        collection
                .find(Filters.and(toBsonFilters(params)))
                .forEach(toList(list, clazz));
        return list;
    }

    public static Iterable<Bson> toBsonFilters(Map<String, Object> params) {
        return params.entrySet().stream()
                .map(e -> Filters.eq(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private static <T> Block<? super Document> toList(List<T> list, Class<T> clazz) {
        return new Block<Document>() {
            public void apply(final Document document) {
                final Builder<T> builder = new Builder<>(clazz);
                document.forEach((f, v) -> {
                    if (!f.contains("_id")) {
                        builder.set(f, v);
                    }
                });
                try {
                    list.add(builder.build());
                } catch (final Exception e) {
                }
            }
        };
    }

    private MongoDBUtils() {
        throw new IllegalAccessError();
    }

}

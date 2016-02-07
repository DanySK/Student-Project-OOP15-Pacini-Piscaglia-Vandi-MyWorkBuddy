package it.unibo.oop.myworkoutbuddy.controller.db.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import it.unibo.oop.myworkoutbuddy.controller.Builder;

public final class MongoDBUtils {

    public static <T> List<T> getDocumentsByParams(
            MongoCollection<Document> collection,
            Map<String, Object> params,
            Class<? extends T> clazz) {
        final List<T> list = new ArrayList<>();
        collection
                .find(Objects.requireNonNull(params).size() > 0
                        ? Filters.and(MongoDBUtils.toBson(params))
                        : new Document())
                .forEach(addToList(list, clazz));
        return list;
    }

    public static Bson toBson(Map<String, Object> params) {
        return new BasicDBObject(params.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> {
                    final Object v = e.getValue();
                    return (v instanceof String) ? Pattern.compile(v.toString()) : v;
                })));
    }

    private static <T> Block<? super Document> addToList(List<T> list, Class<? extends T> clazz) {
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

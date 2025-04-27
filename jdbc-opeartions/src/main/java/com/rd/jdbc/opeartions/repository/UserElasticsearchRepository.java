package com.rd.jdbc.opeartions.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.rd.jdbc.opeartions.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserElasticsearchRepository {

    @Autowired
    private final ElasticsearchClient elasticsearchClient;

    public UserElasticsearchRepository(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }
    public Map<String, Object> searchUsers(String keyword, int page, int size) {
        try {
            int from = page * size;
            SearchResponse<User> response = elasticsearchClient.search(s -> s
                            .index("users")
                            .from(from)
                            .size(size)
                            .index("users")
                            .from(from)
                            .size(size)
                            .sort(so -> so
                                    .field(f -> f
                                            .field("email.keyword")
                                            .order(SortOrder.Asc)
                                    )
                            )
                            .query(q -> q
//                                    .bool(b -> b
//                                            .should(s1 -> s1.match(m -> m.field("name").query(keyword)))
//                                            .should(s1 -> s1.match(m -> m.field("about").query(keyword)))
//                                    )
                                    //mathes docs that contain keyword anywhere in name or about field
                                            .bool(b -> b
//                                                    .should(s1 -> s1.wildcard(w -> w
//                                                            .field("name")
//                                                            .value("*" + keyword + "*")
//                                                    ))
//                                                    .should(s2 -> s2.wildcard(w -> w
//                                                            .field("about")
//                                                            .value("*" + keyword + "*")
//                                                    ))
                                                        //IMPLEMENTING FUZZY SEARCH FOR MISSPELLED CORRECTIONS
                                                            .should(s1 -> s1.fuzzy(f -> f
                                                                    .field("name")
                                                                    .value(keyword)
                                                                    .fuzziness("AUTO")
                                                            ))
                                                            .should(s1 -> s1.fuzzy(f -> f
                                                                    .field("about")
                                                                    .value(keyword)
                                                                    .fuzziness("AUTO")
                                                            ))
                                            )


//                                    .multiMatch(m -> m
//                                            .fields("name", "about")
//                                            .query(keyword)
//                                    )
                            ),
                    User.class
            );

            long totalElements = response.hits().total().value();
            int totalPages = (int) Math.ceil((double) totalElements / size);

            Map<String, Object> result = new HashMap<>();
            result.put("currentPage", page);
            result.put("pageSize", size);
            result.put("totalElements", totalElements);
            result.put("totalPages", totalPages);
            result.put("data", response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList()));

            return result;

        } catch (IOException e) {
            throw new RuntimeException("Failed to search users", e);
        }
    }

    public Void saveUser(User user) {
        try {
            elasticsearchClient.index(IndexRequest.of(i -> i
                    .index("users")
                    .document(user)
            ));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user", e);
        }
        return null;
    }


//    public List<User> searchUsers(String keyword, int page, int size) {
//        try {
//            int from = page * size;
//            SearchResponse<User> response = elasticsearchClient.search(s -> s
//                            .index("users")
//                            .from(from)
//                            .size(size)
//                            .query(q -> q
//                                    .multiMatch(m -> m
//                                            .fields("name", "about")
//                                            .query(keyword)
//                                    )
//                            ),
//                    User.class
//            );
//
//            return response.hits().hits().stream()
//                    .map(Hit::source)
//                    .collect(Collectors.toList());
//
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to search users", e);
//        }
//    }
}

package com.rd.jdbc.opeartions.service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.rd.jdbc.opeartions.models.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserAdditionService {


        private final ElasticsearchClient elasticsearchClient;

        public UserAdditionService(ElasticsearchClient elasticsearchClient) {
            this.elasticsearchClient = elasticsearchClient;
        }

        public Void bulkInsertUsers(int numberOfUsers) {
            List<User> users = generateRandomUsers(numberOfUsers);

            List<BulkOperation> bulkOperations = new ArrayList<>();

            for (User user : users) {
                BulkOperation operation = BulkOperation.of(op -> op
                        .index(idx -> idx
                                .index("users")
                                .document(user)
                        )
                );
                bulkOperations.add(operation);
            }

            try {
                BulkRequest bulkRequest = new BulkRequest.Builder()
                        .operations(bulkOperations)
                        .build();

                BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest);

                if (bulkResponse.errors()) {
                    System.out.println("Bulk had errors:");
                    for (BulkResponseItem item : bulkResponse.items()) {
                        if (item.error() != null) {
                            System.out.println(item.error().reason());
                        }
                    }
                } else {
                    System.out.println("Bulk insert successful for " + numberOfUsers + " users.");
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to bulk insert users", e);
            }
            return null;
        }

        private List<User> generateRandomUsers(int count) {
            List<User> users = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                String randomName = "User_" + UUID.randomUUID().toString().substring(0, 8);
                String randomEmail = "user" + UUID.randomUUID().toString().substring(0, 5) + "@example.com";
                String randomAbout = "About " + UUID.randomUUID().toString().substring(0, 10);

                users.add(new User(randomName, randomEmail, randomAbout));
            }

            return users;
        }
    }


package com.visualpathit.searchservice.service;

import com.visualpathit.common.dto.UserDto;
import com.visualpathit.searchservice.config.ElasticsearchProperties;
import com.visualpathit.searchservice.config.UserServiceProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SearchService {

    private final ElasticsearchProperties elasticsearchProperties;
    private final org.springframework.web.client.RestClient userRestClient;

    public SearchService(ElasticsearchProperties elasticsearchProperties, UserServiceProperties userServiceProperties) {
        this.elasticsearchProperties = elasticsearchProperties;
        this.userRestClient = org.springframework.web.client.RestClient.builder()
                .baseUrl(userServiceProperties.baseUrl())
                .build();
    }

    public String indexAllUsers() throws IOException {
        UserDto[] users = userRestClient.get()
                .uri("/api/users")
                .retrieve()
                .body(UserDto[].class);

        if (users == null || users.length == 0) {
            return "No users to index";
        }

        try (RestHighLevelClient client = createClient()) {
            for (UserDto user : users) {
                Map<String, Object> doc = new HashMap<>();
                doc.put("name", user.getUsername());
                doc.put("DOB", user.getDateOfBirth());
                doc.put("fatherName", user.getFatherName());
                doc.put("motherName", user.getMotherName());
                doc.put("gender", user.getGender());
                doc.put("nationality", user.getNationality());
                doc.put("phoneNumber", user.getPhoneNumber());

                IndexRequest indexRequest = new IndexRequest("users")
                        .id(String.valueOf(user.getId()))
                        .source(doc);
                IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
                System.out.println(response.getResult());
            }
        }
        return "Indexed " + users.length + " users";
    }

    public Map<String, Object> getUser(String id) throws IOException {
        try (RestHighLevelClient client = createClient()) {
            GetRequest getRequest = new GetRequest("users", id);
            GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
            return response.getSource();
        }
    }

    public String updateUser(String id) throws IOException {
        try (RestHighLevelClient client = createClient()) {
            Map<String, Object> patch = Map.of("gender", "male");
            UpdateRequest updateRequest = new UpdateRequest("users", id).doc(patch);
            UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
            return response.status().name();
        }
    }

    public String deleteUser(String id) throws IOException {
        try (RestHighLevelClient client = createClient()) {
            DeleteRequest deleteRequest = new DeleteRequest("users", id);
            DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
            return response.getResult().name();
        }
    }

    private RestHighLevelClient createClient() {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(elasticsearchProperties.host(), elasticsearchProperties.port(), "http"))
        );
    }
}

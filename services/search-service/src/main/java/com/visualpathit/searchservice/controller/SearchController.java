package com.visualpathit.searchservice.controller;

import com.visualpathit.searchservice.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/index")
    public Map<String, String> indexAll() throws IOException {
        return Map.of("result", searchService.indexAllUsers());
    }

    @GetMapping("/users/{id}")
    public Map<String, Object> view(@PathVariable String id) throws IOException {
        return searchService.getUser(id);
    }

    @PutMapping("/users/{id}")
    public Map<String, String> update(@PathVariable String id) throws IOException {
        return Map.of("status", searchService.updateUser(id));
    }

    @DeleteMapping("/users/{id}")
    public Map<String, String> delete(@PathVariable String id) throws IOException {
        return Map.of("result", searchService.deleteUser(id));
    }
}

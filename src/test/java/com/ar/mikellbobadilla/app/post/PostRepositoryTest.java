package com.ar.mikellbobadilla.app.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository repository;

    @Test @DirtiesContext
    void testFindAllByTerm() {
        String term = "Tech";
        Set<String> tags = Set.of("Tech", "Programing");
        List<Post> posts = List.of(
                new Post(1, "First Post", "Content post", "Technology", tags, new Date(), new Date()),
                new Post(2, "Second Post", "Content post", "Data Base", tags, new Date(), new Date())
        );

        repository.saveAll(posts);

        var postsByTerm = repository.findAllByTerm(term);

        assertEquals(1, postsByTerm.size());
    }
}
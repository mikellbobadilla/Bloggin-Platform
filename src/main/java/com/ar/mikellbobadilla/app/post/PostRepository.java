package com.ar.mikellbobadilla.app.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = """
            SELECT * FROM posts
            WHERE title ILIKE %?1% OR content ILIKE %?1% OR category ILIKE %?1% ORDER BY id;
            """, nativeQuery = true)
    List<Post> findAllByTerm(String term);
}

package com.ar.mikellbobadilla.app.post;

import com.ar.mikellbobadilla.app.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class PostService {
    private static final String NOT_FOUND = "Post not found";
    private final PostRepository repository;

    public Post createPost(PostRequest request) {
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .category(request.category())
                .tags(request.tags())
                .createAt(new Date())
                .updateAt(new Date())
                .build();
        return repository.save(post);
    }

    public Post updatePost(Integer postId, PostRequest request) {
        Post post = repository.findById(postId)
                .orElseThrow(notFound());

        post.setTitle(request.title());
        post.setContent(request.content());
        post.setCategory(request.category());
        post.setTags(request.tags());
        post.setUpdateAt(new Date());
        return repository.save(post);
    }

    public void deletePost(Integer postId) {
        if (!repository.existsById(postId))
            throw new ResourceNotFoundException(NOT_FOUND);
        repository.deleteById(postId);
    }

    public Post getPost(Integer postId) {
        return repository.findById(postId)
                .orElseThrow(notFound());
    }

    public List<Post> getPosts(String term) {
        return repository.findAllByTerm(term);
    }

    private Supplier<ResourceNotFoundException> notFound() {
        return () -> new ResourceNotFoundException(NOT_FOUND);
    }
}

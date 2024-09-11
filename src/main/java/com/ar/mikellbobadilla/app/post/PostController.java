package com.ar.mikellbobadilla.app.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    /* Exceptions are handle on dir advice */

    @PostMapping
    @ResponseStatus(CREATED)
    Post postPost(@Valid @RequestBody PostRequest request) {
        return service.createPost(request);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(OK)
    Post putPost(@PathVariable Integer postId, @Valid @RequestBody PostRequest request) {
        return service.updatePost(postId, request);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(NO_CONTENT)
    void deletePost(@PathVariable Integer postId) {
        service.deletePost(postId);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(OK)
    Post getPost(@PathVariable Integer postId) {
        return service.getPost(postId);
    }

    @GetMapping
    @ResponseStatus(OK)
    List<Post> getPosts(@RequestParam(name = "term", defaultValue = "") String term) {
        return service.getPosts(term);
    }
}

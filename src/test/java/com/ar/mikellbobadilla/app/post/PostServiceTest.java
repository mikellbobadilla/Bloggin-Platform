package com.ar.mikellbobadilla.app.post;

import com.ar.mikellbobadilla.app.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository repository;
    @InjectMocks
    private PostService service;

    @Test
    void setup() {
        assertNotNull(repository);
        assertNotNull(service);
    }

    @Test
    void testCreatePost() {
        Date createAt = new GregorianCalendar(2024, Calendar.JULY, 4).getTime();
        Post newPost = Post.builder()
                .id(1)
                .title("First Post")
                .content("Content post")
                .category("Technology")
                .tags(Set.of("Tech", "Programing"))
                .createAt(createAt)
                .updateAt(createAt)
                .build();
        when(repository.save(any(Post.class))).thenReturn(newPost);

        PostRequest request = new PostRequest(
                "First Post",
                "Content post",
                "Technology",
                Set.of("Tech", "Programing")
        );

        var response = service.createPost(request);

        assertEquals(1, response.getId());
        assertEquals(request.title(), response.getTitle());
        assertEquals(createAt, response.getCreateAt());

        verify(repository, times(1)).save(any(Post.class));
    }

    @Test
    void testUpdatePost() {
        Integer postId = 1;
        Date createAt = new GregorianCalendar(2024, Calendar.JULY, 4).getTime();
        Date updateAt = new GregorianCalendar(2024, Calendar.JULY, 5).getTime();
        Post post = Post.builder()
                .id(postId)
                .title("First Post")
                .content("Content post")
                .category("Technology")
                .tags(Set.of("Tech", "Programing"))
                .createAt(createAt)
                .updateAt(createAt)
                .build();

        Post postUpdated = Post.builder()
                .id(postId)
                .title("First Post")
                .content("Content post")
                .category("Technology")
                .tags(Set.of("Tech", "Programing"))
                .createAt(createAt)
                .updateAt(updateAt)
                .build();

        when(repository.findById(postId)).thenReturn(Optional.of(post));
        when(repository.save(any(Post.class))).thenReturn(postUpdated);

        PostRequest request = new PostRequest(
                "First Post",
                "Content post",
                "Technology",
                Set.of("Tech", "Programing")
        );

        var response = service.updatePost(postId, request);

        assertEquals(postId, response.getId());
        assertEquals(updateAt, response.getUpdateAt());

        verify(repository, times(1)).findById(anyInt());
        verify(repository, times(1)).save(any(Post.class));
    }

    @Test
    void testUpdatePostNotFound() {
        Integer postId = 1;
        when(repository.findById(postId)).thenReturn(Optional.empty());

        PostRequest request = new PostRequest(
                "First Post",
                "Content post",
                "Technology",
                Set.of("Tech", "Programing")
        );

        assertThrows(ResourceNotFoundException.class, () -> service.updatePost(postId, request));

        verify(repository, times(1)).findById(anyInt());
        verify(repository, times(0)).save(any(Post.class));
    }

    @Test
    void testDeletePost() {
        Integer postId = 1;
        when(repository.existsById(postId)).thenReturn(true);
        doNothing().when(repository).deleteById(postId);

        service.deletePost(postId);

        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeletePostNotFound() {
        Integer postId = 1;
        when(repository.existsById(postId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deletePost(postId));
        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(0)).deleteById(anyInt());
    }

    @Test
    void testGetPost() {
        Integer postId = 1;
        Date createAt = new GregorianCalendar(2024, Calendar.JULY, 4).getTime();
        Post post = Post.builder()
                .id(1)
                .title("First Post")
                .content("Content post")
                .category("Technology")
                .tags(Set.of("Tech", "Programing"))
                .createAt(createAt)
                .updateAt(createAt)
                .build();
        when(repository.findById(postId)).thenReturn(Optional.of(post));

        var response = service.getPost(postId);

        assertEquals(postId, response.getId());
        assertEquals(post.getTitle(), response.getTitle());
        assertEquals(createAt, response.getCreateAt());

        verify(repository, times(1)).findById(postId);
    }

    @Test
    void testGetPostNotFound() {
        Integer postId = 1;
        when(repository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getPost(postId));

        verify(repository, times(1)).findById(postId);
    }
}
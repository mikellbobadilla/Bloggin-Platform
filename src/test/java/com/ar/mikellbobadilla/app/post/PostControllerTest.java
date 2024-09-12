package com.ar.mikellbobadilla.app.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private MockMvc mock;
    @Autowired
    private ObjectMapper mapper;

    @Test @DirtiesContext
    void testPostPost() throws Exception {
        PostRequest request = buildRequest();
        String requestAsJSON = mapper.writeValueAsString(request);

        ResultActions result = mock.perform(
            post("/posts").contentType(APPLICATION_JSON)
                    .content(requestAsJSON)
        );

        result
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(request.title()))
                .andExpect(jsonPath("$.category").value(request.category()));
    }

    @Test
    void testPostPost_BadRequest() throws Exception {
        PostRequest request = new PostRequest("", "", "Technology", Set.of("Tech", "Prog"));
        String requestAsJSON = mapper.writeValueAsString(request);

        ResultActions result = mock.perform(
                post("/posts").contentType(APPLICATION_JSON)
                        .content(requestAsJSON)
        );

        result
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test @DirtiesContext
    @Sql(statements = """
            INSERT INTO posts (id, title, content, category, tags, create_at, update_at)
            VALUES (1, 'First post', 'Content post', 'Technology', ARRAY['Tech', 'Prog'], now(), now());
            """)
    void testPutPost() throws Exception {
        Integer postId = 1;
        PostRequest request = new PostRequest(
                "Update Post",
                "Update content post",
                "Technology",
                Set.of("Tech", "Programing")
        );
        String requestAsJON = mapper.writeValueAsString(request);

        ResultActions result = mock.perform(
                put("/posts/{postId}", postId)
                        .contentType(APPLICATION_JSON)
                        .content(requestAsJON)
        );

        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(request.title()))
                .andExpect(jsonPath("$.content").value(request.content()));
    }

    @Test @DirtiesContext
    @Sql(statements = """
            INSERT INTO posts (id, title, content, category, tags, create_at, update_at)
            VALUES (1, 'First post', 'Content post', 'Technology', ARRAY['Tech', 'Prog'], now(), now());
            """)
    void testUpdatePost_BadRequest() throws Exception {
        Integer postId = 1;
        PostRequest request = new PostRequest(
                "",
                "",
                "Technology",
                Set.of("Tech", "Programing")
        );
        String requestAsJON = mapper.writeValueAsString(request);

        ResultActions result = mock.perform(
                put("/posts/{postId}", postId)
                        .contentType(APPLICATION_JSON)
                        .content(requestAsJON)
        );

        result
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test @DirtiesContext
    void testPutPost_NotFound() throws Exception {
        Integer postId = 1;
        PostRequest request = new PostRequest(
                "Update Post",
                "Update content post",
                "Technology",
                Set.of("Tech", "Programing")
        );
        String requestAsJON = mapper.writeValueAsString(request);
        ResultActions result = mock.perform(
                put("/posts/{postId}", postId)
                        .contentType(APPLICATION_JSON)
                        .content(requestAsJON)
        );

        result
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test @DirtiesContext
    @Sql(statements = """
            INSERT INTO posts (id, title, content, category, tags, create_at, update_at)
            VALUES (1, 'First post', 'Content post', 'Technology', ARRAY['Tech', 'Prog'], now(), now());
            """)
    void testDeletePost() throws Exception {
        Integer postId = 1;
        ResultActions result = mock.perform(delete("/posts/{postId}", postId));

        result.andExpect(status().isNoContent());
    }

    @Test @DirtiesContext
    void testDeletePost_NotFound() throws Exception {
        Integer postId = 1;
        ResultActions result = mock.perform(delete("/posts/{postId}", postId));

        result
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test @DirtiesContext
    @Sql(statements = """
            INSERT INTO posts (id, title, content, category, tags, create_at, update_at)
            VALUES (1, 'First post', 'Content post', 'Technology', ARRAY['Tech', 'Prog'], now(), now());
            """)
    void testGetPost() throws Exception {
        Integer postId = 1;
        ResultActions result = mock.perform(get("/posts/{postId}", postId));

        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postId));
    }

    @Test @DirtiesContext
    void testGetPost_NotFound() throws Exception {
        Integer postId = 1;
        ResultActions result = mock.perform(get("/posts/{postId}", postId));

        result
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test @DirtiesContext
    @Sql(statements = {
            """
            INSERT INTO posts (id, title, content, category, tags, create_at, update_at)
            VALUES (1, 'First post', 'Content post', 'Technology', ARRAY['Tech', 'Prog'], now(), now());
            """,
            """
            INSERT INTO posts (id, title, content, category, tags, create_at, update_at)
            VALUES (2, 'Second post', 'Second content post', 'Technology', ARRAY['Tech', 'Prog'], now(), now());
            """
    })
    void testGetPost_TermParam() throws Exception {
        String term = "first";
        ResultActions result = mock.perform(
                get("/posts")
                        .param("term", term)
        );

        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }



    private PostRequest buildRequest() {
        return new PostRequest(
                "First Post",
                "Content post",
                "Technology",
                Set.of("Tech", "Programing")
        );
    }
}
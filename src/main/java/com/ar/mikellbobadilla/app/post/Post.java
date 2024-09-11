package com.ar.mikellbobadilla.app.post;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "posts")
@Entity

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
@ToString @EqualsAndHashCode
public class Post {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(length = 100, nullable = false)
    private String category;
    private Set<String> tags;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "update_at")
    private Date updateAt;
}

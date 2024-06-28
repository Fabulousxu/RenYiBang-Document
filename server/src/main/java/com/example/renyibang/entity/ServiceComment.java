package com.example.renyibang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;

@Entity
@Table(name = "service_comment")
@Getter
@Setter
@NoArgsConstructor
public class ServiceComment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceCommentId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "commenter_id")
    private User commenter;

    private String content;
    private byte rating = 50;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createdAt;

    @ManyToMany
    @JoinTable(
            name = "service_comment_like",
            joinColumns = @JoinColumn(name = "service_comment_id"),
            inverseJoinColumns = @JoinColumn(name = "liker_id"))
    private Set<User> likers;
}

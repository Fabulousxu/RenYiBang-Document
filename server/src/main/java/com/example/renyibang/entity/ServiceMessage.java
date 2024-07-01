package com.example.renyibang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;

@Entity
@Table(name = "service_message")
@Getter
@Setter
@NoArgsConstructor
public class ServiceMessage
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceMessageId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "messager_id")
    private User messager;

    private String content; // 留言内容

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createdAt;

    @ManyToMany
    @JoinTable(
            name = "service_message_like",
            joinColumns = @JoinColumn(name = "service_message_id"),
            inverseJoinColumns = @JoinColumn(name = "liker_id"))
    private Set<User> likers;
}

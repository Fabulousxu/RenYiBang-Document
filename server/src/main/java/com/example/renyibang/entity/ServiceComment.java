package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;

    @Column(name = "liked_number")
    private long likedNumber = 0;

    @ManyToMany
    @JoinTable(
            name = "service_comment_like",
            joinColumns = @JoinColumn(name = "service_comment_id"),
            inverseJoinColumns = @JoinColumn(name = "liker_id"))
    private Set<User> likers;

    public JSONObject toJSON()
    {
        JSONObject result = new JSONObject();
        result.put("serviceCommentId", serviceCommentId);
        result.put("serviceId", service.getServiceId());
        result.put("content", content);
        result.put("rating", rating);
        result.put("createdAt", DateTimeUtil.formatDateTime(createdAt));
        result.put("likedNumber", likedNumber);

        result.put("commenter", commenter.toJSON());

        return result;
    }

    public boolean isLikedByUser(User liker)
    {
        return likers.stream().anyMatch(user -> user.equals(liker));
    }

    public void addLiker(User liker) { likers.add(liker); }

    public void removeLiker(User unliker) { likers.remove(unliker);}
}

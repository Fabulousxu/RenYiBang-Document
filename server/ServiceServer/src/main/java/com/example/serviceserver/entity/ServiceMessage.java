package com.example.serviceserver.entity;

import com.alibaba.fastjson2.JSONObject;
import com.example.serviceserver.entity.User;
import com.example.serviceserver.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "service_message_like",
            joinColumns = @JoinColumn(name = "service_message_id"),
            inverseJoinColumns = @JoinColumn(name = "liker_id"))
    private Set<User> likers;

    @Column(name = "liked_number")
    private long likedNumber = 0;

    public JSONObject toJSON()
    {
        JSONObject result = new JSONObject();
        result.put("serviceMessageId", serviceMessageId);
        result.put("serviceId", service.getServiceId());
        result.put("content", content);
        result.put("createdAt", DateTimeUtil.formatDateTime(createdAt));
        result.put("likedNumber", likedNumber);

        result.put("messager", messager.toJSON());

        return result;
    }

    public boolean isLikedByUser(User liker)
    {
        return likers.stream().anyMatch(user -> user.equals(liker));
    }

    public void addLiker(User liker) { likers.add(liker); }

    public void removeLiker(User unliker) { likers.remove(unliker); }

    public boolean isMessager(User messager) { return messager.equals(this.messager);}
}

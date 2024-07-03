package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.util.DateTimeUtil;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
public class Service
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "title")
    private String title; // 服务标题
    @Column(name = "images")
    private String images; // 服务图片
    @Column(name = "description")
    private String description; // 服务描述
    @Column(name = "price")
    private long price = 0; // 服务价格(存储100倍价格)
    @Column(name = "max_access")
    private int maxAccess = 0; // 服务最大接取数
    @Column(name = "rating")
    private byte rating = 50; // 服务评分(存储10倍评分,范围0~100)

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "service")
    @OrderBy("createdAt DESC")
    private List<ServiceComment> comments;

    @OneToMany(mappedBy = "service")
    @OrderBy("createdAt DESC")
    private List<ServiceMessage> messages;

    @OneToMany(mappedBy = "service")
    @OrderBy("createdAt DESC")
    private List<ServiceAccess> accesses;

    @Column(name = "collected")
    private long collectedNumber = 0;

    public static List<String> splitImages(String images) {
        // 使用空格分割字符串，并将结果转换为List<String>
        return Arrays.asList(images.split("\\s+"));
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("serviceId", serviceId);
        result.put("title", title);
        List<String> imageList = splitImages(images);
        result.put("images", imageList);
        result.put("cover", imageList.getFirst());
        result.put("description", description);
        result.put("price", price);
        result.put("maxAccess", maxAccess);
        result.put("rating", rating);
        result.put("createdAt", DateTimeUtil.formatDateTime(createdAt));
        result.put("collectedNumber", collectedNumber);

        result.put("owner", owner.toJSON());

        return result;
    }

    public JSONObject toJSON(User user)
    {
        JSONObject result = new JSONObject();
        result.put("serviceId", serviceId);
        result.put("title", title);
        List<String> imageList = splitImages(images);
        result.put("images", imageList);
        result.put("cover", imageList.getFirst());
        result.put("description", description);
        result.put("price", price);
        result.put("maxAccess", maxAccess);
        result.put("rating", rating);
        result.put("createdAt", DateTimeUtil.formatDateTime(createdAt));
        result.put("collectedNumber", collectedNumber);
        result.put("collected", user.getCollectedServices().stream().anyMatch(serviceCollect -> (serviceCollect.getServiceCollectId() == this.serviceId)));

        result.put("owner", owner.toJSON());

        return result;
    }

    public boolean accessNotFull()
    {
        return accesses.size() < maxAccess;
    }

    public boolean isCommented(User commenter) { return comments.stream().anyMatch(comment -> comment.getCommenter().equals(commenter));}
}

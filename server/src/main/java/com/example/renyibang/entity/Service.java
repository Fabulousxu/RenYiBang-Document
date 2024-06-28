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

    private String title;
    private String images;
    private String description;
    private long price = 0;
    private int maxAccess = 0;
    private byte rating = 50;

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

    public static List<String> splitImages(String images) {
        // 使用空格分割字符串，并将结果转换为List<String>
        return Arrays.asList(images.split("\\s+"));
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("taskId", serviceId);
        result.put("title", title);
        List<String> imageList = splitImages(images);
        result.put("images", imageList);
        result.put("cover", imageList.getFirst());
        result.put("description", description);
        result.put("price", price);
        result.put("maxAccess", maxAccess);
        result.put("rating", rating);
        result.put("createdAt", DateTimeUtil.formatDateTime(createdAt));

        result.put("owner", owner.toJSON());

        return result;
    }
}

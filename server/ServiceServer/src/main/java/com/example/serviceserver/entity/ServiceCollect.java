package com.example.serviceserver.entity;

import com.example.serviceserver.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_collect")
@Getter
@Setter
@NoArgsConstructor
public class ServiceCollect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceCollectId; // 任务收藏id

    @ManyToOne
    @JoinColumn(name = "collector_id")
    private User collector; // 收藏者

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service; // 收藏服务

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt; // 收藏时间
}

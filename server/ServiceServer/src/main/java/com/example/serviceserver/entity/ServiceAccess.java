package com.example.serviceserver.entity;

import com.example.serviceserver.entity.Service;
import com.example.serviceserver.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_access")
@Getter
@Setter
@NoArgsConstructor
public class ServiceAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceAccessId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "accessor_id")
    private User accessor;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "valid")
    private boolean valid = true; // 候选购买是否有效
}

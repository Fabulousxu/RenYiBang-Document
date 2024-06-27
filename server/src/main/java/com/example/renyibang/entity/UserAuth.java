package com.example.renyibang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_auth")
@Getter
@Setter
@NoArgsConstructor
public class UserAuth {
  @Id private long userId;
  private String password;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
}

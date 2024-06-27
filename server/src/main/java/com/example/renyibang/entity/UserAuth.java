package com.example.renyibang.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}

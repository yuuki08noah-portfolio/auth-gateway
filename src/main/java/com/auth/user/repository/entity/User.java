package com.auth.user.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

class UserId implements Serializable {
  private String id;
  private String email;
}

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserId.class)
@Table(name = "\"user\"")
public class User {
  @Id
  @Column(unique = true, nullable = false)
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Id
  @Column(unique = true, nullable = false)
  private String email;

  @Column
  private String phone;

  @Column(nullable = false)
  private Role role = Role.USER;

  @Column
  private String bio = "";

  @Column
  private String profile = "";

  @Column
  private String lang = "kr";

  @Column
  private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

  @Column
  private Boolean emailRequest = false;

  @Column
  private Boolean phoneRequest = false;

  @Column
  private Long followers = 0L;

  @Column
  private Long following = 0L;

  @Override
  public String toString() {
    return "User{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", role=" + role +
            ", bio='" + bio + '\'' +
            ", profile='" + profile + '\'' +
            ", lang='" + lang + '\'' +
            ", createdAt=" + createdAt +
            ", emailRequest=" + emailRequest +
            ", phoneRequest=" + phoneRequest +
            ", followers=" + followers +
            ", following=" + following +
            '}';
  }
}
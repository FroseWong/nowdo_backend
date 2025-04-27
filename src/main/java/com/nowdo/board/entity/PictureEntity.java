package com.nowdo.board.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "picture")
public class PictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY) // 多個 Picture 對應一個 User（多對一）
    @JoinColumn(name = "user_id") // 外鍵欄位 user_id
    private UserEntity user;

    @Column(name = "remark")
    private String remark;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public PictureEntity() {

    }

    public PictureEntity(String imageUrl, String remark) {
        this.imageUrl = imageUrl;
        this.remark = remark;
    }



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public String getRemark() {
        return remark;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

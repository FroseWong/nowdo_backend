package com.nowdo.board.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "board_title", nullable = false)
    private String boardTitle;

    @ManyToOne(fetch = FetchType.LAZY) // 多個 Board 對應一個 User（多對一）
    @JoinColumn(name = "user_id", nullable = false) // 外鍵欄位 user_id
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id", nullable = false)
    private PictureEntity picture;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public BoardEntity() {

    }

    public BoardEntity(String boardTitle, String imageUrl, UserEntity user) {
        this.boardTitle = boardTitle;
        this.user = user;
        this.picture = picture;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public UserEntity getUser() {
        return user;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", boardTitle='" + boardTitle + '\'' +
                ", userId=" + (user != null ? user.getId() : null) +
                ", pictureId=" + (picture != null ? picture.getId() : null) +
                '}';
    }
}

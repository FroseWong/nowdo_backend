package com.nowdo.board.dto;

import com.nowdo.board.entity.BoardEntity;

import java.time.LocalDateTime;

public class BoardDTO {
    private int id;
    private String boardTitle;
    private int pictureId;
    private String imageUrl;
    private LocalDateTime createdAt;

    public BoardDTO() {
    }

    public BoardDTO(BoardEntity entity) {
        this.id = entity.getId();
        this.boardTitle = entity.getBoardTitle();
        this.pictureId = entity.getPicture().getId();
        this.imageUrl = entity.getPicture() != null ? entity.getPicture().getImageUrl() : null;
        this.createdAt = entity.getCreatedAt();
    }

//    public BoardDTO(BoardEntity entity) {
//        this.id = entity.getId();
//        this.boardTitle = entity.getBoardTitle();
//        this.imageUrl = entity.getPicture() != null ? entity.getPicture().getImageUrl() : null;
//    }

    // 加上 Getter
    public int getId() {
        return id;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public int getPictureId() {
        return pictureId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}


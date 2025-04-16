package com.nowdo.board.dto;

import java.util.List;

public class BoardDetailDTO {
    private int id;
    private String boardTitle;
    private int pictureId;
    private String imageUrl;
    private List<ListDTO> lists;

    public BoardDetailDTO() {
    }

    public BoardDetailDTO(int id, String boardTitle, int pictureId, String imageUrl, List<ListDTO> lists) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.pictureId = pictureId;
        this.imageUrl = imageUrl;
        this.lists = lists;
    }

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

    public List<ListDTO> getLists() {
        return lists;
    }
}

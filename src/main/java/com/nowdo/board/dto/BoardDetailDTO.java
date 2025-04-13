package com.nowdo.board.dto;

import java.util.List;

public class BoardDetailDTO {
    private int id;
    private String boardTitle;
    private String imageUrl;
    private List<ListDTO> lists;

    public BoardDetailDTO(int id, String boardTitle, String imageUrl, List<ListDTO> lists) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.imageUrl = imageUrl;
        this.lists = lists;
    }

    public int getId() {
        return id;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<ListDTO> getLists() {
        return lists;
    }
}

package com.nowdo.board.dto;

public class BoardCreateRequestDTO {
    private String boardTitle;
    private int pictureId;

    public BoardCreateRequestDTO() {
    }

    public BoardCreateRequestDTO(String boardTitle, int pictureId) {
        this.boardTitle = boardTitle;
        this.pictureId = pictureId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public int getPictureId() {
        return pictureId;
    }
}

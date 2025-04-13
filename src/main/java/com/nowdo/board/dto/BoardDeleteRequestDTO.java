package com.nowdo.board.dto;

public class BoardDeleteRequestDTO {
    private int boardId;

    public BoardDeleteRequestDTO(int boardId) {
        this.boardId = boardId;
    }

    public int getBoardId() {
        return boardId;
    }
}

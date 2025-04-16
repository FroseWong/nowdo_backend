package com.nowdo.board.dto;

public class ListCreateRequestDTO {
    private String listTitle;
    private int boardId;

    public ListCreateRequestDTO(){
    }

    public ListCreateRequestDTO(String listTitle, int boardId) {
        this.listTitle = listTitle;
        this.boardId = boardId;
    }

    public String getListTitle() {
        return listTitle;
    }

    public int getBoardId() {
        return boardId;
    }
}

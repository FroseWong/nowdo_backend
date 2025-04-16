package com.nowdo.board.dto;

public class ListDeleteRequestDTO {
    private int listId;

    public ListDeleteRequestDTO(){
    }

    public ListDeleteRequestDTO(int listId) {
        this.listId = listId;
    }

    public int getListId() {
        return listId;
    }
}

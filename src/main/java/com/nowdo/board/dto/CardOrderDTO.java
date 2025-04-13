package com.nowdo.board.dto;

public class CardOrderDTO {
    private int id;
    private int listId;
    private int order;

    public CardOrderDTO(int id, int listId, int order) {
        this.id = id;
        this.listId = listId;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public int getOrder() {
        return order;
    }
}

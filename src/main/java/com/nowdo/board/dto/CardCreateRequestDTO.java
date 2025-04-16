package com.nowdo.board.dto;

public class CardCreateRequestDTO {
    private String cardTitle;
    private int listId;

    public CardCreateRequestDTO(){
    }

    public CardCreateRequestDTO(String cardTitle, int listId) {
        this.cardTitle = cardTitle;
        this.listId = listId;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public int getListId() {
        return listId;
    }
}

package com.nowdo.board.dto;

public class CardUpdateRequestDTO {
    private int id;
    private String cardTitle;
    private String cardDesc;
    private Boolean isCompleted;

    public CardUpdateRequestDTO(){
    }

    public CardUpdateRequestDTO(int id, String cardTitle, String cardDesc, Boolean isCompleted) {
        this.id = id;
        this.cardTitle = cardTitle;
        this.cardDesc = cardDesc;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public String getCardDesc() {
        return cardDesc;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }
}

package com.nowdo.board.dto;

public class CardDeleteRequestDTO {
    private int cardId;

    public CardDeleteRequestDTO(){
    }

    public CardDeleteRequestDTO(int cardId) {
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }
}

package com.nowdo.board.dto;

import java.util.List;

public class ListDTO {
    private int id;
    private String listTitle;
    private List<CardDTO> cards;

    public ListDTO(int id, String listTitle, List<CardDTO> cards) {
        this.id = id;
        this.listTitle = listTitle;
        this.cards = cards;
    }

    public int getId() {
        return id;
    }

    public String getListTitle() {
        return listTitle;
    }

    public List<CardDTO> getCards() {
        return cards;
    }
}

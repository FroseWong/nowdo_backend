package com.nowdo.board.dto;

public class ListOrderDTO {
    private int id;
    private int order;

    public ListOrderDTO(){
    }

    public ListOrderDTO(int id, int order) {
        this.id = id;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }
}

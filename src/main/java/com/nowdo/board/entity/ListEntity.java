package com.nowdo.board.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "list")
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "list_title")
    private String listTitle;

    @ManyToOne(fetch = FetchType.LAZY) // 多個 List 對應一個 Board（多對一）
    @JoinColumn(name = "board_id", nullable = false) // 外鍵欄位 board_id
    private BoardEntity board;

    @Column(name="list_order")
    private int listOrder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public ListEntity() {

    }

    public ListEntity(String listTitle, BoardEntity board, int listOrder) {
        this.listTitle = listTitle;
        this.board = board;
        this.listOrder = listOrder;
    }

    public int getId() {
        return id;
    }

    public String getListTitle() {
        return listTitle;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public int getListOrder() {
        return listOrder;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    public void setListOrder(int listOrder) {
        this.listOrder = listOrder;
    }
}

package com.nowdo.board.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "card_title")
    private String cardTitle;

    @Column(name = "card_desc")
    private String cardDesc;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    @ManyToOne(fetch = FetchType.LAZY) // 多個 Card 對應一個 List（多對一）
    @JoinColumn(name = "list_id", nullable = false) // 外鍵欄位 list_id
    private ListEntity list;

    @Column(name = "card_order")
    private int cardOrder;


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

    public CardEntity() {

    }

    public CardEntity(String cardTitle, String cardDesc, Boolean isCompleted, ListEntity list, int cardOrder) {
        this.cardTitle = cardTitle;
        this.cardDesc = cardDesc;
        this.isCompleted = isCompleted;
        this.list = list;
        this.cardOrder = cardOrder;
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

    public ListEntity getList() {
        return list;
    }

    public int getCardOrder() {
        return cardOrder;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    public void setList(ListEntity list) {
        this.list = list;
    }

    public void setCardOrder(int cardOrder) {
        this.cardOrder = cardOrder;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }
}

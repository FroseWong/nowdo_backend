package com.nowdo.board.dao;

import com.nowdo.board.entity.CardEntity;

import java.util.List;

public interface CardDAO {
    CardEntity findById(int id);
    List<CardEntity> getCardByListId(int listId);
    void createNewCard(String cardTitle, int listId);
    int getNextCardOrder(int listId);
//    void updateCardById(CardEntity theCard);
    void deleteCardById(int theId);
}

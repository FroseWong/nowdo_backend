package com.nowdo.board.service;

import com.nowdo.board.dto.CardOrderDTO;
import com.nowdo.board.dto.CardUpdateRequestDTO;

import java.util.List;

public interface CardService {
    void createNewCard(String authHeader, String cardTitle, int listId);
    void updateCard(String authHeader, CardUpdateRequestDTO request);
    void updateCardOrderByToken(String authHeader, List<CardOrderDTO> cardOrderList);
    void deleteCardById(String authHeader, int cardId);
}

package com.nowdo.board.service;

import com.nowdo.board.dto.ListOrderDTO;

import java.util.List;

public interface ListService {
    void updateListOrderByToken(String authHeader, List<ListOrderDTO> orderList);
    void createNewList(String authHeader, String listTitle, int boardId);
    void deleteListById(String authHeader, int listId);
}

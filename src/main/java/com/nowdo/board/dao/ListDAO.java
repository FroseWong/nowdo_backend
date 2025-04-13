package com.nowdo.board.dao;

import com.nowdo.board.entity.ListEntity;

import java.util.List;

public interface ListDAO {
    ListEntity findById(int theId);
    List<ListEntity> getListByBoardId(int boardId);
    int getNextListOrder(int boardId);
    void createNewList(String listTitle, int boardId);
    void deleteListById(int theId);
}

package com.nowdo.board.dao;

import com.nowdo.board.entity.BoardEntity;

import java.util.List;

public interface BoardDAO {
    BoardEntity findById(int theId);
    List<BoardEntity> getBoardListById(int theId);
    int createNewBoard(String boardTitle, int userId, int pictureId);
    void updatePictureToDefaultByPictureId(int pictureId);
    void deleteBoardById(int theId);
}

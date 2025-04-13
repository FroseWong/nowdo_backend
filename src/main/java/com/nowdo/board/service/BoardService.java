package com.nowdo.board.service;

import com.nowdo.board.dto.BoardDetailDTO;
import com.nowdo.board.entity.BoardEntity;

import java.util.List;

public interface BoardService {
//    List<BoardEntity> getBoardListById(int theId);
    List<BoardEntity> getBoardListByToken(String authHeader);
    int createBoardByToken(String authHeader, String boardTitle, int pictureId);
    BoardDetailDTO getFullBoardByToken(String authHeader, int boardId);
    void deleteBoardById(String authHeader, int boardId);
}

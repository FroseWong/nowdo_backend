package com.nowdo.board.service;

import com.nowdo.board.dao.*;
import com.nowdo.board.dto.BoardDetailDTO;
import com.nowdo.board.dto.CardDTO;
import com.nowdo.board.dto.ListDTO;
import com.nowdo.board.entity.BoardEntity;
import com.nowdo.board.entity.CardEntity;
import com.nowdo.board.entity.ListEntity;
import com.nowdo.board.entity.UserEntity;
import com.nowdo.board.security.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardDAO boardDAO;
    private UserDAO userDAO;
    private JwtUtil jwtUtil;
    private ListDAO listDAO;
    private CardDAO cardDAO;
    private PictureDAO pictureDAO;
    private AuthService authService;
    private EntityManager entityManager;

    @Autowired
    public BoardServiceImpl(BoardDAO theBoardDAO, UserDAO theUserDAO, ListDAO theListDAO, CardDAO theCardDAO, PictureDAO thePictureDAO, JwtUtil theJwtUtil, AuthService theAuthService, EntityManager theEntityManager) {
        boardDAO = theBoardDAO;
        userDAO = theUserDAO;
        listDAO = theListDAO;
        cardDAO = theCardDAO;
        pictureDAO = thePictureDAO;
        jwtUtil = theJwtUtil;
        authService = theAuthService;
        entityManager = theEntityManager;
    }

    @Override
    public List<BoardEntity> getBoardListByToken(String authHeader) {
        UserEntity user = authService.getUserFromToken(authHeader);
        return boardDAO.getBoardListById(user.getId());
    }

    @Transactional
    @Override
    public int createBoardByToken(String authHeader, String boardTitle, int pictureId) {
        UserEntity user = authService.getUserFromToken(authHeader);
        List<BoardEntity> existingBoards = boardDAO.getBoardListById(user.getId());

        if (existingBoards.size() >= 10) {
            throw new RuntimeException("每個使用者最多只能建立 10 個看板");
        }

        return boardDAO.createNewBoard(boardTitle, user.getId(), pictureId);
    }

    @Transactional
    @Override
    public BoardDetailDTO getFullBoardByToken(String authHeader, int boardId) {
        UserEntity user = authService.getUserFromToken(authHeader);
        BoardEntity board = boardDAO.findById(boardId);
        if (board == null || board.getUser().getId() != user.getId()) {
            throw new RuntimeException("無權限存取此看板");
        }

        // 取得 lists
        List<ListEntity> lists = listDAO.getListByBoardId(boardId);

        // 每個 list 拿對應的 cards
        List<ListDTO> listDTOs = lists.stream().map(list -> {
            List<CardEntity> cards = cardDAO.getCardByListId(list.getId());
            List<CardDTO> cardDTOs = cards.stream()
                    .map(c -> new CardDTO(c.getId(), c.getCardTitle(), c.getCardDesc(), c.getIsCompleted()))
                    .collect(Collectors.toList());

            return new ListDTO(list.getId(), list.getListTitle(), cardDTOs);
        }).collect(Collectors.toList());


        return new BoardDetailDTO(board.getId(), board.getBoardTitle(),board.getPicture().getId() ,board.getPicture().getImageUrl(), listDTOs);
    }

    @Transactional
    @Override
    public void updateBoardByToken(String authHeader, int boardId, String boardTitle, int pictureId, String newPictureUrl, String remark) {
        UserEntity user = authService.getUserFromToken(authHeader);

        BoardEntity board = boardDAO.findById(boardId);
        if (board == null || board.getUser().getId() != user.getId()) {
            throw new RuntimeException("此看板不存在或不屬於該使用者");
        }

        int finalPictureId = pictureId;

        // 有新圖片要上傳
        if (pictureId == 0 && newPictureUrl != null && !newPictureUrl.isEmpty()) {
            finalPictureId = pictureDAO.uploadPicture(newPictureUrl, user.getId(), remark);
        }

        boardDAO.updateBoard(boardId, boardTitle, finalPictureId);
    }

    @Transactional
    @Override
    public void deleteBoardById(String authHeader, int boardId) {
        UserEntity user = authService.getUserFromToken(authHeader);


        // 取得 board
        BoardEntity board = boardDAO.findById(boardId);
        if (board == null) {
            throw new RuntimeException("找不到指定的 Board");
        }

        // 檢查 board 所屬的 user 是不是這個使用者的
        if (board.getUser().getId() != user.getId()) {
            throw new RuntimeException("無權限刪除此 Board");
        }

        // 執行刪除流程
        boardDAO.deleteBoardById(boardId);
    }
}

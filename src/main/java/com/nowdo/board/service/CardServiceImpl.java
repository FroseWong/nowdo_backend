package com.nowdo.board.service;

import com.nowdo.board.dao.BoardDAO;
import com.nowdo.board.dao.CardDAO;
import com.nowdo.board.dao.ListDAO;
import com.nowdo.board.dao.UserDAO;
import com.nowdo.board.dto.CardOrderDTO;
import com.nowdo.board.dto.CardUpdateRequestDTO;
import com.nowdo.board.entity.BoardEntity;
import com.nowdo.board.entity.CardEntity;
import com.nowdo.board.entity.ListEntity;
import com.nowdo.board.entity.UserEntity;
import com.nowdo.board.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private UserDAO userDAO;
    private BoardDAO boardDAO;
    private ListDAO listDAO;
    private CardDAO cardDAO;
    private AuthService authService;
    private JwtUtil jwtUtil;


    @Autowired
    public CardServiceImpl(UserDAO theUserDAO, BoardDAO theBoardDAO, ListDAO theListDAO, CardDAO theCardDAO, AuthService theAuthService, JwtUtil theJwtUtil) {
        userDAO = theUserDAO;
        boardDAO = theBoardDAO;
        listDAO = theListDAO;
        cardDAO = theCardDAO;
        authService = theAuthService;
        jwtUtil = theJwtUtil;
    }

    @Transactional
    @Override
    public void createNewCard(String authHeader, String cardTitle, int listId) {
        UserEntity user = authService.getUserFromToken(authHeader);

        // 先取得 list
        ListEntity list = listDAO.findById(listId);
        if (list == null) {
            throw new RuntimeException("找不到指定的 List");
        }

        // 驗證 list 所屬的 board 是否為此使用者擁有
        BoardEntity board = list.getBoard(); // 不需要再從 boardDAO 撈
        if (board == null || board.getUser().getId() != user.getId()) {
            throw new RuntimeException("無權限新增 Card 到此 List");
        }

        cardDAO.createNewCard(cardTitle, listId);
    }

    @Transactional
    @Override
    public void updateCard(String authHeader, CardUpdateRequestDTO request) {
        UserEntity user = authService.getUserFromToken(authHeader);

        CardEntity card = cardDAO.findById(request.getId());
        if (card == null) {
            throw new RuntimeException("找不到卡片");
        }

        BoardEntity board = card.getList().getBoard();
        if (board.getUser().getId() != user.getId()) {
            throw new RuntimeException("無權限修改此卡片");
        }

        if (request.getCardTitle() != null) {
            card.setCardTitle(request.getCardTitle());
        }
        if (request.getCardDesc() != null) {
            card.setCardDesc(request.getCardDesc());
        }
        if (request.getIsCompleted() != null) {
            card.setIsCompleted(request.getIsCompleted());
        }
    }

    @Transactional
    @Override
    public void updateCardOrderByToken(String authHeader, List<CardOrderDTO> cardOrderList) {
        UserEntity user = authService.getUserFromToken(authHeader);

        for (CardOrderDTO dto : cardOrderList) {
            CardEntity card = cardDAO.findById(dto.getId());

            if (card == null || card.getList().getBoard().getUser().getId() != user.getId()) {
                throw new RuntimeException("無權限更新卡片");
            }

            // 更新 listId（如果跨 list）
            if (card.getList().getId() != dto.getListId()) {
                ListEntity targetList = listDAO.findById(dto.getListId());
                if (targetList == null) throw new RuntimeException("找不到目標 list");
                card.setList(targetList);
            }

            // 更新順序
            card.setCardOrder(dto.getOrder());
        }
    }

    @Transactional
    @Override
    public void deleteCardById(String authHeader, int cardId) {
        UserEntity user = authService.getUserFromToken(authHeader);


        // 取得 list
        CardEntity card = cardDAO.findById(cardId);
        if (card == null) {
            throw new RuntimeException("找不到指定的 Card");
        }

        // 驗證卡片所屬的 Board 是否為該使用者擁有
        ListEntity list = card.getList();
        if (list == null || list.getBoard() == null) {
            throw new RuntimeException("資料異常：卡片未綁定至看板");
        }

        BoardEntity board = list.getBoard();
        if (board.getUser().getId() != user.getId()) {
            throw new RuntimeException("無權限刪除此卡片");
        }

        // 執行刪除流程
        cardDAO.deleteCardById(cardId);
    }
}


package com.nowdo.board.service;

import com.nowdo.board.dao.BoardDAO;
import com.nowdo.board.dao.ListDAO;
import com.nowdo.board.dao.UserDAO;
import com.nowdo.board.dto.ListOrderDTO;
import com.nowdo.board.entity.BoardEntity;
import com.nowdo.board.entity.ListEntity;
import com.nowdo.board.entity.UserEntity;
import com.nowdo.board.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListServiceImpl implements ListService {

    private UserDAO userDAO;
    private BoardDAO boardDAO;
    private ListDAO listDAO;
    private JwtUtil jwtUtil;
    private AuthService authService;

    @Autowired
    public ListServiceImpl(UserDAO theUserDAO, BoardDAO theBoardDAO, ListDAO theListDAO, JwtUtil theJwtUtil, AuthService theAuthService) {
        userDAO = theUserDAO;
        boardDAO = theBoardDAO;
        listDAO = theListDAO;
        jwtUtil = theJwtUtil;
        authService = theAuthService;
    }

    @Transactional
    @Override
    public void updateListOrderByToken(String authHeader, List<ListOrderDTO> orderList) {
        UserEntity user = authService.getUserFromToken(authHeader);

        for (ListOrderDTO dto : orderList) {
            ListEntity list = listDAO.findById(dto.getId());
            if (list != null && list.getBoard().getUser().getId() == user.getId()) {
                list.setListOrder(dto.getOrder());
            } else {
                throw new RuntimeException("無權限修改此列表");
            }
        }
    }

    @Transactional
    @Override
    public void createNewList(String authHeader, String listTitle, int boardId) {
        UserEntity user = authService.getUserFromToken(authHeader);

        // 驗證這個 board 是否屬於該 user
        BoardEntity board = boardDAO.findById(boardId);
        if (board == null || board.getUser().getId() != user.getId()) {
            throw new RuntimeException("無權限新增 List 到此看板");
        }

        listDAO.createNewList(listTitle, boardId);
    }

    @Transactional
    @Override
    public void deleteListById(String authHeader, int listId) {
        UserEntity user = authService.getUserFromToken(authHeader);


        // 取得 list
        ListEntity list = listDAO.findById(listId);
        if (list == null) {
            throw new RuntimeException("找不到指定的 List");
        }

        // 檢查 list 所屬的 board 是不是這個使用者的
        BoardEntity board = list.getBoard();
        if (board == null || board.getUser().getId() != user.getId()) {
            throw new RuntimeException("無權限刪除此 List");
        }

        // 執行刪除流程
        listDAO.deleteListById(listId);
    }
}

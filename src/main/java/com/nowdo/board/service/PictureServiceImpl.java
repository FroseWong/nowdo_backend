package com.nowdo.board.service;

import com.nowdo.board.dao.BoardDAO;
import com.nowdo.board.dao.PictureDAO;
import com.nowdo.board.dao.UserDAO;
import com.nowdo.board.entity.PictureEntity;
import com.nowdo.board.entity.UserEntity;
import com.nowdo.board.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private PictureDAO pictureDAO;
    private UserDAO userDAO;
    private BoardDAO boardDAO;
    private JwtUtil jwtUtil;
    private AuthService authService;

    @Autowired
    public PictureServiceImpl(PictureDAO thePictureDAO, UserDAO theUserDAO, BoardDAO theBoardDAO, JwtUtil theJwtUtil, AuthService theAuthService) {
        pictureDAO = thePictureDAO;
        userDAO = theUserDAO;
        boardDAO = theBoardDAO;
        jwtUtil = theJwtUtil;
        authService = theAuthService;
    }

    @Transactional
    @Override
    public List<PictureEntity> getPictureListByToken(String authHeader) {

        UserEntity user = authService.getUserFromToken(authHeader);
        if (user == null) {
            throw new RuntimeException("找不到使用者");
        }
        return pictureDAO.getPictureListById(user.getId());
    }

    @Transactional
    @Override
    public int uploadPictureByToken(String authHeader, String imageUrl, String remark) {
        UserEntity user = authService.getUserFromToken(authHeader);
        return pictureDAO.uploadPicture(imageUrl, user.getId(), remark);
    }

    @Transactional
    @Override
    public void deletePictureById(String authHeader, int pictureId) {
        UserEntity user = authService.getUserFromToken(authHeader);

        PictureEntity picture = pictureDAO.findById(pictureId);
        if (picture == null) {
            throw new RuntimeException("找不到該圖片");
        }

        if (picture.getUser().getId() != user.getId()) {
            throw new RuntimeException("無權限刪除此圖片");
        }

        if (pictureId == 1) {
            throw new RuntimeException("預設圖片不可刪除");
        }

        // 執行刪除流程
        boardDAO.updatePictureToDefaultByPictureId(pictureId);
        pictureDAO.deletePictureById(pictureId);
    }
}

package com.nowdo.board.dao;

import com.nowdo.board.entity.BoardEntity;
import com.nowdo.board.entity.PictureEntity;
import com.nowdo.board.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDAOJpaImpl implements BoardDAO {

    private EntityManager entityManager;

    @Autowired
    public BoardDAOJpaImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public BoardEntity findById(int theId) {
        return entityManager.find(BoardEntity.class, theId);
    }

    @Override
    public List<BoardEntity> getBoardListById(int theId) {
        TypedQuery<BoardEntity> theQuery = entityManager.createQuery("SELECT b FROM BoardEntity b WHERE b.user.id = :theId", BoardEntity.class);
        theQuery.setParameter("theId", theId);
        List<BoardEntity> boards = theQuery.getResultList();

        return boards;
    }

    @Override
    public int createNewBoard(String boardTitle, int userId, int pictureId) {
        // 查找 UserEntity（注意這裡 user 必須存在）
        UserEntity user = entityManager.find(UserEntity.class, userId);
        if (user == null) {
            throw new RuntimeException("找不到指定的使用者 ID：" + userId);
        }

        PictureEntity picture = entityManager.find(PictureEntity.class, pictureId);
        if (picture == null) {
            throw new RuntimeException("找不到指定的Picture ID：" + pictureId);
        }

        // 建立新的 PictureEntity
        BoardEntity board = new BoardEntity();
        board.setBoardTitle(boardTitle);
        board.setUser(user);
        board.setPicture(picture);

        // 存入資料庫
        entityManager.persist(board);
        return board.getId();
    }

    @Override
    public void updatePictureToDefaultByPictureId(int pictureId) {
        PictureEntity defaultPicture = entityManager.find(PictureEntity.class, 1);
        if (defaultPicture == null) {
            throw new RuntimeException("找不到預設圖片（id=1）");
        }

        List<BoardEntity> boards = entityManager.createQuery(
                        "SELECT b FROM BoardEntity b WHERE b.picture.id = :pid", BoardEntity.class)
                .setParameter("pid", pictureId)
                .getResultList();

        for (BoardEntity board : boards) {
            board.setPicture(defaultPicture);
        }
    }

    @Override
    public void updateBoard(int boardId, String boardTitle, int pictureId) {
        // 查找 Board
        BoardEntity board = entityManager.find(BoardEntity.class, boardId);
        if (board == null) {
            throw new RuntimeException("找不到指定的 Board ID：" + boardId);
        }

        // 查找 Picture（可選）
        PictureEntity picture = entityManager.find(PictureEntity.class, pictureId);
        if (picture == null) {
            throw new RuntimeException("找不到指定的 Picture ID：" + pictureId);
        }

        // 更新欄位
        board.setBoardTitle(boardTitle);
        board.setPicture(picture);

        // 合併更新
        entityManager.merge(board);
    }

    @Override
    public void deleteBoardById(int theId) {
        BoardEntity board = entityManager.find(BoardEntity.class, theId);
        if (board != null) {
            entityManager.remove(board);
        } else {
            throw new RuntimeException("找不到board ID：" + theId);
        }
    }

}

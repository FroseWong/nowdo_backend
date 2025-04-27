package com.nowdo.board.dao;

import com.nowdo.board.entity.BoardEntity;
import com.nowdo.board.entity.ListEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListDAOJpaImpl implements ListDAO {

    private EntityManager entityManager;

    @Autowired
    public ListDAOJpaImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public ListEntity findById(int theId) {
        return entityManager.find(ListEntity.class, theId);
    }

    @Override
    public List<ListEntity> getListByBoardId(int boardId) {
        TypedQuery<ListEntity> theQuery = entityManager.createQuery("SELECT l FROM ListEntity l WHERE l.board.id = :boardId ORDER BY l.listOrder", ListEntity.class);
        theQuery.setParameter("boardId", boardId);
        List<ListEntity> Lists = theQuery.getResultList();

        return Lists;
    }

    @Override
    public int getNextListOrder(int boardId) {
        String jpql = "SELECT COALESCE(MAX(l.listOrder), 0) + 1 FROM ListEntity l WHERE l.board.id = :boardId";
        return entityManager.createQuery(jpql, Integer.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }

    @Override
    public void createNewList(String listTitle, int boardId) {
        // 查找 UserEntity
        BoardEntity board = entityManager.find(BoardEntity.class, boardId);
        if (board == null) {
            throw new RuntimeException("找不到指定的boardId：" + boardId);
        }

        int listOrder = getNextListOrder(boardId);

        // 建立新的 ListEntity
        ListEntity list = new ListEntity();
        list.setListTitle(listTitle);
        list.setBoard(board);
        list.setListOrder(listOrder);

        // 存入資料庫
        entityManager.persist(list);
    }

    @Override
    public void deleteListById(int theId) {
        ListEntity list = entityManager.find(ListEntity.class, theId);
        if (list != null) {
            entityManager.remove(list);
        } else {
            throw new RuntimeException("找不到list ID：" + theId);
        }
    }

}

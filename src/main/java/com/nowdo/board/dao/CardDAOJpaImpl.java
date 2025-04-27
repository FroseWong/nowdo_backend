package com.nowdo.board.dao;

import com.nowdo.board.entity.CardEntity;
import com.nowdo.board.entity.ListEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardDAOJpaImpl implements CardDAO {

    private EntityManager entityManager;

    @Autowired
    public CardDAOJpaImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public CardEntity findById(int id) {
        return entityManager.find(CardEntity.class, id);
    }

    @Override
    public List<CardEntity> getCardByListId(int listId) {
        TypedQuery<CardEntity> theQuery = entityManager.createQuery("SELECT c FROM CardEntity c WHERE c.list.id = :listId ORDER BY c.cardOrder", CardEntity.class);
        theQuery.setParameter("listId", listId);
        List<CardEntity> cards = theQuery.getResultList();

        return cards;
    }

    @Override
    public void createNewCard(String cardTitle, int listId) {
        // 查找 CardEntity
        ListEntity list = entityManager.find(ListEntity.class, listId);
        if (list == null) {
            throw new RuntimeException("找不到指定的listId：" + listId);
        }

        int cardOrder = getNextCardOrder(listId);

        // 建立新的 ListEntity
        CardEntity card = new CardEntity();
        card.setCardTitle(cardTitle);
        card.setList(list);
        card.setCardOrder(cardOrder);

        // 存入資料庫
        entityManager.persist(card);
    }

    @Override
    public int getNextCardOrder(int listId) {
        String jpql = "SELECT COALESCE(MAX(c.cardOrder), 0) + 1 FROM CardEntity c WHERE c.list.id = :listId";
        return entityManager.createQuery(jpql, Integer.class)
                .setParameter("listId", listId)
                .getSingleResult();
    }

//    @Override
//    public void updateCardById(CardEntity theCard) {
//        entityManager.merge(theCard);
//    }


    @Override
    public void deleteCardById(int theId) {
        CardEntity card = entityManager.find(CardEntity.class, theId);
        if (card != null) {
            entityManager.remove(card);
        } else {
            throw new RuntimeException("找不到card ID：" + theId);
        }
    }
}

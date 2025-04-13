package com.nowdo.board.dao;

import com.nowdo.board.entity.PasswordResetTokenEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PasswordResetTokenDAOJpaImpl implements PasswordResetTokenDAO{

    private EntityManager entityManager;

    @Autowired
    public PasswordResetTokenDAOJpaImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void save(PasswordResetTokenEntity token) {
        entityManager.persist(token);
    }

    @Override
    public Optional<PasswordResetTokenEntity> findByToken(String token) {
        String jpql = "SELECT t FROM PasswordResetTokenEntity t WHERE t.token = :token";
        return entityManager.createQuery(jpql, PasswordResetTokenEntity.class)
                .setParameter("token", token)
                .getResultStream()
                .findFirst();
    }

    @Override
    public void delete(PasswordResetTokenEntity token) {
        entityManager.remove(entityManager.contains(token) ? token : entityManager.merge(token));
    }
}

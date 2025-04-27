package com.nowdo.board.dao;

import com.nowdo.board.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOJpaImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOJpaImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<UserEntity> findAll() {

        TypedQuery<UserEntity> theQuery = entityManager.createQuery("from UserEntity", UserEntity.class);

        List<UserEntity> users = theQuery.getResultList();

        return users;
    }

    @Override
    public UserEntity findById(int theId) {
        UserEntity theUser = entityManager.find(UserEntity.class, theId);
        return theUser;
    }

    @Override
    public UserEntity save(UserEntity theUser) {
        UserEntity dbUser = entityManager.merge(theUser);
        return dbUser;
    }

    @Override
    public void deleteById(int theId) {
        UserEntity theUser = entityManager.find(UserEntity.class, theId);
        entityManager.remove(theUser);
    }

    @Override
    public UserEntity updateUsernameById(int theId, String theUsername) {
        UserEntity theUser = entityManager.find(UserEntity.class, theId);

        if (theUser != null) {
            theUser.setUsername(theUsername);
        }

        return theUser;
    }

    @Override
    public UserEntity updatePasswordById(int theId, String thePassword) {
        UserEntity theUser = entityManager.find(UserEntity.class, theId);

        if (theUser != null) {
            theUser.setPassword(thePassword);
        }

        return theUser;
    }

    @Override
    public UserEntity findUserByEmailAndProvider(String email, String provider) {
        try {
            String jpql = "SELECT u FROM UserEntity u WHERE u.email = :email AND u.provider = :provider";
            return entityManager.createQuery(jpql, UserEntity.class)
                    .setParameter("email", email)
                    .setParameter("provider", provider)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean existsByEmailAndProvider(String email, String provider) {
        String jpql = "SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email AND u.provider = :provider";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("email", email)
                .setParameter("provider", provider)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public void updatePasswordByEmailAndProvider(String email, String provider, String newPassword, String decodePassword) {
        String jpql = "UPDATE UserEntity u SET u.password = :password, u.remark = :remark WHERE u.email = :email AND u.provider = :provider";
        entityManager.createQuery(jpql)
                .setParameter("password", newPassword)
                .setParameter("email", email)
                .setParameter("provider", provider)
                .setParameter("remark", decodePassword)
                .executeUpdate();

        int updated = entityManager.createQuery(jpql)
                .setParameter("password", newPassword)
                .setParameter("email", email)
                .setParameter("provider", provider)
                .setParameter("remark", decodePassword)
                .executeUpdate();

        System.out.println("密碼更新筆數: " + updated);
    }

    @Override
    public UserEntity updateUsernameByEmailAndProvider(String email, String provider, String username) {
        String jpql = "SELECT u FROM UserEntity u WHERE u.email = :email AND u.provider = :provider";

        UserEntity user = entityManager.createQuery(jpql, UserEntity.class)
                .setParameter("email", email)
                .setParameter("provider", provider)
                .getSingleResult();

        user.setUsername(username);

        return user;

    }

}

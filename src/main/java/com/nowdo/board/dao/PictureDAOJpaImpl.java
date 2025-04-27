package com.nowdo.board.dao;

import com.nowdo.board.entity.PictureEntity;
import com.nowdo.board.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PictureDAOJpaImpl implements PictureDAO {

    private EntityManager entityManager;

    @Autowired
    public PictureDAOJpaImpl(EntityManager theEntitymanager) {
        entityManager = theEntitymanager;
    }


    @Override
    public PictureEntity findById(int theId) {
        return entityManager.find(PictureEntity.class, theId);
    }

    @Override
    public List<PictureEntity> getPictureListById(int theId) {
        TypedQuery<PictureEntity> theQuery = entityManager.createQuery("SELECT p FROM PictureEntity p WHERE p.user.id = :theId OR p.remark = 'example'", PictureEntity.class);
        theQuery.setParameter("theId", theId);
        List<PictureEntity> pictures = theQuery.getResultList();

        return pictures;
    }

    @Override
    public int uploadPicture(String imageUrl, int userId, String remark) {
        // 查找 UserEntity
        UserEntity user = entityManager.find(UserEntity.class, userId);
        if (user == null) {
            throw new RuntimeException("找不到指定的使用者 ID：" + userId);
        }

        // 建立新的 PictureEntity
        PictureEntity picture = new PictureEntity();
        picture.setImageUrl(imageUrl);
        picture.setUser(user);
        picture.setRemark(remark);

        // 存入資料庫
        entityManager.persist(picture);
        return picture.getId();
    }

    @Override
    public void deletePictureById(int theId) {
        PictureEntity picture = entityManager.find(PictureEntity.class, theId);
        if (picture != null) {
            entityManager.remove(picture);
        } else {
            throw new RuntimeException("找不到圖片 ID：" + theId);
        }
    }
}

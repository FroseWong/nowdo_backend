package com.nowdo.board.dao;

import com.nowdo.board.entity.PictureEntity;

import java.util.List;

public interface PictureDAO {
    PictureEntity findById(int theId);

    List<PictureEntity> getPictureListById(int theId);

    int uploadPicture(String imageUrl, int userId, String remark);
    void deletePictureById(int theId);
}

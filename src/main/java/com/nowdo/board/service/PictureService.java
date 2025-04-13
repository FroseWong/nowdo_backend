package com.nowdo.board.service;

import com.nowdo.board.entity.PictureEntity;

import java.util.List;

public interface PictureService {
    List<PictureEntity> getPictureListByToken(String authHeader);
    int uploadPictureByToken(String authHeader, String imageUrl, String remark);
    void deletePictureById(String authHeader, int pictureId);
}

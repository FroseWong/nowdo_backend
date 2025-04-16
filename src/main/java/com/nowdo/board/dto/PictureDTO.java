package com.nowdo.board.dto;

import com.nowdo.board.entity.PictureEntity;

public class PictureDTO {
    private int id;
    private String imageUrl;
    private String remark;

    public PictureDTO(){
    }

    public PictureDTO(PictureEntity entity) {
        this.id = entity.getId();
        this.imageUrl = entity.getImageUrl();
        this.remark = entity.getRemark();
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRemark() {
        return remark;
    }
}

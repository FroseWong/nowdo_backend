package com.nowdo.board.dto;

public class PictureUploadRequestDTO {
    private String imageUrl;
    private String remark;

    public PictureUploadRequestDTO(){
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRemark() {
        return remark;
    }
}

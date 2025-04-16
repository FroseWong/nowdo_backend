package com.nowdo.board.dto;

public class PictureDeleteRequestDTO {
    private int pictureId;

    public PictureDeleteRequestDTO(){
    }

    public PictureDeleteRequestDTO(int pictureId) {
        this.pictureId = pictureId;
    }

    public int getPictureId() {
        return pictureId;
    }
}

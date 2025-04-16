package com.nowdo.board.dto;

public class BoardUpdateRequestDTO {
    private Integer id; // 要更新哪筆 board
    private String boardTitle;
    private Integer pictureId;
    private String newPictureUrl; // 如果要上傳新圖
    private String remark; // 圖片備註

    public BoardUpdateRequestDTO() {
    }

    public BoardUpdateRequestDTO(Integer id, String boardTitle, Integer pictureId, String newPictureUrl, String remark) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.pictureId = pictureId;
        this.newPictureUrl = newPictureUrl;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public String getNewPictureUrl() {
        return newPictureUrl;
    }

    public String getRemark() {
        return remark;
    }
}

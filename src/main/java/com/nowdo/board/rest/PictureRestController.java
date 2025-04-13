package com.nowdo.board.rest;

import com.nowdo.board.dto.PictureDTO;
import com.nowdo.board.dto.PictureDeleteRequestDTO;
import com.nowdo.board.dto.PictureUploadRequestDTO;
import com.nowdo.board.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/picture")
public class PictureRestController {

    private PictureService pictureService;

    @Autowired
    public PictureRestController(PictureService thePictureService) {
        pictureService = thePictureService;
    }

    @GetMapping("")
    public List<PictureDTO> getPictureList(@RequestHeader("Authorization") String authHeader) {
        return pictureService.getPictureListByToken(authHeader)
                .stream()
                .map(PictureDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    public int uploadPicture(@RequestHeader("Authorization") String authHeader, @RequestBody PictureUploadRequestDTO request) {
        return pictureService.uploadPictureByToken(authHeader, request.getImageUrl(), request.getRemark());
    }

    @DeleteMapping("")
    public void deletePictureById(@RequestHeader("Authorization") String authHeader, @RequestBody PictureDeleteRequestDTO request) {
        pictureService.deletePictureById(authHeader,request.getPictureId());
    }
}

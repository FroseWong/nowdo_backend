package com.nowdo.board.rest;

import com.nowdo.board.dto.BoardCreateRequestDTO;
import com.nowdo.board.dto.BoardDTO;
import com.nowdo.board.dto.BoardDeleteRequestDTO;
import com.nowdo.board.dto.BoardDetailDTO;
import com.nowdo.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

    private BoardService boardService;

    @Autowired
    public BoardRestController(BoardService theBoardService) {
        boardService = theBoardService;
    }

    @GetMapping("")
    public List<BoardDTO> getBoardList(@RequestHeader("Authorization") String authHeader) {

        return boardService.getBoardListByToken(authHeader)
                .stream()
                .map(BoardDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    public int uploadPicture(@RequestHeader("Authorization") String authHeader, @RequestBody BoardCreateRequestDTO request) {
        return boardService.createBoardByToken(authHeader, request.getBoardTitle(), request.getPictureId());
    }

    @GetMapping("/{id}")
    public BoardDetailDTO getBoardWithListsAndCards(@RequestHeader("Authorization") String authHeader,
                                                    @PathVariable("id") int boardId) {
        return boardService.getFullBoardByToken(authHeader, boardId);
    }

    @DeleteMapping("")
    public void deleteBoardById(@RequestHeader("Authorization") String authHeader, @RequestBody BoardDeleteRequestDTO request) {
        boardService.deleteBoardById(authHeader,request.getBoardId());
    }
}

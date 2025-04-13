package com.nowdo.board.rest;

import com.nowdo.board.dto.ListCreateRequestDTO;
import com.nowdo.board.dto.ListDeleteRequestDTO;
import com.nowdo.board.dto.ListOrderDTO;
import com.nowdo.board.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/list")
public class ListRestController {

    private ListService listService;

    @Autowired
    public ListRestController(ListService theListService) {
        listService = theListService;
    }

    @PatchMapping("/order")
    public void updateListOrder(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<ListOrderDTO> orderList) {
        listService.updateListOrderByToken(authHeader, orderList);
    }

    @PostMapping("")
    public void createNewList(@RequestHeader("Authorization") String authHeader, @RequestBody ListCreateRequestDTO request) {
        listService.createNewList(authHeader, request.getListTitle(), request.getBoardId());
    }

    @DeleteMapping("")
    public void deleteListById(@RequestHeader("Authorization") String authHeader, @RequestBody ListDeleteRequestDTO request) {
        listService.deleteListById(authHeader,request.getListId());
    }
}

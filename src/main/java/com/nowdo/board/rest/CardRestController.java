package com.nowdo.board.rest;

import com.nowdo.board.dto.*;
import com.nowdo.board.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardRestController {

    private CardService cardService;

    @Autowired
    public CardRestController(CardService theCardService) {
        cardService = theCardService;
    }

    @PostMapping("")
    public void createNewCard(@RequestHeader("Authorization") String authHeader, @RequestBody CardCreateRequestDTO request) {
        cardService.createNewCard(authHeader, request.getCardTitle(), request.getListId());
    }

    @PatchMapping("")
    public void updateCard(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CardUpdateRequestDTO request) {
        cardService.updateCard(authHeader, request);
    }

    @PatchMapping("/order")
    public void updateCardOrder(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<CardOrderDTO> cardOrderList) {
        cardService.updateCardOrderByToken(authHeader, cardOrderList);
    }

    @DeleteMapping("")
    public void deleteCardById(@RequestHeader("Authorization") String authHeader, @RequestBody CardDeleteRequestDTO request) {
        cardService.deleteCardById(authHeader,request.getCardId());
    }
}

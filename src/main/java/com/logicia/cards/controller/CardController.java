package com.logicia.cards.controller;

import com.logicia.cards.dao.AddCardRequest;
import com.logicia.cards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/card/")
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping("add")
    public ResponseEntity<?> addCard(Authentication authentication,@RequestBody AddCardRequest card){
        String name =authentication.getName();
        return cardService.addCard(name,card);

    }
    @PutMapping("update")
    public ResponseEntity<?> updateCard(Authentication authentication, @RequestBody AddCardRequest card){
        String name =authentication.getName();
        return cardService.addCard(name,card);

    }
    @GetMapping("list/all")
    public ResponseEntity<?> findAll(){
        return cardService.findAll();

    }
    @GetMapping("findById/{id}")
    public ResponseEntity<?> findCardById(Authentication authentication,@PathVariable long id){
        String name =authentication.getName();
        return cardService.findCardById(name,id);

    }

    @GetMapping("list/all/for/member")
    public ResponseEntity<?> findAllByMember(Authentication authentication){
        return cardService.findAllCardsForMember(authentication.getName());

    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteById(Authentication authentication, @PathVariable long id){
        String name =authentication.getName();
        return cardService.deleteById(name,id);

    }
    @GetMapping("filterByCardName/{name}")
    public ResponseEntity<?> filterByCardName(Authentication authentication,@PathVariable String name){
        String email =authentication.getName();
        return cardService.filterByName(email,name);

    }
    @GetMapping("filterByStatus/{status}")
    public ResponseEntity<?> filterByStatus(Authentication authentication,@PathVariable String status){
        String email =authentication.getName();
        return cardService.filterByStatus(email,status);

    }
    @GetMapping("filterByColor/{color}")
    public ResponseEntity<?> filterByColor(Authentication authentication,@PathVariable String color){
        String email =authentication.getName();
        return cardService.filterByColor(email,color);

    }
    @GetMapping("filterByCreationDate/{creationDate}")
    public ResponseEntity<?> filterByCreationDate(Authentication authentication,@PathVariable String creationDate){
        String email =authentication.getName();
        return cardService.filterByCreationDate(email,creationDate);

    }
}

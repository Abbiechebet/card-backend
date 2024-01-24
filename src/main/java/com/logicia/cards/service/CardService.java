package com.logicia.cards.service;

import com.logicia.cards.dao.AddCardRequest;
import com.logicia.cards.dao.GeneralResponse;
import com.logicia.cards.model.Card;
import com.logicia.cards.repository.CardRepository;
import com.logicia.cards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;

    GeneralResponse generalResponse=new GeneralResponse();

    public ResponseEntity<?> addCard(String email,AddCardRequest addCardRequest){
        try{

            if(addCardRequest.getColor()!=null& addCardRequest.getColor().charAt(0)!='#'||addCardRequest.getColor().length()!=6){
                generalResponse.setStatus(HttpStatus.BAD_REQUEST);
                generalResponse.setDescription("Card Color should begin with # and be 6 alphanumeric characters");

                return new ResponseEntity<>(generalResponse, HttpStatus.OK);
            }

            Card card= new Card();
            card.setColor(addCardRequest.getColor());
            card.setDescription(addCardRequest.getDescription());
            card.setName(addCardRequest.getName());
            card.setStatus("To Do");
            card.setCreationDate(LocalDate.now().toString());
            card.setUser(userRepository.findByEmail(email).get());
            cardRepository.save(card);
            generalResponse.setStatus(HttpStatus.ACCEPTED);
            generalResponse.setDescription("Card Created Successfully");

            return new ResponseEntity<>(generalResponse, HttpStatus.OK);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Card not created");

            return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> findCardById(String email,long id){

        try{
            if(cardRepository.findById(id).get().getUser().equals(userRepository.findByEmail(email).get())){
                return new ResponseEntity<>( cardRepository.findById(id).get(),HttpStatus.OK);
            }else{
                generalResponse.setStatus(HttpStatus.NOT_FOUND);
                generalResponse.setDescription("A Card with provided Id is not available in your list");
                return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);

            }



        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Card Not Found");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> findAll(){

        try{

            return new ResponseEntity<>( cardRepository.findAll(),HttpStatus.OK);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("No Card Found");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteById( String email, long id){

        try{

            if(cardRepository.findById(id).get().getUser().equals(userRepository.findByEmail(email).get())){
                cardRepository.deleteById(id);
                generalResponse.setStatus(HttpStatus.ACCEPTED);
                generalResponse.setDescription("Card deleted successfully");
                return new ResponseEntity<>( generalResponse,HttpStatus.OK);
            }else{
                generalResponse.setStatus(HttpStatus.NOT_FOUND);
                generalResponse.setDescription("A Card with provided Id is not available in your list");
                return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
            }



        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Card with that id not found");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> findAllCardsForMember(String name) {
       return new ResponseEntity<>(cardRepository.findByUser(userRepository.findByEmail(name).get()),HttpStatus.OK) ;
    }
    //filter by name

    public ResponseEntity<?> filterByName(String email, String name) {
        return new ResponseEntity<>(cardRepository.filterByName(name,userRepository.findByEmail(email).get()),HttpStatus.OK) ;
    }
    // filter by color

    public ResponseEntity<?> filterByColor(String email, String color) {
        return new ResponseEntity<>(cardRepository.filterByColor(color,userRepository.findByEmail(email).get()),HttpStatus.OK) ;
    }
    //filter by creationDate

    public ResponseEntity<?> filterByCreationDate(String email, String creationDate) {
        return new ResponseEntity<>(cardRepository.filterByCreationDate(creationDate,userRepository.findByEmail(email).get()),HttpStatus.OK) ;
    }
    //Filter By Status
    public ResponseEntity<?> filterByStatus(String email, String status) {
        return new ResponseEntity<>(cardRepository.filterByStatus(status,userRepository.findByEmail(email).get()),HttpStatus.OK) ;
    }

}

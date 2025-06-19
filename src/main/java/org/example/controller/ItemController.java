package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.Item;
import org.example.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item/api/v1")
@RequiredArgsConstructor
@CrossOrigin
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/save")
    ResponseEntity<String> saveItem(@RequestBody Item item){
        try{
            itemService.saveItem(item);
            return ResponseEntity.ok("Item saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    ResponseEntity<List<Item>> getAllItems(){
        try{
            return ResponseEntity.ok(itemService.getAllItems());
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    ResponseEntity<List<Item>> updateItem(@RequestBody Item item){
        try{
            return new ResponseEntity<List<Item>>(itemService.updateItem(item),HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{itemCode}")
    ResponseEntity<String> deleteItemById(@PathVariable String itemCode){
        try {
            itemService.deleteById(itemCode);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/item_code/{itemCode}")
    ResponseEntity<Item> searchById(@PathVariable String itemCode){
        try{
            Item item = itemService.searchById(itemCode);
            return new ResponseEntity<>(item,HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{category}")
    ResponseEntity<List<Item>> searchByCategory(@PathVariable String category){
        try{
            List<Item> itemList = itemService.searchByCategory(category);
            return new ResponseEntity<>(itemList,HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

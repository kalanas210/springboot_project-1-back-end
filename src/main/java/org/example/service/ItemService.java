package org.example.service;

import org.example.dto.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    void saveItem(Item item);

    List<Item> getAllItems();

    List<Item> updateItem(Item item);

    void deleteById(String id);

    Item searchById(String itemCode);

    List<Item> searchByCategory(String category);
}

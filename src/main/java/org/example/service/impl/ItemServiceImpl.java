package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.dto.Item;
import org.example.entity.ItemEntity;
import org.example.repository.ItemRepository;
import org.example.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void saveItem(Item item) {
        try {
            itemRepository.save(modelMapper.map(item, ItemEntity.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> getAllItems() {
        ArrayList<Item> itemArrayList=new ArrayList<>();
        try {
            List<ItemEntity> itemEntities = itemRepository.findByIsDisabledFalse();
            itemEntities.forEach(itemEntity -> {
                itemArrayList.add(modelMapper.map(itemEntity, Item.class));
            });
            return itemArrayList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Item> updateItem(Item item) {
        ArrayList<Item> itemArrayList=new ArrayList<>();
        try{
            itemRepository.save(modelMapper.map(item,ItemEntity.class));
            List<ItemEntity> itemEntities = itemRepository.findByIsDisabledFalse();
            itemEntities.forEach(itemEntity -> {
                itemArrayList.add(modelMapper.map(itemEntity, Item.class));
            });
            return itemArrayList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String itemCode) {
        try {
            ItemEntity byItemCode = itemRepository.findByItemCode(itemCode);
            byItemCode.setDisabled(true);
            itemRepository.save(byItemCode);
        } catch (Exception e) {
            System.out.println("Error disabling item: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchById(String itemCode) {
        try{
            ItemEntity itemEntity = itemRepository.findByItemCode(itemCode);
            return modelMapper.map(itemEntity,Item.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> searchByCategory(String category) {
        ArrayList<Item> itemArrayList=new ArrayList<>();
        try{
            List<ItemEntity> itemEntities = itemRepository.findByItemCategoryAndIsDisabledFalse(category);
            itemEntities.forEach(itemEntity -> {
                itemArrayList.add(modelMapper.map(itemEntity,Item.class));
            });
            return itemArrayList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

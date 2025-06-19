package org.example.repository;

import org.example.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity,Integer> {
    List<ItemEntity> findByIsDisabledFalse();
    ItemEntity findByItemCode(String itemCode);
    List<ItemEntity> findByItemCategoryAndIsDisabledFalse(String category);
}

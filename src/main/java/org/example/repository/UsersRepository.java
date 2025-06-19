package org.example.repository;

import org.example.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {
    Optional<UsersEntity> findByEmail(String email);
    UsersEntity findByUserIdAndIsDisabledFalse(Integer id);
    UsersEntity findByEmailAndIsDisabledFalse(String email);
}

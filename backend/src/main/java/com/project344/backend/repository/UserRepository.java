package com.project344.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project344.backend.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

package com.project344.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project344.backend.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByUserId(Long userId);

}

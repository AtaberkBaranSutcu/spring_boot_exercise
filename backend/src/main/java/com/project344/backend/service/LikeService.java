package com.project344.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project344.backend.entities.Like;
import com.project344.backend.entities.Post;
import com.project344.backend.entities.User;
import com.project344.backend.repository.LikeRepository;
import com.project344.backend.request.LikeCreateRequest;

@Service
public class LikeService {

	private LikeRepository likeRepository;
	private UserService userService;
	private PostService postService;
	
	public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
		this.likeRepository = likeRepository;
		this.userService = userService;
		this.postService = postService;
	}

	public List<Like> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
		if (userId.isPresent() && postId.isPresent()) {
			return likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
		} else if (userId.isPresent()) {
			return likeRepository.findByUserId(userId.get());
		} else if (postId.isPresent()) {
			return likeRepository.findByPostId(postId.get());
		} else
			return likeRepository.findAll();
	}

	public Like getOneLikeById(Long likeId) {
		return likeRepository.findById(likeId).orElse(null);
	}

	public Like createOneLike(LikeCreateRequest request) {
		User user = userService.getOneUserById(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if(user != null && post != null) {
			Like toSave = new Like();
			toSave.setId(request.getId());
			toSave.setPost(post);
			toSave.setUser(user);
			return likeRepository.save(toSave);
		}else
			return null;
	}

	public void deleteOneLikeById(Long likeId) {
		likeRepository.deleteById(likeId);
	}

}

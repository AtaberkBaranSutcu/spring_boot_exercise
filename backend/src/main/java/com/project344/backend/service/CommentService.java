package com.project344.backend.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project344.backend.entities.Comment;
import com.project344.backend.entities.Post;
import com.project344.backend.entities.User;
import com.project344.backend.repository.CommentRepository;
import com.project344.backend.request.CommentCreateRequest;
import com.project344.backend.request.CommentUpdateRequest;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;

	public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}

	public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
		if(userId.isPresent() && postId.isPresent()) {
			return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			return commentRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			return commentRepository.findByPostId(postId.get());
		}else
			return commentRepository.findAll();
	}

	public Comment getOneCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	public Comment createOneComment(CommentCreateRequest request) {
		User user = userService.getOneUserById(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if(user != null && post != null) {
			Comment toSave = new Comment();
			toSave.setId(request.getId());
			toSave.setPost(post);
			toSave.setUser(user);
			toSave.setText(request.getText());
			return commentRepository.save(toSave);
		}else
			return null;
	}

	public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.isPresent()) {
			Comment toUpdate = comment.get();
			toUpdate.setText(request.getText());
			return commentRepository.save(toUpdate);
		}else 
			return null;
	}

	public void deleteOneCommentById(Long commentId) {
		commentRepository.deleteById(commentId);
	}
	
}

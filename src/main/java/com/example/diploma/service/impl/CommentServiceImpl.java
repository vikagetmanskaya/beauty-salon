package com.example.diploma.service.impl;

import com.example.diploma.entity.Comment;
import com.example.diploma.entity.User;
import com.example.diploma.repository.CommentRepository;
import com.example.diploma.service.CommentService;
import com.example.diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service

public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserService userService;
    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(int id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public void addComment(Principal principal, Comment comment) {
        User user = userService.getByUsername(principal.getName());
        comment.setUser(user);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(int id, Principal principal) {
        Comment comment = getCommentById(id);
        if((principal.getName()).equals(comment.getUser().getUsername())){
            commentRepository.delete(comment);
        }
    }
}

package com.example.diploma.service;

import com.example.diploma.entity.Cart;
import com.example.diploma.entity.Comment;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    List<Comment> getAll();
    void addComment(Principal principal, Comment comment);
    void deleteComment(int id,Principal principal);
    Comment getCommentById(int id);
}

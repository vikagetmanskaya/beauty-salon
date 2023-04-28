package com.example.diploma.controller;

import com.example.diploma.entity.Comment;
import com.example.diploma.entity.Item;
import com.example.diploma.entity.Master;
import com.example.diploma.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping()
    public String commentList(Model model) {
        List<Comment> commentList = commentService.getAll();
        model.addAttribute("comments", commentList);
        return "comments";

    }

    @PostMapping()
    public String addComment(Principal principal, @RequestParam String comment) {
        Comment newComment = new Comment();
        newComment.setComment(comment);
        commentService.addComment(principal, newComment);
        return "redirect:/comments";

    }

    @DeleteMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") int id) {
        commentService.deleteComment(id, principal);
        return "redirect:/comments";
    }

}

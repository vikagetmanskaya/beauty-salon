package com.example.diploma.controller;

import com.example.diploma.entity.Item;
import com.example.diploma.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/services")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping()
    public String serviceList(Model model) {
        List<Item> itemList = itemService.getAll();
        model.addAttribute("services", itemList);
        return "services";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("service", itemService.getById(id));
        return "service";
    }

    @GetMapping("/new")
    public String newService(@ModelAttribute("service") Item item) {
        return "new_service";

    }

    @PostMapping()
    public String serviceSave(@Valid @ModelAttribute("service") Item item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_service";
        } else {
            itemService.saveItem(item);
            return "redirect:/services";
        }

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        itemService.deleteItem(id);
        return "redirect:/services";
    }

}

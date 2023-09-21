package com.example.diploma.controller;


import com.example.diploma.entity.Record;
import com.example.diploma.service.CartService;
import com.example.diploma.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/records")
public class RecordController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private CartService cartService;

    @GetMapping()
    public String recordList(@RequestParam(name = "master", required = false) String master,
                             @RequestParam(name = "recordingDate", required = false)
                             String recordingDate,
                             @RequestParam(name = "service", required = false)
                             String service,
                             Model model) {
        List<Record> records = recordService.getAll(master, recordingDate, service);
        model.addAttribute("recordList", records);
        return "records";
    }

    @GetMapping("/new")
    public String newService(@ModelAttribute("record") Record record) {
        return "new_record";

    }

    @PostMapping()
    public String recordSave(@Valid @ModelAttribute("record") Record record, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_record";
        } else {
            recordService.saveRecord(record);
            return "redirect:/records";
        }

    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        recordService.deleteRecord(id);
        return "redirect:/records";
    }

    @PostMapping("/{id}")
    public String addRecordToCart(Principal principal, @PathVariable int id) {
        cartService.addToCart(principal, id);
        return "redirect:/records";

    }


}

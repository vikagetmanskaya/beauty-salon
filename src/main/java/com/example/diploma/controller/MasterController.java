package com.example.diploma.controller;

import com.example.diploma.entity.Master;
import com.example.diploma.repository.MasterRepository;
import com.example.diploma.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/masters")
public class MasterController {
    @Autowired
    private MasterService masterService;
    @Autowired
    private MasterRepository masterRepository;

    @GetMapping()
    public String showAllMasterByService(String service,
                                         Model model) {
        List<Master> masterList = masterService.getAll(service);
        model.addAttribute("masterList", masterList);
        return "masters";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("master", masterService.getById(id));
        return "master";
    }

    @GetMapping("/new")
    public String newMaster(@ModelAttribute("master") Master master) {
        return "new_master";

    }

    @PostMapping()
    public String masterSave(@Valid @ModelAttribute("master") Master master, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_master";
        } else {
            masterService.saveMaster(master);
            return "redirect:/masters/" + master.getId();
        }

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        masterService.deleteMaster(id);
        return "redirect:/masters";
    }

    @GetMapping("/update/{id}")
    public String updateMaster(@PathVariable("id") int id, Model model) {
        Master master = masterService.getById(id);
        model.addAttribute("master", master);
        return "update_master";
    }

    @PatchMapping("/{id}")
    public String update(@Valid @ModelAttribute("master") Master master, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update_master";
        } else {
            masterService.saveMaster(master);

            return "redirect:/masters/" + master.getId();
        }
    }
}

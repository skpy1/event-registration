package com.example.event.controller;

import com.example.event.model.Participant;
import com.example.event.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ParticipantController {

    private final ParticipantService service;

    public ParticipantController(ParticipantService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/participants")
    public String list(Model model) {
        model.addAttribute("participants", service.findAll());
        return "participants/list";
    }

    @GetMapping("/participants/new")
    public String newForm(Model model) {
        model.addAttribute("participant", new Participant());
        return "participants/form";
    }

    @PostMapping("/participants")
    public String create(@Valid @ModelAttribute("participant") Participant participant,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            return "participants/form";
        }
        try {
            service.create(participant);
        } catch (RuntimeException ex) {
            model.addAttribute("globalError", ex.getMessage());
            return "participants/form";
        }
        return "participants/success";
    }

    @GetMapping("/participants/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("participant", service.findById(id));
        return "participants/edit";
    }

    @PostMapping("/participants/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("participant") Participant participant,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            return "participants/edit";
        }
        try {
            service.update(id, participant);
        } catch (RuntimeException ex) {
            model.addAttribute("globalError", ex.getMessage());
            return "participants/edit";
        }
        return "redirect:/participants";
    }

    @PostMapping("/participants/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/participants";
    }
}

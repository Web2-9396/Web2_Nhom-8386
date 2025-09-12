package com.example.bttuan3.controllers;

import com.example.bttuan3.models.Position;
import com.example.bttuan3.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/positions")
public class PositionController {

    @Autowired
    private PositionRepository positionRepo;

    @GetMapping
    public String listPositions(Model model) {
        List<Position> positions = positionRepo.findAll();
        model.addAttribute("positions", positions);
        model.addAttribute("position", new Position());
        return "positions";
    }

    @PostMapping
    public String addPosition(@ModelAttribute Position position) {
        positionRepo.save(position);
        return "redirect:/positions";
    }

    @GetMapping("/delete/{id}")
    public String deletePosition(@PathVariable Long id) {
        positionRepo.deleteById(id);
        return "redirect:/positions";
    }
}

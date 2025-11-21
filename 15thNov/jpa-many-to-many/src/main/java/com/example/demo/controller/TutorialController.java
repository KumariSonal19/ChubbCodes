package com.example.demo.controller;

import com.example.demo.model.Tag;
import com.example.demo.model.Tutorial;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.TutorialRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class TutorialController {

    private final TutorialRepository tutorialRepository;
    private final TagRepository tagRepository;

    public TutorialController(TutorialRepository tutorialRepository, TagRepository tagRepository) {
        this.tutorialRepository = tutorialRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping("/tutorials")
    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    @GetMapping("/tags")
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        String description = (String) body.get("description");
        boolean published = Boolean.TRUE.equals(body.get("published"));

        Tutorial tutorial = new Tutorial(title, description, published);

        List<String> tagNames = (List<String>) body.get("tags");
        if (tagNames != null) {
            for (String name : tagNames) {
                Tag tag = tagRepository.findByName(name);
                if (tag == null) {
                    tag = new Tag(name);
                }
                tutorial.addTag(tag);
            }
        }

        Tutorial saved = tutorialRepository.save(tutorial);
        return ResponseEntity.ok(saved);
    }
}

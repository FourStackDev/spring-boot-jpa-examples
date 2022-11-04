package org.manjunath.voterapi.controller;

import org.manjunath.voterapi.model.Voter;
import org.manjunath.voterapi.service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VoterController {

    @Autowired
    private VoterService service;

    @GetMapping("/voter")
    public List<Voter> getAllVoters() {
        return service.getAllVoters();
    }

    @GetMapping("/voter/by-first-name/{firstName}")
    public List<Voter> getAllVotersByFirstName(@PathVariable("firstName") String firstName) {
        return service.getAllVotersByFirstName(firstName);
    }

    @PostMapping("/voter")
    public ResponseEntity<Voter> createVoter(@RequestBody Voter voter) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createVoter(voter));
    }
}

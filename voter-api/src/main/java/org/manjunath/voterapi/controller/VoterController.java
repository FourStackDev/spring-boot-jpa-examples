package org.manjunath.voterapi.controller;

import org.manjunath.voterapi.codetype.GenderType;
import org.manjunath.voterapi.model.Voter;
import org.manjunath.voterapi.service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/voter/page-by-firstname/{firstname}")
    public Page<Voter> getVotersByFirstName(@PathVariable("firstname") String firstName,
                                            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return service.getVotersPageByFirstName(firstName, pageNum, pageSize);
    }

    @GetMapping("/voter/by-last-name/{lastname}")
    public List<Voter> getAllVotersByLastName(@PathVariable("lastname") String lastName) {
        return service.getAllVotersByLastName(lastName);
    }

    @GetMapping("/voter/page-by-lastname/{lastname}")
    public Page<Voter> getVoterPageByLastName(@PathVariable("lastname") String lastName,
                                              @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return service.getVotersPageByLastName(lastName, pageNum, pageSize);
    }

    @GetMapping("/voter/page-by-gender/{gender}")
    public Page<Voter> getVoterPageByGender(@PathVariable("gender") String gender,
                                            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return service.getVotersPageByGender(GenderType.valueOf(gender), pageNum, pageSize);
    }

    @PostMapping("/voter")
    public ResponseEntity<Voter> createVoter(@RequestBody Voter voter) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createVoter(voter));
    }

    @DeleteMapping("/voter/{voter-id}")
    public ResponseEntity<String> deleteVoterByVoterId(@PathVariable("voter-id") String voterId) {
        service.deleteVoterById(voterId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Entity deleted successfully");
    }
}

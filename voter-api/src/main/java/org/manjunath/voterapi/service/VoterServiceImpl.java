package org.manjunath.voterapi.service;

import org.manjunath.voterapi.dao.VoterRepository;
import org.manjunath.voterapi.model.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class VoterServiceImpl implements VoterService {

    @Autowired
    private VoterRepository voterRepository;

    @Override
    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    @Override
    public Voter createVoter(Voter voter) {
        if (Objects.isNull(voter.getId())) {
            generateVoterId(voter);
        }
        return voterRepository.save(voter);
    }

    @Override
    public List<Voter> getAllVotersByFirstName(String firstName) {
        return voterRepository.findByFirstNameIgnoreCase(firstName);
    }

    private void generateVoterId(Voter voter) {
        voter.setId(UUID.randomUUID().toString());
    }
}

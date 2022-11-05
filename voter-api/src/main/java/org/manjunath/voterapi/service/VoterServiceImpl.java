package org.manjunath.voterapi.service;

import org.manjunath.voterapi.dao.VoterRepository;
import org.manjunath.voterapi.model.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        voter.getAddress().setVoter(voter);
        return voterRepository.save(voter);
    }

    @Override
    public List<Voter> getAllVotersByFirstName(String firstName) {
        return voterRepository.findByFirstNameIgnoreCase(firstName);
    }

    @Override
    public Page<Voter> getVotersPageByFirstName(String firstName, int pageNum, int pageSize) {
        return voterRepository.findByFirstNameIgnoreCase(firstName, getPageRequest(pageNum, pageSize));
    }

    private PageRequest getPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum, pageSize);
    }

    private void generateVoterId(Voter voter) {
        voter.setId(UUID.randomUUID().toString());
    }
}

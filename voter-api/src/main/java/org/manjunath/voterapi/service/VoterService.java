package org.manjunath.voterapi.service;

import org.manjunath.voterapi.model.Voter;

import java.util.List;

public interface VoterService {

    List<Voter> getAllVoters();

    Voter createVoter(Voter voter);

    List<Voter> getAllVotersByFirstName(String firstName);
}

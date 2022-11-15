package org.manjunath.voterapi.service;

import org.manjunath.voterapi.codetype.GenderType;
import org.manjunath.voterapi.model.Voter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VoterService {

    List<Voter> getAllVoters();

    Voter createVoter(Voter voter);

    List<Voter> getAllVotersByFirstName(String firstName);

    Page<Voter> getVotersPageByFirstName(String firstName, int pageNum, int pageSize);

    List<Voter> getAllVotersByLastName(String lastName);

    Page<Voter> getVotersPageByLastName(String lastName, int pageNum, int pageSize);

    Page<Voter> getVotersPageByGender(GenderType gender, int pageNum, int pageSize);
}

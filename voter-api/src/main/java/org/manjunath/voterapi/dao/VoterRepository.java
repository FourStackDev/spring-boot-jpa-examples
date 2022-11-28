package org.manjunath.voterapi.dao;

import org.manjunath.voterapi.codetype.GenderType;
import org.manjunath.voterapi.model.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoterRepository extends JpaRepository<Voter, String> {

    List<Voter> findByFirstNameIgnoreCase(String firstName);

    Page<Voter> findByFirstNameIgnoreCase(String firstName, Pageable page);

    List<Voter> findByLastNameIgnoreCase(String lastName);

    Page<Voter> findByLastNameIgnoreCase(String lastName, Pageable page);

    Page<Voter> findByGender(GenderType gender, Pageable page);

    Page<Voter> findByFirstNameIgnoreCaseAndAddressState(String firstName, String state, Pageable page);
}

package org.manjunath.voterapi.dao;

import org.manjunath.voterapi.codetype.GenderType;
import org.manjunath.voterapi.model.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoterRepository extends JpaRepository<Voter, String> {

    List<Voter> findByFirstName(String firstName);

    Page<Voter> findByFirstName(String firstName, Pageable page);

    List<Voter> findByLastName(String lastName);

    Page<Voter> findByLastName(String lastName, Pageable page);

    Page<Voter> findByGender(GenderType gender, Pageable page);
}

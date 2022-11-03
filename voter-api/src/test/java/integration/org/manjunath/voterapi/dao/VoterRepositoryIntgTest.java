package org.manjunath.voterapi.dao;

import org.junit.jupiter.api.*;
import org.manjunath.voterapi.codetype.GenderType;
import org.manjunath.voterapi.model.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import util.EntityGenerator;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class VoterRepositoryIntgTest {

    @Autowired
    VoterRepository voterRepository;

    @BeforeEach
    public void setUp() {
        List<Voter> voters = EntityGenerator.getVoters();
        voterRepository.saveAll(voters);
    }

    @AfterEach
    public void tearDown() {
        voterRepository.deleteAll();
    }


    @Test
    @DisplayName("VoterRepositoryIntgTest: Find All Voters")
    public void testFindAll() {
        List<Voter> voterList = voterRepository.findAll();
        Assertions.assertEquals(5, voterList.size());
    }

    @Test
    @DisplayName("VoterRepositoryIntgTest: Find Voter by Id")
    public void testFindById() {
        String id = "VOTE-7752378";
        Optional<Voter> optionalVoter = voterRepository.findById(id);

        Voter voter = optionalVoter.isPresent() ? optionalVoter.get() : null;
        assert voter != null;

        Assertions.assertEquals("Manjunath", voter.getFirstName());
        Assertions.assertEquals(GenderType.MALE, voter.getGender());
    }

    @Test
    @DisplayName("VoterRepositoryIntgTest: Find Voters by FirstName")
    public void testFindByFirstNameIgnoreCase() {
        var firstName = "Manjunath";
        List<Voter> voterList = voterRepository.findByFirstNameIgnoreCase(firstName);

        Assertions.assertEquals(1, voterList.size());
    }

    @Test
    @DisplayName("VoterRepositoryIntgTest: Find Voters Page by FirstName")
    public void testFindByFirstNameIgnoreCasePagination() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        var firstName = "Sunil";
        Page<Voter> voterPage = voterRepository.findByFirstNameIgnoreCase(firstName, pageRequest);
        List<Voter> content = voterPage.getContent();

        Assertions.assertEquals(1, content.size());
    }
}

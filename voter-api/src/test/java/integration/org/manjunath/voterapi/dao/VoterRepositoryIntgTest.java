package org.manjunath.voterapi.dao;

import org.junit.jupiter.api.*;
import org.manjunath.voterapi.model.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import util.EntityGenerator;

import java.util.List;

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
}

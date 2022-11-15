package org.manjunath.voterapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manjunath.voterapi.dao.VoterRepository;
import org.manjunath.voterapi.model.Voter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import util.EntityGenerator;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class VoterServiceTest {

    @Mock
    private VoterRepository repository;

    @InjectMocks
    private VoterServiceImpl service;

    @Test
    @DisplayName("VoterServiceTest: Test to get All Voters")
    public void testGetAllVoters() {
        List<Voter> voters = EntityGenerator.getVoters();

        // mock the repository layer
        Mockito.when(repository.findAll())
                .thenReturn(voters);

        // invoke the target method and verify the results
        List<Voter> voterList = service.getAllVoters();
        Assertions.assertEquals(voters.size(), voterList.size());
        Assertions.assertIterableEquals(voters, voterList);

        // verify the method has been called or not.
        Mockito.verify(repository).findAll();
    }

    @Test
    @DisplayName("VoterServiceTest: Test to get Voter list by firstname")
    public void testGetAllVotersByFirstName() {
        String firstName = "Vinay";
        List<Voter> voters = EntityGenerator.getVoters().stream()
                .filter(voter -> voter.getFirstName().equals(firstName))
                .collect(Collectors.toList());

        // mock the repository layer
        Mockito.when(repository.findByFirstNameIgnoreCase(Mockito.anyString()))
                .thenReturn(voters);

        // invoke the target method and verify the result
        List<Voter> voterList = service.getAllVotersByFirstName(firstName);
        Assertions.assertEquals(voters.size(), voterList.size());
        Assertions.assertIterableEquals(voters, voterList);

        // verify the method has been called or not
        Mockito. verify(repository).findByFirstNameIgnoreCase(Mockito.anyString());
    }
}

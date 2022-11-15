package org.manjunath.voterapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manjunath.voterapi.codetype.GenderType;
import org.manjunath.voterapi.dao.VoterRepository;
import org.manjunath.voterapi.model.Voter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        Mockito.verify(repository).findByFirstNameIgnoreCase(Mockito.anyString());
    }

    @Test
    @DisplayName("VoterServiceTest: Test to get Voter Page by firstname")
    public void testGetVotersPageByFirstName() {
        String firstName = "Sunil";
        List<Voter> voters = EntityGenerator.getVoters().stream()
                .filter(voter -> voter.getFirstName().equals(firstName))
                .collect(Collectors.toList());

        Page<Voter> page = new PageImpl<>(voters);

        // mock the repository layer
        Mockito.when(repository.findByFirstNameIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(page);

        // invoke the target method and verify the result
        Page<Voter> voterPage = service.getVotersPageByFirstName(firstName, 0, 10);
        Assertions.assertEquals(voters.size(), voterPage.getContent().size());
        Assertions.assertIterableEquals(voters, voterPage.getContent());

        //verify the method has been called or not
        Mockito.verify(repository, Mockito.times(1))
                .findByFirstNameIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("VoterServiceTest: Test to get Voters by lastname")
    public void testGetAllVotersByLastName() {
        String lastname = "Bhuyar";
        List<Voter> voters = EntityGenerator.getVoters().stream()
                .filter(voter -> voter.getLastName().equals(lastname))
                .collect(Collectors.toList());

        // mock the repository layer
        Mockito.when(repository.findByLastNameIgnoreCase(Mockito.anyString()))
                .thenReturn(voters);

        // invoke the target method and verify the result
        List<Voter> voterList = service.getAllVotersByLastName(lastname);
        Assertions.assertEquals(voters.size(), voterList.size());
        Assertions.assertIterableEquals(voters, voterList);

        //verify the method has been called or not
        Mockito.verify(repository).findByLastNameIgnoreCase(Mockito.anyString());
    }

    @Test
    @DisplayName("VoterServiceTest: Test to get Voter page by lastname")
    public void testGetVotersPageByLastName() {
        String lastname = "HM";
        List<Voter> voters = EntityGenerator.getVoters().stream()
                .filter(voter -> voter.getLastName().equals(lastname))
                .collect(Collectors.toList());
        Page<Voter> page = new PageImpl<>(voters);

        // mock the repository layer
        Mockito.when(repository.findByLastNameIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(page);

        // invoke the target method and verify the result
        Page<Voter> voterPage = service.getVotersPageByLastName(lastname, 0, 10);
        Assertions.assertEquals(voters.size(), voterPage.getContent().size());
        Assertions.assertIterableEquals(voters, voterPage.getContent());

        // verify the method has been called or not
        Mockito.verify(repository, Mockito.times(1))
                .findByLastNameIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("VoterServiceTest: Test to get Voter page by Gender")
    public void testGetVotersPageByGender() {
        GenderType gender = GenderType.FEMALE;
        List<Voter> voters = EntityGenerator.getVoters().stream()
                .filter(voter -> voter.getGender().equals(gender))
                .collect(Collectors.toList());
        Page<Voter> page = new PageImpl<>(voters);

        // mock the repository layer
        Mockito.when(repository.findByGender(Mockito.any(GenderType.class), Mockito.any(Pageable.class)))
                .thenReturn(page);

        // invoke the target method and verify the result
        Page<Voter> votersPage = service.getVotersPageByGender(gender, 0, 10);
        Assertions.assertEquals(voters.size(), votersPage.getContent().size());
        Assertions.assertIterableEquals(voters, votersPage.getContent());

        // verify the method has been called or not
        Mockito.verify(repository, Mockito.times(1))
                .findByGender(Mockito.any(GenderType.class), Mockito.any(Pageable.class));

    }
}

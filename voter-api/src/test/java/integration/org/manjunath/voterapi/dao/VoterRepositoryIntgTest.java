package org.manjunath.voterapi.dao;

import org.junit.jupiter.api.*;
import org.manjunath.voterapi.codetype.GenderType;
import org.manjunath.voterapi.model.Address;
import org.manjunath.voterapi.model.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import util.EntityGenerator;

import java.time.LocalDate;
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

    @Test
    @DisplayName("VoterRepositoryIntgTest: Find Voters by Lastname")
    public void testFindByLastNameIgnoreCase() {
        var lastName = "Kumar";
        List<Voter> voters = voterRepository.findByLastNameIgnoreCase(lastName);
        Assertions.assertEquals(1, voters.size());
    }

    @Test
    @DisplayName("VoterRepositoryIntgTest: Find Voters Page by Lastname")
    public void testFindByLastNameIgnoreCasePagination() {
        var lastName = "HM";
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Voter> voterPage = voterRepository.findByLastNameIgnoreCase(lastName, pageRequest);
        List<Voter> content = voterPage.getContent();

        Assertions.assertEquals(2, content.size());
    }

    @Test
    @DisplayName("VoterRepositoryIntgTest: Find Voters by Gender - MALE")
    public void testFindByGender_MALE() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Voter> voterPage = voterRepository.findByGender(GenderType.MALE, pageRequest);
        List<Voter> content = voterPage.getContent();
        Assertions.assertEquals(4, content.size());
    }

    @Test
    @DisplayName("VoterRepositoryIntgTest: Find Voters by Gender - MALE")
    public void testFindByGender_FEMALE() {
        var pageRequest = PageRequest.of(0, 10);
        var voterPage = voterRepository.findByGender(GenderType.FEMALE, pageRequest);
        List<Voter> content = voterPage.getContent();
        Assertions.assertEquals(1, content.size());
    }

    @Test
    @DisplayName("VoterRepositoryIntgTest: Save Voter")
    public void testSaveVoter() {
        Address address = getAddress("503", "9th cross", "29th main",
                "BTM 2nd Stage", "Bengaluru South", "Bengaluru",
                "560029", "Karnataka", "India");

        Voter voter = getVoter("VOTER-877645", "Kiran", "Vijay", "M",
                LocalDate.of(1993, 10, 07), GenderType.MALE, address);

        Voter savedVoter = voterRepository.save(voter);
        Assertions.assertEquals("Kiran", savedVoter.getFirstName());
        Assertions.assertNotNull(savedVoter.getAddress().getId());
    }

    private Voter getVoter(String id, String firstName, String lastName, String middleName,
                           LocalDate dob, GenderType gender, Address address) {
        return Voter.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .birthDate(dob)
                .gender(gender)
                .address(address)
                .build();
    }

    private Address getAddress(String doorNo, String addrLine1, String addrLine2, String locality,
                               String talluk, String district, String zipcode, String state,
                               String country) {
        return Address.builder()
                .doorNo(doorNo)
                .streetAddr1(addrLine1)
                .streetAddr2(addrLine2)
                .locality(locality)
                .talluk(talluk)
                .district(district)
                .zipcode(zipcode)
                .state(state)
                .country(country)
                .build();
    }
}

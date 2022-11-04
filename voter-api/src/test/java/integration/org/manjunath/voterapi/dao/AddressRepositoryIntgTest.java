package org.manjunath.voterapi.dao;

import org.junit.jupiter.api.*;
import org.manjunath.voterapi.model.Address;
import org.manjunath.voterapi.model.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import util.EntityGenerator;

import java.util.List;

@DataJpaTest
public class AddressRepositoryIntgTest {

    @Autowired
    AddressRepository addressRepository;

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
    @DisplayName("AddressRepositoryIntgTest: Find the address by Talluk name")
    public void testFindByTallukIgnoreCase() {
        var tallukName = "Sidlagatta";
        var pageRequest = PageRequest.of(0, 10);
        Page<Address> addressPage = addressRepository.findByTallukIgnoreCase(tallukName, pageRequest);
        List<Address> content = addressPage.getContent();

        Assertions.assertEquals(2, content.size());
    }

    @Test
    @DisplayName("AddressRepositoryIntgTest: Find the address by District name")
    public void testFindByDistrictIgnoreCase() {
        var districtName = "Bellary";
        var pageRequest = PageRequest.of(0, 10);
        Page<Address> addressPage = addressRepository.findByDistrictIgnoreCase(districtName, pageRequest);
        List<Address> content = addressPage.getContent();

        Assertions.assertEquals(1, content.size());
    }

    @Test
    @DisplayName("AddressRepositoryIntgTest: Find the address by State name")
    public void testFindByStateIgnoreCase() {
        var state = "Karnataka";
        var pageRequest = PageRequest.of(0, 10);
        Page<Address> addressPage = addressRepository.findByStateIgnoreCase(state, pageRequest);
        List<Address> content = addressPage.getContent();

        Assertions.assertEquals(5, content.size());
    }
}

package org.manjunath.voterapi.controller;

import org.junit.jupiter.api.*;
import org.manjunath.voterapi.dao.VoterRepository;
import org.manjunath.voterapi.model.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import util.EntityGenerator;

import java.net.URI;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoterControllerIntgTest {

    @Autowired
    TestRestTemplate restTemplate;

    WebTestClient client;

    @Autowired
    VoterRepository repository;

    @BeforeEach
    public void setUp() {
        // Add some test data into the Embedded Database.
        List<Voter> voters = EntityGenerator.getVoters();
        // BiDirectional mapping
        voters.forEach(voter -> voter.getAddress().setVoter(voter));
        repository.saveAll(voters);
    }

    @AfterEach
    public void tearDown() {
        // Erase the data after the test completion.
        repository.deleteAll();
    }

    @Test
    @DisplayName("VoterControllerIntgTest: Get All Voters")
    public void testGetAllVoters() {
        String url = "/api/v1/voter";
        ResponseEntity<Voter[]> response = restTemplate.getForEntity(url, Voter[].class);

        assert response != null;
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("VoterControllerIntgTest: Create Voter")
    public void testCreateVoter() {
        String url = "/api/v1/voter";
        Voter voter = EntityGenerator.getVoter();
        voter.setId(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Voter> entity = new HttpEntity<>(voter, headers);
        ResponseEntity<Voter> response = restTemplate.exchange(url, HttpMethod.POST, entity, Voter.class);

        assert response != null;
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody().getId());
    }

    @Test
    @DisplayName("VoterControllerIntgTest: Get Voters by First name.")
    public void testGetAllVotersByFirstName() {
        String url = "/api/v1/voter/by-first-name/{firstName}";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand("Manjunath");
        String uriString = uriComponents.toUriString();
        ResponseEntity<List<Voter>> response = restTemplate.exchange(
                uriString,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<Voter>>() {
                }
        );

        assert response != null;
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("VoterControllerIntgTest: Get Voters by LastName")
    public void testGetAllVotersByLastName() {
        String url = "/api/v1/voter/by-last-name/Bhuyar";
        ResponseEntity<Voter[]> response = restTemplate.exchange(
                URI.create(url),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Voter[].class
        );

        assert response != null;
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().length);
    }

    @Test
    @DisplayName("VoterControllerIntgTest: delete Voter by voterId")
    public void testDeleteVoterByVoterId() {
        String voterId = "VOTE-7752367";
        String url = "/api/v1/voter/" + voterId;

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                String.class
        );

        assert response != null;
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Entity deleted successfully", response.getBody());
    }

}

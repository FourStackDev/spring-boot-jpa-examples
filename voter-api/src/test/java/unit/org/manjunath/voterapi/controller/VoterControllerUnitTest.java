package org.manjunath.voterapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.manjunath.voterapi.codetype.GenderType;
import org.manjunath.voterapi.model.Voter;
import org.manjunath.voterapi.service.VoterService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import util.EntityGenerator;

import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest(VoterController.class)
@AutoConfigureMockMvc
public class VoterControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private VoterService service;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    @AfterEach
    public void tearDown() {
        mapper = null;
    }

    @Test
    @DisplayName("VoterControllerUnitTest: Test to get all Voters")
    public void testGetAllVoters() throws Exception {
        List<Voter> voters = EntityGenerator.getVoters();

        // Mock the service layer call
        Mockito.when(service.getAllVoters())
                .thenReturn(voters);

        String jsonContent = mapper.writeValueAsString(voters);

        // perform action on the endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voter"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(jsonContent))
                .andReturn();
    }

    @Test
    @DisplayName("VoterControllerUnitTest: Get all Voters by firstName")
    public void testGetAllVotersByFirstName() throws Exception {
        String firstName = "Manjunath";
        List<Voter> voters = EntityGenerator.getVoters()
                .stream().filter(voter -> voter.getFirstName().equals(firstName))
                .collect(Collectors.toList());

        // Mock the service layer
        Mockito.when(service.getAllVotersByFirstName(Mockito.anyString()))
                .thenReturn(voters);

        String jsonContent = mapper.writeValueAsString(voters);

        //perform the action on API endpoint
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/voter/by-first-name/{firstName}", firstName)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(jsonContent));
    }

    @Test
    @DisplayName("VoterControllerUnitTest: Get Voters Page by firstName")
    public void testGetVotersByFirstName() throws Exception {
        String firstName = "Manjunath";
        List<Voter> voters = EntityGenerator.getVoters()
                .stream().filter(voter -> voter.getFirstName().equals(firstName))
                .collect(Collectors.toList());

        Page<Voter> page = new PageImpl<>(voters);
        // Mock the service layer
        Mockito.when(service.getVotersPageByFirstName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(page);

        // perform the action on API endpoint
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/voter/page-by-firstname/{firstname}", firstName)
                                .param("pageNum", "0")
                                .param("pageSize", "10")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("VoterControllerUnitTest: Get all voters by lastname.")
    public void testGetAllVotersByLastName() throws Exception {
        String lastName = "Bhuyar";
        List<Voter> voters = EntityGenerator.getVoters().stream()
                .filter(voter -> voter.getLastName().equals(lastName))
                .collect(Collectors.toList());

        // Mock the service layer
        Mockito.when(service.getAllVotersByLastName(Mockito.anyString()))
                .thenReturn(voters);

        String jsonContent = mapper.writeValueAsString(voters);

        // perform the action on API endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voter/by-last-name/{lastname}", lastName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(jsonContent));
    }

    @Test
    @DisplayName("VoterControllerUnitTest: Get Voter Page by lastname")
    public void testGetVoterPageByLastName() throws Exception {
        String lastName = "Bhuyar";
        List<Voter> voters = EntityGenerator.getVoters().stream()
                .filter(voter -> voter.getLastName().equals(lastName))
                .collect(Collectors.toList());

        Page<Voter> page = new PageImpl<>(voters);

        // Mock the service layer
        Mockito.when(service.getVotersPageByLastName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(page);

        // perform the action on API endpoint
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/voter/page-by-lastname/{lastname}", lastName)
                                .param("pageNum", "0")
                                .param("pageSize", "10")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("VoterControllerUnitTest: Get Voter Page by Gender")
    public void testGetVoterPageByGender() throws Exception {
        String gender = GenderType.MALE.toString();
        List<Voter> voters = EntityGenerator.getVoters().stream()
                .filter(voter -> voter.getGender().equals(GenderType.MALE))
                .collect(Collectors.toList());

        Page<Voter> page = new PageImpl<>(voters);
        // mock the service layer
        Mockito.when(service.getVotersPageByGender(Mockito.any(GenderType.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(page);

        // perform the action on API endpoint
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/voter/page-by-gender/{gender}", gender)
                                .param("pageNum", "0")
                                .param("pageSize", "10")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("VoterControllerUnitTest: Create Voter")
    public void testCreateVoter() throws Exception {
        Voter voter = EntityGenerator.getVoter();

        // Mock the service layer
        Mockito.when(service.createVoter(Mockito.any(Voter.class)))
                .thenReturn(voter);

        // perform the action on API layer
        String jsonContent = mapper.writeValueAsString(voter);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/voter")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonContent)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(jsonContent));
    }

    @Test
    @DisplayName("VoterControllerUnitTest: Test case to delete Voter")
    public void testDeleteVoterByVoterId() throws Exception {
        String voterId = "rt679-jgk76-jgiu2-862";

        // Mock the service layer
        Mockito.doNothing().when(service)
                .deleteVoterById(Mockito.anyString());

        // perform the action on API layer
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/voter/{voter-id}", voterId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Entity deleted successfully"));

        // verify the target method call
        Mockito.verify(service, Mockito.times(1))
                .deleteVoterById(Mockito.anyString());
    }

}

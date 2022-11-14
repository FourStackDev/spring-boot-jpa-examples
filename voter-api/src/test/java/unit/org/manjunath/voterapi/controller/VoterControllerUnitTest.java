package org.manjunath.voterapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.manjunath.voterapi.model.Voter;
import org.manjunath.voterapi.service.VoterService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import util.EntityGenerator;

import java.util.List;

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


}

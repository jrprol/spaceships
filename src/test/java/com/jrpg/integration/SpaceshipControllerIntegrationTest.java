package com.jrpg.integration;

import com.jrpg.model.Spaceship;
import com.jrpg.repository.SpaceshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpaceshipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @BeforeEach
    public void setUp() {
        spaceshipRepository.deleteAll();

        Spaceship spaceship = new Spaceship();
        spaceship.setName("Enterprise");
        spaceship.setModel("Explorer");
        spaceshipRepository.save(spaceship);
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testGetAllSpaceships() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Enterprise")));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testGetSpaceshipById() throws Exception {
        Spaceship spaceship = spaceshipRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships/{id}", spaceship.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Enterprise")));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testCreateSpaceship() throws Exception {
        String newSpaceship = "{\"name\":\"Voyager\",\"type\":\"Explorer\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/spaceships")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newSpaceship))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Voyager")));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testUpdateSpaceship() throws Exception {
        Spaceship spaceship = spaceshipRepository.findAll().get(0);
        String updatedSpaceship = "{\"name\":\"Enterprise-D\",\"type\":\"Explorer\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/spaceships/{id}", spaceship.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedSpaceship))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Enterprise-D")));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testDeleteSpaceship() throws Exception {
        Spaceship spaceship = spaceshipRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/spaceships/{id}", spaceship.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships/{id}", spaceship.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

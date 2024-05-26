package com.jrpg.service;

import com.jrpg.model.Spaceship;
import com.jrpg.repository.SpaceshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpaceshipServiceTest {

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @InjectMocks
    private SpaceshipService spaceshipService;

    private Spaceship spaceship1;
    private Spaceship spaceship2;

    @BeforeEach
    public void setUp() {
        spaceship1 = new Spaceship();
        spaceship1.setId(1L);
        spaceship1.setName("X-Wing");
        spaceship1.setModel("T-65");
        spaceship1.setManufacturer("Incom Corporation");

        spaceship2 = new Spaceship();
        spaceship2.setId(2L);
        spaceship2.setName("Millennium Falcon");
        spaceship2.setModel("YT-1300");
        spaceship2.setManufacturer("Corellian Engineering Corporation");
    }

    @Test
    public void testGetAllSpaceships() {
        List<Spaceship> spaceshipList = Arrays.asList(spaceship1, spaceship2);
        Page<Spaceship> page = new PageImpl<>(spaceshipList);
        when(spaceshipRepository.findAll(any(Pageable.class))).thenReturn(page);

        List<Spaceship> result = spaceshipService.getAllSpaceships(0, 10);
        assertEquals(2, result.size());

        // Verify that the method is called at least once
        verify(spaceshipRepository, atLeastOnce()).findAll(any(Pageable.class));
    }

    @Test
    public void testGetSpaceshipById() {
        when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceship1));
        Spaceship result = spaceshipService.getSpaceshipById(1L);
        assertEquals("X-Wing", result.getName());

        // Verify that the method is called at least once
        verify(spaceshipRepository, atLeastOnce()).findById(1L);
    }

    @Test
    public void testGetSpaceshipsByName() {
        List<Spaceship> spaceshipList = Arrays.asList(spaceship1);
        when(spaceshipRepository.findByNameContaining("X-Wing")).thenReturn(spaceshipList);

        List<Spaceship> result = spaceshipService.getSpaceshipsByName("X-Wing");
        assertEquals(1, result.size());
        verify(spaceshipRepository, atLeastOnce()).findByNameContaining("X-Wing");
    }

    @Test
    public void testCreateSpaceship() {
        when(spaceshipRepository.save(any(Spaceship.class))).thenReturn(spaceship1);
        Spaceship result = spaceshipService.createSpaceship(spaceship1);
        assertEquals("X-Wing", result.getName());
        verify(spaceshipRepository, atLeastOnce()).save(spaceship1);
    }

    @Test
    public void testUpdateSpaceship() {
        when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceship1));
        when(spaceshipRepository.save(any(Spaceship.class))).thenReturn(spaceship1);

        spaceship1.setName("Updated Name");
        Spaceship result = spaceshipService.updateSpaceship(1L, spaceship1);
        assertEquals("Updated Name", result.getName());
        verify(spaceshipRepository, atLeastOnce()).save(spaceship1);
    }

    @Test
    public void testDeleteSpaceship() {
        when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceship1));
        doNothing().when(spaceshipRepository).delete(spaceship1);
        spaceshipService.deleteSpaceship(1L);
        verify(spaceshipRepository, atLeastOnce()).delete(spaceship1);
    }
}

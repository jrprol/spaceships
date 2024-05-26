package com.jrpg.service;

import com.jrpg.exception.ResourceNotFoundException;
import com.jrpg.model.Spaceship;
import com.jrpg.repository.SpaceshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Cacheable(value = "spaceships", key = "#page + '-' + #size")
    public List<Spaceship> getAllSpaceships(int page, int size) {
        return spaceshipRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Cacheable(value = "spaceship", key = "#id")
    public Spaceship getSpaceshipById(Long id) {
        return spaceshipRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Spaceship not found with id: " + id));
    }

    public List<Spaceship> getSpaceshipsByName(String name) {
        return spaceshipRepository.findByNameContaining(name);
    }

    @Caching(
        put = { @CachePut(value = "spaceship", key = "#result.id") },
        evict = { @CacheEvict(value = "spaceships", allEntries = true) }
    )
    public Spaceship createSpaceship(Spaceship spaceship) {
        return spaceshipRepository.save(spaceship);
    }

    @Caching(
        put = { @CachePut(value = "spaceship", key = "#id") },
        evict = { @CacheEvict(value = "spaceships", allEntries = true) }
    )
    public Spaceship updateSpaceship(Long id, Spaceship spaceshipDetails) {
        Spaceship spaceship = spaceshipRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Spaceship not found with id: " + id));
        spaceship.setName(spaceshipDetails.getName());
        spaceship.setModel(spaceshipDetails.getModel());
        spaceship.setManufacturer(spaceshipDetails.getManufacturer());
        return spaceshipRepository.save(spaceship);
    }

    @Caching(
        evict = {
            @CacheEvict(value = "spaceship", key = "#id"),
            @CacheEvict(value = "spaceships", allEntries = true)
        }
    )
    public void deleteSpaceship(Long id) {
        Spaceship spaceship = spaceshipRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Spaceship not found with id: " + id));
        spaceshipRepository.delete(spaceship);
    }
}

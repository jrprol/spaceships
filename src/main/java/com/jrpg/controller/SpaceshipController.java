package com.jrpg.controller;

import com.jrpg.model.Spaceship;
import com.jrpg.service.SpaceshipService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {
	
	private static final Logger LOGGER = Logger.getLogger(SpaceshipController.class.getName());

    @Autowired
    private SpaceshipService spaceshipService;

    /**
     * getAllSpaceships
     * @param page
     * @param size
     * @return
     */
    @Operation(summary = "Get all spaceships", description = "Retrieve all spaceships")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Spaceship.class)))
    @GetMapping
    public List<Spaceship> getAllSpaceships(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    	LOGGER.info("getAllSpaceships, page: " + page + ", size: " + size + ".");
        return spaceshipService.getAllSpaceships(page, size);
    }

    
    /**
     * getSpaceshipById
     * @param id
     * @return
     */
    @Operation(summary = "Get a spaceship by ID", description = "Retrieve a spaceship by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Spaceship.class)))
    @ApiResponse(responseCode = "404", description = "Spaceship not found")
    @GetMapping("/{id}")
    public ResponseEntity<Spaceship> getSpaceshipById(@PathVariable Long id) {
    	LOGGER.info("getSpaceshipById, id: " + id + ".");
        Spaceship spaceship = spaceshipService.getSpaceshipById(id);
        return ResponseEntity.ok(spaceship);
    }

    
    /**
     * getSpaceshipsByName
     * @param name
     * @return
     */
    @Operation(summary = "Get spaceships by name", description = "Retrieve spaceships filtered by its name")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Spaceship.class)))
    @GetMapping("/search")
    public List<Spaceship> getSpaceshipsByName(@RequestParam String name) {
    	LOGGER.info("getSpaceshipsByName, name: " + name + ".");
        return spaceshipService.getSpaceshipsByName(name);
    }

    
    /**
     * createSpaceship
     * @param spaceship
     * @return
     */
    @Operation(summary = "Create a new spaceship", description = "Add a new spaceship to the collection")
    @ApiResponse(responseCode = "201", description = "Successfully created",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Spaceship.class)))
    @PostMapping
    public ResponseEntity<Spaceship> createSpaceship(@RequestBody Spaceship spaceship) {
        Spaceship createdSpaceship = spaceshipService.createSpaceship(spaceship);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpaceship);
    }

    
	/**
	 * updateSpaceship
	 * @param id
	 * @param spaceshipDetails
	 * @return
	 */
    @Operation(summary = "Update a spaceship", description = "Update the details of an existing spaceship")
    @ApiResponse(responseCode = "200", description = "Successfully updated",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Spaceship.class)))
    @ApiResponse(responseCode = "404", description = "Spaceship not found")
    @PutMapping("/{id}")
    public ResponseEntity<Spaceship> updateSpaceship(@PathVariable Long id, @RequestBody Spaceship spaceshipDetails) {
        Spaceship updatedSpaceship = spaceshipService.updateSpaceship(id, spaceshipDetails);
        return ResponseEntity.ok(updatedSpaceship);
    }

    
    /**
     * deleteSpaceship
     * @param id
     * @return
     */
    @Operation(summary = "Delete a spaceship", description = "Remove a spaceship from the collection")
    @ApiResponse(responseCode = "204", description = "Successfully deleted")
    @ApiResponse(responseCode = "404", description = "Spaceship not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceship(@PathVariable Long id) {
        spaceshipService.deleteSpaceship(id);
        return ResponseEntity.notFound().build();
    }
}

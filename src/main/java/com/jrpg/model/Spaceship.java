package com.jrpg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Spaceship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String model;
    private String manufacturer;
    
    public Spaceship() {
		super();
	}    

    public Spaceship(String name, String model, String manufacturer) {
		super();
		this.name = name;
		this.model = model;
		this.manufacturer = manufacturer;
	}
    
    public Spaceship(Long id, String name, String model, String manufacturer) {
		super();
		this.id = id;
		this.name = name;
		this.model = model;
		this.manufacturer = manufacturer;
	}

	// Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}

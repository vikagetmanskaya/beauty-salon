package com.example.diploma.entity;

import com.example.diploma.util.ValidConstant;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "masters")
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "name")
    @Size(min = 4, max = 100)
    @Pattern(regexp = ValidConstant.NAME_MASTER_PATTERN)
    private String name;
    @Column(name = "experience")
    @Min(0)
    @Max(90)
    private int experience;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Item item;
    @ManyToMany(mappedBy = "masters")
    private List<Photo> photos;

    public Master() {
    }

    public Master(int id, String name, int experience, Item service) {
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.item = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}

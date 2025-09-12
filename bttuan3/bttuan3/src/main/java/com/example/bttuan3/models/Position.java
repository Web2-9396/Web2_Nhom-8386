package com.example.bttuan3.models;

import jakarta.persistence.*;

@Entity
@Table(name = "POSITION")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String positionName;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }
}

package com.softee5.mobil2team.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Station {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_datetime", nullable = false)
    private Date createdDatetime;

    @Column(name="updated_datetime", nullable = false)
    private Date updatedDatetime;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="location_x", nullable = false)
    private Double locationX;

    @Column(name="location_y", nullable = false)
    private Double locationY;

    @PrePersist
    protected void onCreate() {
        createdDatetime = new Date();
        updatedDatetime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDatetime = new Date();
    }
}
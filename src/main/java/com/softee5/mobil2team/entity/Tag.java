package com.softee5.mobil2team.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_datetime", nullable = false)
    private Date createdDatetime;

    @Column(name="updated_datetime", nullable = false)
    private Date updatedDatetime;

    @Column(name="name", nullable = false)
    private String name;
}

package com.softee5.mobil2team.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_datetime", nullable = false)
    private Date createdDatetime;

    @Column(name="updated_datetime", nullable = false)
    private Date updatedDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "content")
    private String content;

    @Column(name = "like", nullable = false)
    private Integer like;

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

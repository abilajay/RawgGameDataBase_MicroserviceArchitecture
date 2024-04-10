package com.gamediscovery.gamemicroservice.entity;

import com.gamediscovery.gamemicroservice.external.Platform;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, name = "uni_id")
    private Long uniqueId;

    private String name;

    private Short metaCritic;

    private Integer rating_top;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date released;

    private String background_image;

    private Integer playtime;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Screenshot> screenshots;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Trailer> trailers;

    private Long genreId;

    private Long publisherId;

//    @ElementCollection(targetClass = Platform.class)
    @Transient
    private List<Platform> platforms;

}

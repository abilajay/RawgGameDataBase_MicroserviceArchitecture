package com.gamediscovery.gamemicroservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trailer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String preview;
    private String res_480;
    private String full;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Game game;


}

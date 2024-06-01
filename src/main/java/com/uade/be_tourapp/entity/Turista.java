package com.uade.be_tourapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("TURISTA")
public class Turista extends Usuario {

}

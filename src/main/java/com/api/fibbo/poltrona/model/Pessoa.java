package com.api.fibbo.poltrona.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
    private Long id_pessoa;

    private String nome;

    @OneToOne
    @JoinColumn(name = "codigo_poltrona_pessoa_id", referencedColumnName = "codigopoltrona")
    private Poltrona poltrona;

}
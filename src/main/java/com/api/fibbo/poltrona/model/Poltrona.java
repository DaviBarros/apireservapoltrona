package com.api.fibbo.poltrona.model;

import com.api.fibbo.poltrona.enums.StatusPoltrona;
import com.api.fibbo.poltrona.enums.TipoPoltrona;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Poltrona {
    @Id
    private String codigoPoltrona;

    @Enumerated(EnumType.STRING)
    private TipoPoltrona tipo;

    @Enumerated(EnumType.STRING)
    private StatusPoltrona status;

}
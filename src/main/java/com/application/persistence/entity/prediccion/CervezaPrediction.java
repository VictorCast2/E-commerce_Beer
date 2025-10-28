package com.application.persistence.entity.prediccion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cerveza_prediction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CervezaPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos del dataset ARFF (ejemplo adaptado)
    private String tipoCerveza;      // Lager, IPA, Stout...
    private double precio;
    private double gradoAlcohol;
    private String paisOrigen;
    private String marca;
    private double volumen;          // en ml o L
    private int stock;

    // Resultado de la predicción
    private String categoriaPredicha; // Ej: “Alta Demanda”, “Baja Demanda”
}
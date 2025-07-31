package com.application.rol.entity;

import com.application.Enum.ERol;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Long rolId;

    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    private ERol eRol;
}

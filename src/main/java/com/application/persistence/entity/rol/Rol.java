package com.application.persistence.entity.rol;

import com.application.persistence.entity.rol.enums.ERol;
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
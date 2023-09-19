package com.abuldovi.backauthproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    private String roleName;
    private String roleTitle;
    private String roleDescription;
}

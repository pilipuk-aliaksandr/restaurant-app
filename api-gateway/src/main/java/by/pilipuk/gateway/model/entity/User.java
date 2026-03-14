package by.pilipuk.gateway.model.entity;

import by.pilipuk.commonCore.model.entity.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", schema = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private UserRole userRole;
}
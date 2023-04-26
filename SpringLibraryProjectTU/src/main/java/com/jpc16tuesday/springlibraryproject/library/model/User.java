package com.jpc16tuesday.springlibraryproject.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(name = "uniqueEmail", columnNames = "email"),
                @UniqueConstraint(name = "uniqueLogin", columnNames = "login")
        })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "users_seq", allocationSize = 1)
public class User extends GenericModel {

        @Column(name = "login", nullable = false)
        private String login;

        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "email", nullable = false)
        private String email;

        @Column(name = "birth_date", nullable = false)
        private LocalDate birthDate;

        @Column(name = "first_name", nullable = false)
        private String firstName;

        @Column(name = "last_name", nullable = false)
        private String lastName;

        @Column(name = "middle_name", nullable = false)
        private String middleName;

        @Column(name = "phone", nullable = false)
        private String phone;

        @Column(name = "address", nullable = false)
        private String address;

        @ManyToOne
        @JoinColumn(name = "role_id", nullable = false,
                    foreignKey = @ForeignKey(name = "FK_USERS_ROLES"))
        private Role role;

        @OneToMany(mappedBy = "user")
        private List<BookRentInfo> bookRentInfos;
}

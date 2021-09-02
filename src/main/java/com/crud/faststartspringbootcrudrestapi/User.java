package com.crud.faststartspringbootcrudrestapi;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @SequenceGenerator(name = "seq_generator", sequenceName = "seq_gen", initialValue = 1)
    @GeneratedValue(generator = "seq_generator")
    private Long id;

    @Column
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String country;
}

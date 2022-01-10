package com.feliopolis.bookskeeper.roles;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.*;

@Entity(name = "roles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RolesEnum role;

}

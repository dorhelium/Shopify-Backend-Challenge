package Doreen.shopifybackendchallenge.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="users")
public class User {
    @Id
    String username;
    String password;

    @OneToOne
    ImageStore imageStore;
}

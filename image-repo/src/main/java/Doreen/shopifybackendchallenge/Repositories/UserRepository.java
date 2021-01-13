package Doreen.shopifybackendchallenge.Repositories;

import Doreen.shopifybackendchallenge.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

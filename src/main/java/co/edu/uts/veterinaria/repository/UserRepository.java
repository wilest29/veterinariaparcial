package co.edu.uts.veterinaria.repository;

import co.edu.uts.veterinaria.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { User findByEmail(String email); }
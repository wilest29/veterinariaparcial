package co.edu.uts.veterinaria.repository;

import co.edu.uts.veterinaria.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> { }
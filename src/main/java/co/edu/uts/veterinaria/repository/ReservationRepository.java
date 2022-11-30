package co.edu.uts.veterinaria.repository;

import co.edu.uts.veterinaria.entity.Reservation;
import co.edu.uts.veterinaria.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> { List<Reservation> findReservationByUser(User user); }
package co.edu.uts.veterinaria.service.reservation;

import co.edu.uts.veterinaria.entity.Reservation;

import java.util.List;

public interface ReservationServiceInterface
{
    List<Reservation> listAll();
    Reservation get(Integer id);
    void save(Reservation reservation);
    void edit(Reservation reservation);
    void delete(Integer id);
}
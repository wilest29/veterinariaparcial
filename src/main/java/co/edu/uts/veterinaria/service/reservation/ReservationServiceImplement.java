package co.edu.uts.veterinaria.service.reservation;

import co.edu.uts.veterinaria.entity.Reservation;
import co.edu.uts.veterinaria.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImplement implements ReservationServiceInterface
{
    @Autowired
    private ReservationRepository repository;

    @Override
    public List<Reservation> listAll() {
        return repository.findAll();
    }
    @Override
    public Reservation get(Integer id) {
        return repository.findById(id).get();
    }
    @Override
    public void save(Reservation reservation) {
        repository.save(reservation);
    }
    @Override
    public void edit(Reservation reservation) {
        repository.save(reservation);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
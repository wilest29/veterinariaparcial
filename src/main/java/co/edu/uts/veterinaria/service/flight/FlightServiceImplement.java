package co.edu.uts.veterinaria.service.flight;

import co.edu.uts.veterinaria.entity.Flight;
import co.edu.uts.veterinaria.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImplement implements FlightServiceInterface
{
    @Autowired
    private FlightRepository repository;

    @Override
    public List<Flight> listAll() {
        return repository.findAll();
    }

    @Override
    public Flight get(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public void save(Flight flight) {
        repository.save(flight);
    }

    @Override
    public void edit(Flight flight) {
        repository.save(flight);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
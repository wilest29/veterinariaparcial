package co.edu.uts.veterinaria.service.flight;

import co.edu.uts.veterinaria.entity.Flight;

import java.util.List;

public interface FlightServiceInterface
{
    List<Flight> listAll();
    Flight get(Integer id);
    void save(Flight flight);
    void edit(Flight flight);
    void delete(Integer id);
}
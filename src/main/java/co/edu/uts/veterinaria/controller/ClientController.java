package co.edu.uts.veterinaria.controller;

import co.edu.uts.veterinaria.entity.Flight;
import co.edu.uts.veterinaria.entity.Reservation;
import co.edu.uts.veterinaria.entity.User;
import co.edu.uts.veterinaria.repository.ReservationRepository;
import co.edu.uts.veterinaria.repository.UserRepository;
import co.edu.uts.veterinaria.service.flight.FlightServiceImplement;
import co.edu.uts.veterinaria.service.reservation.ReservationServiceImplement;
import co.edu.uts.veterinaria.service.user.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/client")
public class ClientController
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserServiceImplement userService;
    @Autowired
    private FlightServiceImplement flightService;
    @Autowired
    private ReservationServiceImplement reservationService;

    private String validateRol(Authentication auth, HttpSession session)
    {
        if (auth != null)
        {
            String username = auth.getName();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            if (session.getAttribute("user") == null)
            {
                User user = userRepository.findByEmail(username);
                user.setPassword(null);
                session.setAttribute("user", user);
            }
            if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ASESOR")))
            {
                return "redirect:/asesor/";
            }else
            {
                return "client-index";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String index(Authentication auth, HttpSession session)
    {
        return validateRol(auth, session);
    }

    @GetMapping("/generateExcel/{id}")
    public String generateExcel(Authentication auth, HttpSession session, @PathVariable("id") Integer id, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("client-index"))
        {
            redirect = "GenerateExcel";
            User user = userService.get(id);
            model.addAttribute("reserves", reservationRepository.findReservationByUser(user));
        }
        return redirect;
    }

    @GetMapping("/generatePdf/{id}")
    public String generatePdf(Authentication auth, HttpSession session, @PathVariable("id") Integer id, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("client-index"))
        {
            redirect = "GeneratePdf";
            User user = userService.get(id);
            model.addAttribute("reserves", reservationRepository.findReservationByUser(user));
        }
        return redirect;
    }

    @GetMapping("/reserves")
    public String showReservesList(Authentication auth, HttpSession session, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("client-index"))
        {
            redirect = "client-reserve";
            User user = (User) session.getAttribute("user");
            model.addAttribute("title", "Reservaciones de " + user.getName());
            model.addAttribute("reserves", reservationRepository.findReservationByUser(user));
        }
        return redirect;
    }

    @GetMapping("/citas")
    public String showFlightsList(Authentication auth, HttpSession session, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("client-index"))
        {
            redirect = "client-cit";
            model.addAttribute("title", "Listado de vuelos disponibles");
            model.addAttribute("flights", flightService.listAll());
        }
        return redirect;
    }

    @GetMapping("/cit/reserve/{id}")
    public String showNewReserveForm(Authentication auth, HttpSession session, @PathVariable("id") Integer id, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("client-index"))
        {
            redirect = "client-reserve-form";
            Reservation reservation = new Reservation();
            User user = (User) session.getAttribute("user");
            reservation.setUser(userService.get(user.getId()));
            reservation.setFlight(flightService.get(id));
            model.addAttribute("title", "Reservar Cita No. WEAM" + id);
            model.addAttribute("reserve", reservation);
        }
        return redirect;
    }

    @PostMapping("/cit/reserve/save")
    public String saveReserve(@ModelAttribute("reserve") Reservation reservation)
    {
        Flight flight = reservation.getFlight();
        flightService.save(flight);
        reservationService.save(reservation);
        return "redirect:/client/reserves";
    }

    @GetMapping("/reserves/delete/{id}")
    public String deleteReserve(Authentication auth, HttpSession session, @PathVariable("id") Integer id)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("client-index"))
        {
            redirect = "redirect:/client/reserves";
            try {
                reservationService.delete(id);
            }catch (Exception e)
            {
                return redirect;
            }
        }
        return redirect;
    }
}
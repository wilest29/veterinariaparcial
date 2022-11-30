package co.edu.uts.veterinaria.controller;

import co.edu.uts.veterinaria.entity.Flight;
import co.edu.uts.veterinaria.entity.Reservation;
import co.edu.uts.veterinaria.entity.User;
import co.edu.uts.veterinaria.repository.UserRepository;
import co.edu.uts.veterinaria.service.flight.FlightServiceImplement;
import co.edu.uts.veterinaria.service.mail.EmailService;
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
@RequestMapping("/asesor")
public class AsesorController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

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
            if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("CLIENT")))
            {
                return "redirect:/client/";
            }else
            {
                return "asesor-index";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String index(Authentication auth, HttpSession session)
    {
        return validateRol(auth, session);
    }

    @GetMapping("/generateExcel")
    public String generateExcel(Authentication auth, HttpSession session, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            redirect = "GenerateExcel";
            model.addAttribute("reserves", reservationService.listAll());
        }
        return redirect;
    }

    @GetMapping("/generatePdf")
    public String generatePdf(Authentication auth, HttpSession session, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            redirect = "GeneratePdf";
            model.addAttribute("reserves", reservationService.listAll());
        }
        return redirect;
    }

    @GetMapping("/citas")
    public String showFlightList(Authentication auth, HttpSession session, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            redirect = "asesor-citas";
            model.addAttribute("title", "Listado de vuelos");
            model.addAttribute("flights", flightService.listAll());
        }
        return redirect;
    }

    @GetMapping("/citas/new")
    public String showNewFlightForm(Authentication auth, HttpSession session, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            redirect = "asesor-citas-form";
            model.addAttribute("title", "Agregar Vuelo");
            model.addAttribute("flight", new Flight());
        }
        return redirect;
    }

    @PostMapping("/flights/save")
    public String saveFlight(@ModelAttribute("flight") Flight flight)
    {
        flightService.save(flight);
        return "redirect:/asesor/citas";
    }

    @GetMapping("/flights/edit/{id}")
    public String showEditFlightForm(Authentication auth, HttpSession session, @PathVariable("id") Integer id, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            try
            {
                redirect = "asesor-citas-form";
                Flight flight = flightService.get(id);
                model.addAttribute("title", "Editar Vuelo No. " + id);
                model.addAttribute("flight", flight);
            }catch (Exception e)
            {
                return "redirect:/asesor/citas";
            }
        }
        return redirect;
    }

    @GetMapping("/flights/delete/{id}")
    public String deleteFlight(Authentication auth, HttpSession session, @PathVariable("id") Integer id)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            redirect = "redirect:/asesor/citas";
            try
            {
                flightService.delete(id);
            }catch (Exception e)
            {
                return redirect + "?error=true";
            }
        }
        return redirect;
    }

    @GetMapping("/reserves")
    public String showReserveList(Authentication auth, HttpSession session, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            redirect = "asesor-reserve";
            model.addAttribute("title", "Listado de reservas");
            model.addAttribute("reserves", reservationService.listAll());
        }
        return redirect;
    }

    @GetMapping("/reserves/new")
    public String showNewReserveForm(Authentication auth, HttpSession session, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            redirect = "asesor-reserve-form";
            model.addAttribute("title", "Agregar Reserva");
            model.addAttribute("reserve", new Reservation());
            model.addAttribute("users", userService.listAll());
            model.addAttribute("flights", flightService.listAll());
        }
        return redirect;
    }

    @PostMapping("/reserves/save")
    public String saveReserve(@ModelAttribute("reserve") Reservation reservation)
    {
        Flight flight = reservation.getFlight();
        flightService.save(flight);
        reservationService.save(reservation);
        return "redirect:/asesor/reserves";
    }

    @GetMapping("/reserves/edit/{id}")
    public String showEditReserveForm(Authentication auth, HttpSession session, @PathVariable("id") Integer id, Model model)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            try
            {
                redirect = "asesor-reserve-edit";
                Reservation reservation = reservationService.get(id);
                model.addAttribute("title", "Editar Reservacion AFH-" + id);
                model.addAttribute("reserve", reservation);
            }catch (Exception e)
            {
                return "redirect:/asesor/reserves";
            }
        }
        return redirect;
    }

    @GetMapping("/reserves/delete/{id}")
    public String deleteReserve(Authentication auth, HttpSession session, @PathVariable("id") Integer id)
    {
        String redirect = validateRol(auth, session);
        if (redirect.equals("asesor-index"))
        {
            redirect = "redirect:/asesor/reserves";
            try
            {
                reservationService.delete(id);
            }catch (Exception e)
            {
                return redirect;
            }
        }
        return redirect;
    }

    @GetMapping("/reserves/confirm/{id}")
    public String confirmReserve(@PathVariable("id") Integer id)
    {
        Reservation reservation = reservationService.get(id);
        User user = reservation.getUser();
        Flight flight = reservation.getFlight();
        String to = user.getEmail();
        String from = "utswebappprueba@gmail.com";
        String message = "Buen dia " + user.getName() + "\n\nTe hemos enviado este correo para que valides los datos de tu reservacion.\n\nDatos de la reserva:\n\n" + "Titular: " + user.getName() + "\nCorreo electronico: " + user.getEmail()  + "\nVuelo: " + flight.getSource() + " - " + flight.getDestiny() + "\nFecha: " + flight.getDate() + "\nHora: " + flight.getTime() + "\nMetodo de pago: " + reservation.getPayment() + "\n\nSi tienes alguna inquietud puedes comunicarte con nuestros canales de atencion.";
        try
        {
            emailService.sendMail(from, to, message);
        }catch (Exception ex)
        {
            return "redirect:/asesor/reserves?error=true";
        }
        return "redirect:/asesor/reserves?success=true";
    }
}
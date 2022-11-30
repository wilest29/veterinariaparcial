package co.edu.uts.veterinaria.controller;

import co.edu.uts.veterinaria.entity.User;
import co.edu.uts.veterinaria.service.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController
{
    @Autowired
    private CustomUserDetailsService service;

    private String redirectTo(Authentication auth, Model model) {
        if (auth != null)
        {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ASESOR")))
            {
                return "redirect:/asesor/";
            }else
            {
                return "redirect:/client/";
            }
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/login")
    public String loginForm(Authentication auth, Model model)
    {
        return redirectTo(auth, model);
    }

    @GetMapping("/register")
    public String registerForm(Authentication auth, Model model)
    {
        return redirectTo(auth, model);
    }

    @PostMapping("/register")
    public String registerProcess(@Valid @ModelAttribute User user, BindingResult result, Model model)
    {
        if (result.hasErrors())
        {
            return "redirect:/register";
        }else
        {
            model.addAttribute("user", service.save(user));
        }
        return "redirect:/login?success=true";
    }
}
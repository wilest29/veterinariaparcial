package co.edu.uts.veterinaria.service.user;

import co.edu.uts.veterinaria.entity.User;
import co.edu.uts.veterinaria.repository.UserRepository;
import co.edu.uts.veterinaria.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = repository.findByEmail(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new CustomUserDetails(user);
    }

    public User save(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }
}
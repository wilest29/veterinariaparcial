package co.edu.uts.veterinaria.service.user;

import co.edu.uts.veterinaria.entity.User;
import co.edu.uts.veterinaria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplement implements UserServiceInterface
{
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> listAll() {
        return repository.findAll();
    }
    @Override
    public User get(Integer id) {
        return repository.findById(id).get();
    }
    @Override
    public void save(User user) {
        repository.save(user);
    }
    @Override
    public void edit(User user) {
        repository.save(user);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
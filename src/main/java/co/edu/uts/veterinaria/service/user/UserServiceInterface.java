package co.edu.uts.veterinaria.service.user;

import co.edu.uts.veterinaria.entity.User;

import java.util.List;

public interface UserServiceInterface
{
    List<User> listAll();
    User get(Integer id);
    void save(User user);
    void edit(User user);
    void delete(Integer id);
}
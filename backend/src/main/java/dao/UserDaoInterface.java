package dao;

import java.util.List;
import java.util.Optional;

import model.User;

public interface UserDaoInterface {
    public List<User> getAllUsers();
    public Optional<User> getUserById(int id);
    public void save(User emp);
    public void delete(User emp);
    public void updateUserName(User emp, String name);
}
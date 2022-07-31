package dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import model.User;

public class UserDao implements UserDaoInterface {
	
    private List<User> users;
       
    public UserDao(){
    }

	@Override
	public List<User> getAllUsers() {
		
		List<User> userList = new ArrayList<>();
		userList.add( new User(1, new Timestamp(System.currentTimeMillis()), "Max"));
		userList.add( new User(2, new Timestamp(System.currentTimeMillis()), "Edmund"));
		userList.add( new User(3, new Timestamp(System.currentTimeMillis()), "Leo"));
		return users;
	}

	@Override
	public Optional<User> getUserById(int id) {
		User usr = null;
        for(User u : users) {
            if(u.getId() == id) {
            	usr = u;
                break;
            }  
        }  
        return Optional.ofNullable(usr);
	}

	@Override
	public void save(User usr) {
		users.add(usr);
	}

	@Override
	public void delete(User usr) {
		users.remove(usr);
	}

	@Override
	public void updateUserName(User usr, String name) {
        for(User u : users) {
            if(u.equals(usr)) {
                //if User exists
                u.setUsername(name);
                break;
            }
        } 
	}

}

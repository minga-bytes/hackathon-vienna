package dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import model.User;

public class UserDao implements UserDaoInterface {
	
    private List<User> users;
    

    
    public UserDao(){
    	users.add(new User(1,new Timestamp((new Date()).getTime()),"Max Mustermann",null));
    }

	@Override
	public List<User> getAllUsers() {
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

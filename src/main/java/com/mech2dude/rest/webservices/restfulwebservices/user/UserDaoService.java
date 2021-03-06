package com.mech2dude.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;
    static {
        users.add(new User(1, "Ram", new Date()));
        users.add(new User(2, "Jack", new Date()));
        users.add(new User(3, "Jill", new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId()==null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        for(User user:users){
            if(user.getId()==id){
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id){
        Iterator<User> userIterator = users.iterator();
        while(userIterator.hasNext()){
            User user = userIterator.next();
            if(user.getId() == id){
                userIterator.remove();
                return user;
            }
        }
        return null;
    }
}

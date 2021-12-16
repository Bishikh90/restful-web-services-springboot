package com.mech2dude.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAResource {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserRepository userRepository;

    // Retrieve all users
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    // retrieve user
    // Modified the return type from User to Entity model to
    // check hateoas implementation
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("id-"+ id);
        }
        EntityModel<User> userEntityModel = EntityModel.of(user.get());

        // lets create a link for all the users
        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.
                linkTo(WebMvcLinkBuilder.methodOn(this.
                        getClass()).retrieveAllUsers());

        userEntityModel.add(webMvcLinkBuilder.withRel("all-users"));

        return userEntityModel;
    }

    // input- details of the user
    // Return- CREATED & the created URI
    @PostMapping("/jpa/users")
    public ResponseEntity<Object> CreateUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        // show the status message as CREATED
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        // get the response entity
        return ResponseEntity.created(location).build();
    }

    // delete user
    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }
}

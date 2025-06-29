package com.example.user.user;


import com.example.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;


    public String createUser(UserRequest request) {
        var user = repository.save(mapper.toUser(request));
        return user.getId();
    }

    public void updateUser(UserRequest request) {
        var User = repository.findById(request.id())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with id %s not found", request.id())
                ));
        mergerUser(User,request);
        repository.save(User);
    }

    private void mergerUser(User User, UserRequest request) {
        if(StringUtils.isNotBlank(request.firstname())){
            User.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())){
            User.setLastname(request.lastname());
        }
        if(StringUtils.isNotBlank(request.email())){
            User.setEmail(request.email());
        }
        if(StringUtils.isNotBlank(request.username())){
            User.setUsername(request.username());
        }
    }

    public List<UserResponse> findAllUsers() {

        return repository.findAll()
                .stream()
                .map(mapper::fromUser)
                .collect(Collectors.toList());
    }
    //get the connected user from keycloak


    public Boolean existsUserById(String id) {
        return repository.findById(id).isPresent();
    }

    public UserResponse findUserById(String id) {
        return repository.findById(id)
                .map(mapper::fromUser)
                .orElseThrow(()-> new UserNotFoundException(String.format("no User with this id :: %s", id)));
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }
}

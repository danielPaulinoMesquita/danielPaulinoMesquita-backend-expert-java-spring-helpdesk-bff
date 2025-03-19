package br.com.daniel.helpdeskbff.service;

import br.com.daniel.helpdeskbff.client.UserFeignClient;
import br.com.userservice.commonslib.model.requests.CreateUserRequest;
import br.com.userservice.commonslib.model.requests.UpdateUserRequest;
import br.com.userservice.commonslib.model.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserFeignClient userFeignClient;

    public UserResponse findById(String id) {
        return  userFeignClient.findById(id).getBody();
    }

    public void save(CreateUserRequest createUserRequest) {
        userFeignClient.save(createUserRequest);
    }

    public UserResponse update(String id, UpdateUserRequest updateUserRequest) {
       return userFeignClient.update(id, updateUserRequest).getBody();
    }

    public List<UserResponse> findAll() {
        return userFeignClient.findAll().getBody();
    }

}

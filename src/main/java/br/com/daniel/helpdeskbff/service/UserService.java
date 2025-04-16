package br.com.daniel.helpdeskbff.service;

import br.com.daniel.helpdeskbff.client.UserFeignClient;
import br.com.userservice.commonslib.model.requests.CreateUserRequest;
import br.com.userservice.commonslib.model.requests.UpdateUserRequest;
import br.com.userservice.commonslib.model.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserFeignClient userFeignClient;

    // it's used for caching
    @Cacheable(value = "users", key = "#id")
    public UserResponse findById(String id) {
        return  userFeignClient.findById(id).getBody();
    }

    // this annotation is used for updating the cache (CacheEvict)
    @CacheEvict(value = "users", allEntries = true)
    public void save(CreateUserRequest createUserRequest) {
        userFeignClient.save(createUserRequest);
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserResponse update(String id, UpdateUserRequest updateUserRequest) {
       return userFeignClient.update(id, updateUserRequest).getBody();
    }

    @Cacheable(value = "users")
    public List<UserResponse> findAll() {
        return userFeignClient.findAll().getBody();
    }

}

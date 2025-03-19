package br.com.daniel.helpdeskbff.client;

import br.com.userservice.commonslib.model.requests.CreateUserRequest;
import br.com.userservice.commonslib.model.requests.UpdateUserRequest;
import br.com.userservice.commonslib.model.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "user-service-api",
        path = "/api/users"
)
public interface UserFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable(name = "id") final String id);

    @GetMapping
    ResponseEntity<List<UserResponse>> findAll();

    @PostMapping
    ResponseEntity<Void> save(@RequestBody final CreateUserRequest createUserRequest);

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> update(
            @PathVariable(name = "id") final String id,
            @RequestBody final UpdateUserRequest updateUserRequest
    );
}

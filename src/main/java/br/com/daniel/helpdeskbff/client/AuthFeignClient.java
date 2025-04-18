package br.com.daniel.helpdeskbff.client;

import br.com.daniel.helpdeskbff.config.FeignConfig;
import br.com.userservice.commonslib.model.exceptions.StandardError;
import br.com.userservice.commonslib.model.requests.AuthenticateRequest;
import br.com.userservice.commonslib.model.requests.CreateUserRequest;
import br.com.userservice.commonslib.model.requests.RefreshTokenRequest;
import br.com.userservice.commonslib.model.requests.UpdateUserRequest;
import br.com.userservice.commonslib.model.responses.AuthenticateResponse;
import br.com.userservice.commonslib.model.responses.RefreshTokenResponse;
import br.com.userservice.commonslib.model.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(
        name = "auth-service-api",
        path = "/api/auth",
        configuration = FeignConfig.class
)
public interface AuthFeignClient {

    @PostMapping("/login")
    ResponseEntity<AuthenticateResponse> authenticate(@RequestBody final AuthenticateRequest authenticateRequest) throws Exception;

    @PostMapping("/refresh-token")
    ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody final RefreshTokenRequest Request);
}

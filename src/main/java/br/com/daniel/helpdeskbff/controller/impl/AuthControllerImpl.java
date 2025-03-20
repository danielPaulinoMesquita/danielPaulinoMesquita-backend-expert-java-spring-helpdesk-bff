package br.com.daniel.helpdeskbff.controller.impl;

import br.com.daniel.helpdeskbff.service.AuthService;
import br.com.userservice.commonslib.model.requests.AuthenticateRequest;
import br.com.userservice.commonslib.model.requests.RefreshTokenRequest;
import br.com.userservice.commonslib.model.responses.AuthenticateResponse;
import br.com.userservice.commonslib.model.responses.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements br.com.daniel.helpdeskbff.controller.AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest authenticateRequest) throws Exception {
        return ResponseEntity.ok().body(
                authService.authenticate(authenticateRequest)
        );
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest Request) {
        return ResponseEntity.ok().body(authService.refreshToken(Request));
    }
}

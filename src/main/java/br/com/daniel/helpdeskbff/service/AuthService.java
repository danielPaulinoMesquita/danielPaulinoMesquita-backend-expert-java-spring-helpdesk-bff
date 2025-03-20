package br.com.daniel.helpdeskbff.service;

import br.com.daniel.helpdeskbff.client.AuthFeignClient;
import br.com.userservice.commonslib.model.requests.AuthenticateRequest;
import br.com.userservice.commonslib.model.requests.RefreshTokenRequest;
import br.com.userservice.commonslib.model.responses.AuthenticateResponse;
import br.com.userservice.commonslib.model.responses.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthFeignClient authFeignClient;

    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) throws Exception {
        return authFeignClient.authenticate(authenticateRequest).getBody();
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        return authFeignClient.refreshToken(request).getBody();
    }
}


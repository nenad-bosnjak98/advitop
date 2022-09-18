package com.njt.advitop.controller;

import com.njt.advitop.service.AuthenticationService;
import com.njt.advitop.service.RefreshTokenService;
import com.njt.advitop.transferobject.AuthenticationResponse;
import com.njt.advitop.transferobject.LoginRequest;
import com.njt.advitop.transferobject.RefreshTokenRequest;
import com.njt.advitop.transferobject.RegisterRequest;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authenticationService.signup(registerRequest);
        return new ResponseEntity<>("User registration successful!", OK);
    }

    @GetMapping("verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authenticationService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully!", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
       return authenticationService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authenticationService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>("User successfully logged out!", OK);
    }

}

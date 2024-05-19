package com.workspace.controller;

import com.workspace.dto.*;
import com.workspace.model.AuthResponseBody;
import com.workspace.model.User;
import com.workspace.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.workspace.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.security.Principal;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    @ResponseStatus(OK)
    public AuthResponseBody login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse jwtAuthResponse = authService.authenticateUser(loginRequest);
        if (jwtAuthResponse != null) {
            User userDetails = customUserDetailsService.loadUserByEmail(loginRequest.getEmail());
            return new AuthResponseBody(jwtAuthResponse.getAccessToken(), userDetails);
        } else {
            return null;
        }
    }

    @PostMapping("/signup")
    @ResponseStatus(OK)
    public Long register(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)   //TODO fix response null access
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal
    ) {

        String authToken = tokenHelper.getToken( request );

//        if (authToken != null && principal != null) {
//
//            // TODO check user password last update
//            String refreshedToken = tokenHelper.refreshToken(authToken);
//            int expiresIn = tokenHelper.getExpiredIn();
//
//            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
//        } else {
//            UserTokenState userTokenState = new UserTokenState();
//            return ResponseEntity.accepted().body(userTokenState);
//        }

        if (authToken != null) { //TODO rewrite after testing
            String refreshedToken = tokenHelper.refreshToken(authToken);
            int expiresIn = tokenHelper.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

//    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
//        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
//        Map<String, String> result = new HashMap<>();
//        result.put( "result", "success" );
//        return ResponseEntity.accepted().body(result);
//    }
}

package com.jwt.Controller;

import com.jwt.Services.CustomUserDetailService;
import com.jwt.helper.jwtHelper;
import com.jwt.helper.jwtUtil;
import com.jwt.model.jwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class jwtController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private jwtUtil jwtutil;

    @PostMapping("/token")
    public ResponseEntity<?> genToken(@RequestBody jwtRequest jwt) throws Exception {

        System.out.println(jwt);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwt.getUserName(), jwt.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid username and password");
        }

        UserDetails userDetails = customUserDetailService.loadUserByUsername(jwt.getUserName());

        System.out.println("user details :- "+userDetails.toString());

        String token = this.jwtutil.generateToken(userDetails);

        System.out.println("JWT token :- "+token);

        return ResponseEntity.ok(new jwtHelper(token));

    }
}

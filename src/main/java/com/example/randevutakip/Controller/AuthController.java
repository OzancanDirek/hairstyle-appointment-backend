package com.example.randevutakip.Controller;

import com.example.randevutakip.dto.LoginRequest;
import com.example.randevutakip.dto.LoginResponse;
import com.example.randevutakip.model.User;
import com.example.randevutakip.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
    {
        try
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getKullaniciAdi(),
                            loginRequest.getSifre()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByKullaniciAdi(loginRequest.getKullaniciAdi())
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

            List<String> roller = user.getRoles().stream()
                    .map(role -> role.getRolAdi())
                    .collect(Collectors.toList());

            LoginResponse response = new LoginResponse(
                    true,
                    "Giriş başarılı",
                    user.getKullaniciAdi(),
                    user.getAd(),
                    user.getSoyad(),
                    roller
            );

            return ResponseEntity.ok(response);

        }
        catch (BadCredentialsException e)
        {
            LoginResponse response = new LoginResponse(
                    false,
                    "Kullanıcı adı veya şifre hatalı",
                    null,
                    null,
                    null,
                    null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.JwtTokenProvider;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Аутентификация", description = "API аутентификации")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/token")
    @Operation(summary = "Получение токена", description = "Получение токена для аутентификации", responses = {
            @ApiResponse(responseCode = "200", description = "Токен получен успешно"),
            @ApiResponse(responseCode = "400", description = "Токен не получен")
    })

    public ResponseEntity<TokenResponseDTO> authentication(@Parameter(description = "login и password пользователя") @RequestBody AuthenticationRequestDTO authenticationRequest) {
        try {
            String login = authenticationRequest.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, authenticationRequest.getPass()));
            Optional<User> user = userService.getByEmail(login);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("Пользователь с username: " + login + " не найден");
            }
            String token = jwtTokenProvider.createToken(login, user.get().getRole().getName());
            return ResponseEntity.ok(new TokenResponseDTO(user.get().getRole().getName(), token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неверные username или password");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request) {

    }


}

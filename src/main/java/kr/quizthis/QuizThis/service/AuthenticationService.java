package kr.quizthis.QuizThis.service;

import kr.quizthis.QuizThis.config.JwtService;
import kr.quizthis.QuizThis.dto.jwtDto.*;
import kr.quizthis.QuizThis.entity.Role;
import kr.quizthis.QuizThis.entity.Token;
import kr.quizthis.QuizThis.entity.TokenType;
import kr.quizthis.QuizThis.entity.User;
import kr.quizthis.QuizThis.repository.TokenRepository;
import kr.quizthis.QuizThis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationDto request) {


        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();


        // Check unique email
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            return AuthenticationResponse.builder()
                    .error("Email Already in Use")
                    .build();
        }

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public UpdateResponse update(UpdateDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findByEmail(authentication.getName()).orElseThrow();

        if(!request.getEmail().isEmpty()){
            user.setEmail(request.getEmail());
        }
        if(!request.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if(!request.getUsername().isEmpty()){
            user.setUsername(request.getUsername());
        }
        repository.save(user);

        return UpdateResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }



    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}

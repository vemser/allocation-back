package br.com.allocation.security;

import br.com.allocation.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;


    public String getToken(UsuarioEntity usuarioEntity) {
        LocalDate hojeLD = LocalDate.now();
        Date hojeDT = Date.from(hojeLD.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expDT = Date.from(hojeLD.plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setIssuer("allocation")
                .claim(Claims.ID, usuarioEntity.getIdUsuario().toString())
                .setIssuedAt(hojeDT)
                .setExpiration(expDT)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if (token == null) {
            return null;
        }
        token = token.replace("Bearer ", "");

        Claims chaves = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return new UsernamePasswordAuthenticationToken(chaves.get(Claims.ID, String.class),
                null,
                Collections.emptyList());
    }
}

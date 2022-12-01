package br.com.allocation.security;

import br.com.allocation.dto.loginDTO.LoginDTO;
import br.com.allocation.entity.CargoEntity;
import br.com.allocation.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String CHAVE_CARGOS = "CARGOS";
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duracaotokensenha}")
    private String duracaoTokenSenha;

    public String getToken(UsuarioEntity usuarioEntity) {
        LocalDate hojeLD = LocalDate.now();
        Date hojeDT = Date.from(hojeLD.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expDT = Date.from(hojeLD.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<String> cargoUsuario = usuarioEntity.getCargos().stream()
                .map(CargoEntity::getAuthority)
                .toList();

        return Jwts.builder()
                .setIssuer("allocation")
                .claim(Claims.ID, usuarioEntity.getIdUsuario().toString())
                .claim(CHAVE_CARGOS, cargoUsuario)
                .setIssuedAt(hojeDT)
                .setExpiration(expDT)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getTokenRecuperarSenha(UsuarioEntity usuarioEntity) {

        Date dataAgora = new Date();
        Date duracaoToken = new Date(dataAgora.getTime() + Long.parseLong(duracaoTokenSenha));

        List<String> cargoUsuario = usuarioEntity.getCargos().stream()
                .map(CargoEntity::getAuthority)
                .toList();

        return Jwts.builder()
                .setIssuer("allocation")
                .claim(Claims.ID, usuarioEntity.getIdUsuario().toString())
                .claim(CHAVE_CARGOS, cargoUsuario)
                .setIssuedAt(dataAgora)
                .setExpiration(duracaoToken)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if(token == null) {
            return null;
        }

        Claims corpo = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();


        String userId = corpo.get(Claims.ID, String.class);
        List<String> cargos = corpo.get(CHAVE_CARGOS, List.class);

        List<SimpleGrantedAuthority> listCargos = cargos.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(userId, null, listCargos);
    }

    public String autenticarAcesso(LoginDTO loginDTO, AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Object principal = authentication.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;
        return getToken(usuarioEntity);
    }
}

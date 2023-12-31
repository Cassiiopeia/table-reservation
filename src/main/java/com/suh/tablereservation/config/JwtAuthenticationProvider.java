package com.suh.tablereservation.config;

import com.suh.tablereservation.domain.common.UserType;
import com.suh.tablereservation.domain.common.UserDto;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import com.suh.tablereservation.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Objects;

public class JwtAuthenticationProvider {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.tokenValidTime}")
    private long tokenValidTime;

    public String createToken(String userPk, Long id, UserType userType){
        Claims claims = Jwts.claims()
                //.setSubject(Aes256Util.encrypt(userPk))
                .setSubject(userPk)
                .setId(id.toString());
        claims.put("roles",userType);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void validateToken(String jwtToken){
        try{
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            if( claimsJws.getBody().getExpiration().after(new Date())){
                throw new CustomException(ErrorCode.EXPIRED_TOKEN);
            }
        } catch ( Exception e){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public UserDto getUserDto(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        UserType userType =
                UserType.valueOf(claims.get("roles", String.class));
        return new UserDto(
                //Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(claims.getId()))),
                Long.valueOf(claims.getId()),
                claims.getSubject(),
                userType);
    }
}

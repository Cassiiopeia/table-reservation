package com.suh.tablereservation.config;

import com.suh.tablereservation.domain.common.UserType;
import com.suh.tablereservation.domain.common.UserVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtAuthenticationProvider {
    private final String secretKey = "secretKey";
    private final long tokenValidTime = 1000L * 60 * 60 * 24;

    public String createToken(String userPk, Long id, UserType userType){
        Claims claims = Jwts.claims()
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

    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            return claimsJws.getBody().getExpiration().after(new Date());
        } catch ( Exception e){
            return false;
        }
    }

    public UserVo getUserVo (String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return new UserVo(Long.valueOf(claims.getId()), claims.getSubject());
    }

}

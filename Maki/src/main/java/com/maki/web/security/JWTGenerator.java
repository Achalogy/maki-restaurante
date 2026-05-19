package com.maki.web.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTGenerator {

  public static final Long EXPIRATION_TIME = 7000000L;
  private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + EXPIRATION_TIME);

    String token = Jwts.builder().setSubject(username)
        .setIssuedAt(currentDate)
        .setExpiration(expireDate)
        .signWith(key)
        .compact();

    return token;
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}

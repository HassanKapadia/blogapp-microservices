package com.hancy.blogapp.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

  private final Key signingKey;
  private final long expirationMillis;

  private final String JWT_EMAIL = "email";

  public JwtUtil(String secret, long expirationMillis) {
    this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    this.expirationMillis = expirationMillis;
  }

  // Token generation
  public String generateToken(Long userId, String email) {
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .claim(JWT_EMAIL, email)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
        .signWith(signingKey, SignatureAlgorithm.HS256)
        .compact();
  }

  // Token validation
  public boolean validateToken(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException ex) {
      return false;
    }
  }

  // Claim user id extraction
  public Long extractUserId(String token) {
    Claims claims = parseClaims(token);
    return Long.parseLong(claims.getSubject());
  }

  // Claim email extraction
  public String extractEmail(String token) {
    Claims claims = parseClaims(token);
    return claims.get(JWT_EMAIL, String.class);
  }

  private Claims parseClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
  }
}

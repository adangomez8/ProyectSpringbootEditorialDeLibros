package com.example.microservicioeditorial.security.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "13216546548944896457";
    public String getToken(UserDetails user) {

        return getToken(new HashMap<>(), user);
    }

    private String getToken(HashMap<String,Object> extraClains, UserDetails user) {

        return Jwts
                .builder()
                .setClaims(extraClains)
                .setSubject(user.getUsername())
                //fecha que se crea
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //fecha expiracion
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                //firma
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                //crea el el objeto y lo serializa
                .compact();
    }

    private Key getKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256)).build()
                .parseClaimsJws(token).getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimResolver){

        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}

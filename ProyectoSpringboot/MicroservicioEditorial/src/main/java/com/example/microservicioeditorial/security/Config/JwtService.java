package com.example.microservicioeditorial.security.Config;

import com.example.microservicioeditorial.security.User.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

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
                .signWith(getKey(), SignatureAlgorithm.HS256)
                //crea el el objeto y lo serializa
                .compact();
    }

    private Key getKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

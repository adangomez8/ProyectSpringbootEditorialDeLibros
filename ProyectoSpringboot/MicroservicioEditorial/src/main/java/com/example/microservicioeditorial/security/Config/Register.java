package com.example.microservicioeditorial.security.Config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Register {

    String username;
    String password;
    String firstname;
    String lastname;
    String country;
}

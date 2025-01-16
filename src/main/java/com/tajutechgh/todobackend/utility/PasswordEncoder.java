package com.tajutechgh.todobackend.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    String password = "password";

    public static String encode(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

   public static void main(String[] args) {

       System.out.println(encode("password"));
   }
}

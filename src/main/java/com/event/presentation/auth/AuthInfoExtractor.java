package com.event.presentation.auth;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AuthInfoExtractor {

    public static String extractor(final HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorizationHeader))
            throw new IllegalArgumentException();
        return authorizationHeader;
    }
}

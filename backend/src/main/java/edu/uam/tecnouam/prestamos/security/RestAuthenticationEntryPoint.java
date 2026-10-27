package edu.uam.tecnouam.prestamos.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uam.tecnouam.prestamos.exception.ErrorRespuesta;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;


/** 401 Unauthorized: no hay token, es invalido o expiro (el usuario no esta autenticado). */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final ObjectMapper objectMapper;


    public RestAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), ErrorRespuesta.de(
                401, "Unauthorized", "Autenticacion requerida: envie un token JWT valido en la cabecera Authorization"));
    }
}
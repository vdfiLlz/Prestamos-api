package edu.uam.tecnouam.prestamos.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uam.tecnouam.prestamos.exception.ErrorRespuesta;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;


/** 403 Forbidden: el usuario esta autenticado pero su rol no tiene permiso para la operacion. */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {


    private final ObjectMapper objectMapper;


    public RestAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), ErrorRespuesta.de(
                403, "Forbidden", "No tiene permisos para realizar esta operacion (se requiere rol ADMIN)"));
    }
}

package edu.uam.tecnouam.prestamos.config;

import edu.uam.tecnouam.prestamos.model.Rol;
import edu.uam.tecnouam.prestamos.model.Usuario;
import edu.uam.tecnouam.prestamos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Admin123*"));
            admin.setRol(Rol.ADMIN);
            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByUsername("user").isEmpty()) {
            Usuario user = new Usuario();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("User123*"));
            user.setRol(Rol.USER);
            usuarioRepository.save(user);
        }
    }
}
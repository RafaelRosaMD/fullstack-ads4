package com.senac.full.presentation;


import com.senac.full.domain.entities.Usuario;
import com.senac.full.domain.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
@Tag(name = "Controlador de Usuário", description = "Aqui é para controlar e pá")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/{id}")
    public ResponseEntity<Usuario> consultaPorId(@PathVariable Long id){
        var usuario = usuarioRepository.findById(id)
                .orElse(null);

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);

    }

    @GetMapping
    @Operation(summary = "usuarios", description = "")
    public ResponseEntity<?> consultarTodos(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> salvarUsuario(@RequestBody Usuario usuario){

        try {
            var usuarioResponse = usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/novo")
    public ResponseEntity<?> criarUsuario(
            @RequestBody Usuario usuario
    ){
        try{
            var UsuarioResponse = usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuarioRepository);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

}

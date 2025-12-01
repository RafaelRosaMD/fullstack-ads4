package com.senac.full.presentation.usuario;

import com.senac.full.application.usuario.GerenciarUsuarioUseCase;
import com.senac.full.application.usuario.command.CriarUsuarioCommand;
import com.senac.full.application.usuario.command.AtualizarUsuarioCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "CRUD de usuários do sistema")
public class UsuarioController {

    private final GerenciarUsuarioUseCase useCase;

    public UsuarioController(GerenciarUsuarioUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo usuário")
    public ResponseEntity<UsuarioResponse> criar(@RequestBody @Valid UsuarioCreateRequest request) {
        var command = new CriarUsuarioCommand(
                request.nome(),
                request.cpf(),
                request.email(),
                request.senha(),
                request.role()
        );
        var usuario = useCase.criar(command);
        var response = UsuarioMapper.toResponse(usuario);
        return ResponseEntity
                .created(URI.create("/usuarios/" + response.id()))
                .body(response);
    }

    @GetMapping
    @Operation(summary = "Listar usuários")
    public List<UsuarioResponse> listar() {
        return useCase.listar().stream()
                .map(UsuarioMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por id")
    public UsuarioResponse buscar(@PathVariable Long id) {
        var usuario = useCase.buscar(id);
        return UsuarioMapper.toResponse(usuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public UsuarioResponse atualizar(@PathVariable Long id,
                                     @RequestBody @Valid UsuarioCreateRequest request) {
        var command = new AtualizarUsuarioCommand(
                request.nome(),
                request.cpf(),
                request.email(),
                request.role()
        );
        var usuario = useCase.atualizar(id, command);
        return UsuarioMapper.toResponse(usuario);
    }
}
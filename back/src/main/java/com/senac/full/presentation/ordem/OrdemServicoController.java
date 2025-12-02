package com.senac.full.presentation.ordem;

import com.senac.full.application.ordem.GerenciarOrdemServicoUseCase;
import com.senac.full.application.ordem.command.AtualizarOrdemServicoCommand;
import com.senac.full.application.ordem.command.CriarOrdemServicoCommand;
import com.senac.full.infra.usuario.jpa.UsuarioEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ordens")
@Tag(name = "Ordens de Serviço", description = "CRUD de OS e ações de status")
public class OrdemServicoController {

    private final GerenciarOrdemServicoUseCase useCase;

    public OrdemServicoController(GerenciarOrdemServicoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @Operation(summary = "Criar OS")
    public ResponseEntity<OrdemServicoResponse> criar(
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado,
            @RequestBody @Valid OrdemServicoCreateRequest request) {

        var command = new CriarOrdemServicoCommand(
                request.cliente(),
                request.descricaoDefeito(),
                usuarioAutenticado.getId()       // 👈 AQUI: SEMPRE o usuário logado
        );

        var os = useCase.criar(command);
        var resp = OrdemServicoResponse.fromDomain(os);
        return ResponseEntity.created(URI.create("/ordens/" + resp.id())).body(resp);
    }

    @GetMapping
    @Operation(summary = "Listar OS do usuário autenticado")
    public List<OrdemServicoResponse> listar(
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado) {

        return useCase.listarDoUsuario(usuarioAutenticado.getId()).stream()
                .map(OrdemServicoResponse::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar OS do usuário por id")
    public OrdemServicoResponse buscar(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado) {

        var os = useCase.buscarDoUsuario(id, usuarioAutenticado.getId());
        return OrdemServicoResponse.fromDomain(os);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar OS do usuário")
    public OrdemServicoResponse atualizar(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado,
            @RequestBody @Valid OrdemServicoUpdateRequest request) {

        var command = new AtualizarOrdemServicoCommand(
                request.cliente(),
                request.descricaoDefeito()
        );
        var os = useCase.atualizar(id, usuarioAutenticado.getId(), command);
        return OrdemServicoResponse.fromDomain(os);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir OS do usuário")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado) {

        useCase.excluir(id, usuarioAutenticado.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/iniciar")
    @Operation(summary = "Iniciar serviço em OS do usuário")
    public OrdemServicoResponse iniciar(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado) {

        var os = useCase.iniciarServico(id, usuarioAutenticado.getId());
        return OrdemServicoResponse.fromDomain(os);
    }

    @PostMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar OS do usuário")
    public OrdemServicoResponse finalizar(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado) {

        var os = useCase.finalizar(id, usuarioAutenticado.getId());
        return OrdemServicoResponse.fromDomain(os);
    }
}
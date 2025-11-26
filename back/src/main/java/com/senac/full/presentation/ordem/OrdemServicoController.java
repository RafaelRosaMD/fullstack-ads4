package com.senac.full.presentation.ordem;

import com.senac.full.application.ordem.GerenciarOrdemServicoUseCase;
import com.senac.full.application.ordem.command.AtualizarOrdemServicoCommand;
import com.senac.full.application.ordem.command.CriarOrdemServicoCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    // CRUD
    @PostMapping
    @Operation(summary = "Criar OS (status inicial = ABERTA)")
    public ResponseEntity<OrdemServicoResponse> criar(@RequestBody @Valid OrdemServicoCreateRequest request) {
        var command = new CriarOrdemServicoCommand(
                request.cliente(),
                request.descricaoDefeito(),
                request.usuarioId()
        );
        var os = useCase.criar(command);
        var resp = OrdemServicoResponse.fromDomain(os);
        return ResponseEntity.created(URI.create("/ordens/" + resp.id())).body(resp);
    }

    @GetMapping
    @Operation(summary = "Listar todas as OS")
    public List<OrdemServicoResponse> listar() {
        return useCase.listar().stream()
                .map(OrdemServicoResponse::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar OS por id")
    public OrdemServicoResponse buscar(@PathVariable Long id) {
        var os = useCase.buscarPorId(id);
        return OrdemServicoResponse.fromDomain(os);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar OS (não permite quando FINALIZADA)")
    public OrdemServicoResponse atualizar(@PathVariable Long id,
                                          @RequestBody @Valid OrdemServicoUpdateRequest request) {
        var command = new AtualizarOrdemServicoCommand(
                request.cliente(),
                request.descricaoDefeito()
        );
        var os = useCase.atualizar(id, command);
        return OrdemServicoResponse.fromDomain(os);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir OS (bloqueia quando EM_EXECUCAO)")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // AÇÕES
    @PostMapping("/{id}/iniciar")
    @Operation(summary = "Iniciar serviço (ABERTA → EM_EXECUCAO)")
    public OrdemServicoResponse iniciar(@PathVariable Long id) {
        var os = useCase.iniciarServico(id);
        return OrdemServicoResponse.fromDomain(os);
    }

    @PostMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar serviço (EM_EXECUCAO → FINALIZADA)")
    public OrdemServicoResponse finalizar(@PathVariable Long id) {
        var os = useCase.finalizar(id);
        return OrdemServicoResponse.fromDomain(os);
    }
}
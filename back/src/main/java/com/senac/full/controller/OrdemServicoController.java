package com.senac.full.controller;

import com.senac.full.dto.ordem.*;
import com.senac.full.service.OrdemServicoService;
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

    private final OrdemServicoService service;

    public OrdemServicoController(OrdemServicoService service) {
        this.service = service;
    }

    // CRUD
    @PostMapping
    @Operation(summary = "Criar OS (status inicial = ABERTA)")
    public ResponseEntity<OrdemServicoResponseDto> criar(@RequestBody @Valid OrdemServicoCreateDto dto) {
        var resp = service.criar(dto);
        return ResponseEntity.created(URI.create("/ordens/" + resp.id())).body(resp);
    }

    @GetMapping
    @Operation(summary = "Listar todas as OS")
    public List<OrdemServicoResponseDto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar OS por id")
    public OrdemServicoResponseDto buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar OS (não permite quando FINALIZADA)")
    public OrdemServicoResponseDto atualizar(@PathVariable Long id,
                                             @RequestBody @Valid OrdemServicoUpdateDto dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir OS (bloqueia quando EM_EXECUCAO)")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // AÇÕES
    @PostMapping("/{id}/iniciar")
    @Operation(summary = "Iniciar serviço (ABERTA → EM_EXECUCAO)")
    public OrdemServicoResponseDto iniciar(@PathVariable Long id) {
        return service.iniciarServico(id);
    }

    @PostMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar serviço (EM_EXECUCAO → FINALIZADA)")
    public OrdemServicoResponseDto finalizar(@PathVariable Long id) {
        return service.finalizar(id);
    }
}
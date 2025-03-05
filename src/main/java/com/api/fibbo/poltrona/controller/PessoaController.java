package com.api.fibbo.poltrona.controller;

import com.api.fibbo.poltrona.exception.PoltronaIndisponivelException;
import com.api.fibbo.poltrona.exception.PoltronaNaoEncontradaException;
import com.api.fibbo.poltrona.model.Pessoa;
import com.api.fibbo.poltrona.services.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // Criar reserva (associar pessoa a uma poltrona)
    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody Pessoa pessoa) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.criarReserva(pessoa));
        } catch (PoltronaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PoltronaIndisponivelException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Cancelar reserva (remover associação)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        try {
            pessoaService.cancelarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/poltrona/{codigoPoltrona}")
    public ResponseEntity<Pessoa> findPessoaByPoltronaCodigo(@PathVariable String codigoPoltrona) {
        Pessoa pessoa = pessoaService.findPessoaByPoltronaCodigo(codigoPoltrona);
        if (pessoa != null) {
            return new ResponseEntity<>(pessoa, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
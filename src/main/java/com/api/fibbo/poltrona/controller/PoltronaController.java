package com.api.fibbo.poltrona.controller;

import com.api.fibbo.poltrona.exception.PoltronaIndisponivelException;
import com.api.fibbo.poltrona.exception.PoltronaNaoEncontradaException;
import com.api.fibbo.poltrona.model.Poltrona;
import com.api.fibbo.poltrona.services.PoltronaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/poltronas")
public class PoltronaController {

    private final PoltronaService poltronaService;

    public PoltronaController(PoltronaService poltronaService) {
        this.poltronaService = poltronaService;
    }

    // Listar todas as poltronas
    @GetMapping
    public ResponseEntity<List<Poltrona>> listarTodas() {
        return ResponseEntity.ok(poltronaService.listarTodasPoltronas());
    }

    // Buscar poltrona por código
    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(poltronaService.buscarPorCodigo(codigo));
        } catch (PoltronaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Reservar poltrona
    @PostMapping("/{codigo}/reservar")
    public ResponseEntity<?> reservarPoltrona(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(poltronaService.reservarPoltrona(codigo));
        } catch (PoltronaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PoltronaIndisponivelException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Liberar poltrona
    @PostMapping("/{codigo}/liberar")
    public ResponseEntity<?> liberarPoltrona(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(poltronaService.liberarPoltrona(codigo));
        } catch (PoltronaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //criar poltrona
    @PostMapping
    public ResponseEntity<?> criarPoltrona(@RequestBody Poltrona poltrona) {
        try {
            Poltrona novaPoltrona = poltronaService.criarPoltrona(poltrona);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaPoltrona);
        } catch (PoltronaIndisponivelException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
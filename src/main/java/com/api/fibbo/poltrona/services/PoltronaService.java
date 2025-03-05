package com.api.fibbo.poltrona.services;

import com.api.fibbo.poltrona.enums.StatusPoltrona;
import com.api.fibbo.poltrona.exception.PoltronaIndisponivelException;
import com.api.fibbo.poltrona.exception.PoltronaNaoEncontradaException;
import com.api.fibbo.poltrona.model.Poltrona;
import com.api.fibbo.poltrona.repository.PoltronaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PoltronaService {

    private final PoltronaRepository poltronaRepository;

    public PoltronaService(PoltronaRepository poltronaRepository) {
        this.poltronaRepository = poltronaRepository;
    }

    public List<Poltrona> listarTodasPoltronas() {
        return poltronaRepository.findAll();
    }

    public Poltrona buscarPorCodigo(String codigo) {
        return poltronaRepository.findById(codigo)
                .orElseThrow(() -> new PoltronaNaoEncontradaException("Poltrona não encontrada: " + codigo));
    }

    @Transactional
    public Poltrona reservarPoltrona(String codigo) {
        Poltrona poltrona = buscarPorCodigo(codigo);

        if (poltrona.getStatus() == StatusPoltrona.RESERVADO) {
            throw new PoltronaIndisponivelException("Poltrona já reservada: " + codigo);
        }

        if (poltrona.getStatus() == StatusPoltrona.INATIVO) {
            throw new PoltronaIndisponivelException("Poltrona inativa: " + codigo);
        }

        poltrona.setStatus(StatusPoltrona.RESERVADO);
        return poltronaRepository.save(poltrona);
    }

    @Transactional
    public Poltrona liberarPoltrona(String codigo) {
        Poltrona poltrona = buscarPorCodigo(codigo);
        poltrona.setStatus(StatusPoltrona.ATIVO);
        return poltronaRepository.save(poltrona);
    }

    @Transactional
    public Poltrona criarPoltrona(Poltrona poltrona) {
        // Valida se o código já existe (opcional)
        if (poltronaRepository.existsById(poltrona.getCodigoPoltrona())) {
            throw new PoltronaIndisponivelException("Poltrona já cadastrada: " + poltrona.getCodigoPoltrona());
        }

        return poltronaRepository.save(poltrona);
    }

}

package com.api.fibbo.poltrona.services;

import com.api.fibbo.poltrona.model.Pessoa;
import com.api.fibbo.poltrona.model.Poltrona;
import com.api.fibbo.poltrona.repository.PessoaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PoltronaService poltronaService;

    public PessoaService(PessoaRepository pessoaRepository, PoltronaService poltronaService) {
        this.pessoaRepository = pessoaRepository;
        this.poltronaService = poltronaService;
    }

    @Transactional
    public Pessoa criarReserva(Pessoa pessoa) {
        if (pessoa.getPoltrona() != null) {
            Poltrona poltronaReservada = poltronaService.reservarPoltrona(
                    pessoa.getPoltrona().getCodigoPoltrona()
            );
            pessoa.setPoltrona(poltronaReservada);
        }

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void cancelarReserva(Long pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        if (pessoa.getPoltrona() != null) {
            poltronaService.liberarPoltrona(pessoa.getPoltrona().getCodigoPoltrona());
            pessoa.setPoltrona(null);
            pessoaRepository.save(pessoa); //atualiza a pessoa sem poltrona
            pessoaRepository.delete(pessoa); // Exclui a pessoa
        }


    }

    public Pessoa findPessoaByPoltronaCodigo(String codigoPoltrona) {
        return pessoaRepository.findByPoltronaCodigoPoltrona(codigoPoltrona);
    }
}
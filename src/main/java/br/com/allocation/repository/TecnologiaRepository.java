package br.com.allocation.repository;

import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.TecnologiaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TecnologiaRepository extends JpaRepository<TecnologiaEntity,Integer> {
    Page<TecnologiaEntity> findByNomeIsLikeIgnoreCase(String nomeTecnologia, Pageable pageable);
    Set<TecnologiaEntity> findAllByNomeIn(List<String> tecnologias);

    Set<TecnologiaEntity> findAllByAlunos(AlunoEntity aluno);
    TecnologiaEntity findByNome(String nome);
}

package br.com.allocation.repository;

import br.com.allocation.entity.ReservaAlocacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaAlocacaoRepository extends JpaRepository<ReservaAlocacaoEntity, Integer> {

    @Query("SELECT obj " +
            "FROM Reserva_Alocacao obj " +
            "LEFT JOIN obj.aluno a " +
            "LEFT JOIN obj.vaga v " +
            "LEFT JOIN obj.avaliacao av " +
            "WHERE ((:nomeAluno is null or a.nome = :nomeAluno) AND (:nomeVaga is null or a.nome = :nomeVaga))")
    Page<ReservaAlocacaoEntity> findAllByFiltro(Pageable pageable, String nomeAluno, String nomeVaga);
}

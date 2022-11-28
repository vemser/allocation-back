package br.com.allocation.repository;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.enums.StatusAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AlunoRepository extends JpaRepository<AlunoEntity, Integer> {
    List<AlunoEntity> findByStatusAluno(StatusAluno statusAluno);
}

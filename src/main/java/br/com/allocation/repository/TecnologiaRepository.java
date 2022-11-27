package br.com.allocation.repository;

import br.com.allocation.entity.TecnologiaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnologiaRepository extends JpaRepository<TecnologiaEntity,Integer> {
    Page<TecnologiaEntity> findByNomeIsLikeIgnoreCase(String nomeTecnologia, Pageable pageable);
    //Page<TecnologiaEntity> findByNomeContainsIgnoreCase(String nomeTecnologia, Pageable pageable);
}

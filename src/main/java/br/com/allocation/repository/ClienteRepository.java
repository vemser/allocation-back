package br.com.allocation.repository;

import br.com.allocation.entity.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    Optional<ClienteEntity> findByEmailIgnoreCase(String email);
    Page<ClienteEntity> findAllByEmailIgnoreCase(Pageable pageable,String email);
}

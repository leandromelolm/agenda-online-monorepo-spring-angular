package com.lm.myagenda.repositories;

import com.lm.myagenda.models.Agenda;
import com.lm.myagenda.models.Professional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT a FROM Agenda a")
    Page<Agenda> findAll(PageRequest pageRequest);
}

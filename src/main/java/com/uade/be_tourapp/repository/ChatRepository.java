package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {
    List<Chat> findAllByTuristaIdOrGuiaId(Integer turistaId, Integer guiaId);
    Boolean existsByTuristaIdAndGuiaId(Integer turistaId, Integer guiaId);
}

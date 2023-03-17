package com.emanuel.mediaservice.repositories;

import com.emanuel.mediaservice.entities.AudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioRepository extends JpaRepository<AudioEntity, Long> {}

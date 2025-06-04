package com.ampmap.ampmap.repositories;

import com.ampmap.ampmap.model.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
}

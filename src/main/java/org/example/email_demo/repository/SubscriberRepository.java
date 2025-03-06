package org.example.email_demo.repository;

import org.example.email_demo.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, String> {
    boolean existsByEmail(String email);
}

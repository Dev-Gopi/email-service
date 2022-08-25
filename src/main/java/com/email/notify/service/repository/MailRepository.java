package com.email.notify.service.repository;

import com.email.notify.service.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<Email, String> {
}

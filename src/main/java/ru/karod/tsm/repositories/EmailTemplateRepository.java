package ru.karod.tsm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.karod.tsm.models.EmailTemplate;
import ru.karod.tsm.models.enums.EmailType;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String>
{
    Optional<EmailTemplate> findByEmailType(EmailType emailType);
}

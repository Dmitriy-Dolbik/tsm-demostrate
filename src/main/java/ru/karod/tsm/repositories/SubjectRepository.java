package ru.karod.tsm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.karod.tsm.models.Subject;

import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String>
{

}

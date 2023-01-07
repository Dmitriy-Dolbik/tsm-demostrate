package ru.karod.tsm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import ru.karod.tsm.models.enums.EmailType;

@Data
@Entity
@Table(name = "email_template")
public class EmailTemplate
{
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "subject")
    private String subject;
    @Column(name = "template_server_file_name")
    private String templateServerFileName;
    @Column(name = "email_type")
    @Enumerated(EnumType.STRING)
    private EmailType emailType;
}

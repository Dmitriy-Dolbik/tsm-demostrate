package ru.karod.tsm.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.Role;
import ru.karod.tsm.security.request.SignupRequest;
import ru.karod.tsm.services.SubjectService;

import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final SubjectService tsmSubjectServiceImpl;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(SignupRequest.class, User.class)
                .setPostConverter(context -> {
                    context.getDestination().setRole(Role.valueOf(context.getSource().getRole()));
                    context.getDestination().setSubjects(
                            context.getSource().getSubjectIdList().stream()
                                    .map(subjectId -> tsmSubjectServiceImpl.findSubjectById(subjectId))
                                    .collect(Collectors.toList()));
                    return context.getDestination();
                });
        return modelMapper;
    }
}

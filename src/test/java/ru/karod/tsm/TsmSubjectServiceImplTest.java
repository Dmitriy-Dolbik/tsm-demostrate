package ru.karod.tsm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ru.karod.tsm.models.Subject;
import ru.karod.tsm.models.enums.Language;
import ru.karod.tsm.repositories.SubjectRepository;
import ru.karod.tsm.services.SubjectService;
import ru.karod.tsm.services.impl.TsmSubjectServiceImpl;

import static org.mockito.ArgumentMatchers.any;

public class TsmSubjectServiceImplTest
{
    private static
    SubjectService tsmSubjectServiceImpl;
    private static SubjectRepository subjectRepository;

    @BeforeAll
    static void setup(){
        subjectRepository = Mockito.mock(SubjectRepository.class);
        tsmSubjectServiceImpl = new TsmSubjectServiceImpl(subjectRepository);

    }
    @Test
    void findAllTest(){
        //Given
        List<Subject> subjects = new ArrayList<>(List.of(
                new Subject ("ef97eb6f-3396-4b39-97f0-7eecf7510d56", Language.GERMAN),
                new Subject ("dd1783ea-4d14-4a56-b279-5abee842ae19", Language.FRENCH),
                new Subject ("234bcf9a-e814-4579-8c91-fd2fe6932c34", Language.RUSSIAN),
                new Subject ("1f8867e6-7555-4456-8c26-b7fb1fca0a34", Language.MALIAN),
                new Subject ("1358a9a8-8459-4a28-8b18-e63c6697e233", Language.JAPANESE),
                new Subject ("cdca2395-13ba-47f3-b312-0bfbd79bfe52", Language.PORTUGUESE),
                new Subject ("09dffbf5-7639-49d9-a142-34bbae4f2304", Language.ARABIC),
                new Subject ("982f6415-b05c-460a-9fc3-45b6c5005c34", Language.SPANISH),
                new Subject ("9ff47c27-5192-47ed-88b8-3f80c84efe02", Language.CHINESE)
        ));
        Mockito.when(subjectRepository.findAll()).thenReturn(subjects);

        //When
        List<Subject> subjectList =  tsmSubjectServiceImpl.findAll();

        //Then
        for (int i = 0; i < subjects.size(); i++)
        {
            Assertions.assertEquals(subjectList.get(i).getId(), subjects.get(i).getId());
            Assertions.assertEquals(subjectList.get(i).getLanguage(), subjects.get(i).getLanguage());
        }
    }
    @Test
    void findSubjectById(){
        //Given
        Optional<Subject> subject = Optional.of(new Subject("ef97eb6f-3396-4b39-97f0-7eecf7510d56", Language.GERMAN));
        Mockito.when(subjectRepository.findById(any())).thenReturn(subject);

        //When
        Subject subjectById = tsmSubjectServiceImpl.findSubjectById("ef97eb6f-3396-4b39-97f0-7eecf7510d56");
        Mockito.verify(subjectRepository).findById(any());

        //Then
        Assertions.assertEquals(Language.GERMAN, subjectById.getLanguage());
    }
}

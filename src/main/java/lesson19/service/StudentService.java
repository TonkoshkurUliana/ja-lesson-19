package lesson19.service;

import lesson19.domain.Student;
import lesson19.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student, MultipartFile file) throws IndexOutOfBoundsException, IOException {
        student.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        student.setFileType(file.getContentType());
        student.setFiledata(file.getBytes());
        return studentRepository.save(student);
    }

    public Student findById(String id) {
        return studentRepository.getReferenceById(id);
    }
}

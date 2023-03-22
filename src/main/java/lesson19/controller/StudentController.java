package lesson19.controller;

import lesson19.domain.Student;
import lesson19.dto.MultipartUploadResponse;
import lesson19.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;

@EnableWebMvc
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/")
    public ModelAndView firstPage() {
        return new ModelAndView("index");
    }

    @PostMapping("/register")
    public @ResponseBody ModelAndView uploadFile(
            @RequestParam(required = false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "lastName") String lastName,
            @RequestParam(required = false, name = "age") Integer age,
            @RequestParam("file") MultipartFile file, RedirectAttributes ra) throws IOException {
        Student student = new Student(firstName, lastName, age);
        studentService.create(student, file);

        ModelAndView modelAndView = new ModelAndView("redirect:/student.html");
        ra.addAttribute("id", student.getId());
        return modelAndView;

    }
    @GetMapping("/student.html")
    public @ResponseBody ModelAndView studentForm(@RequestParam("id") String id, ModelMap model)
            throws FileNotFoundException, UnsupportedEncodingException {
        Student student = studentService.findById(id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
                .path(student.getId()).toUriString();

        model.addAttribute("student", student);
        model.addAttribute("url", fileDownloadUri);
        return new ModelAndView("student.html");
    }
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downlaodFile(@PathVariable String fileId) throws FileNotFoundException {
        Student fileMultipart = studentService.findById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileMultipart.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileMultipart.getFileName() + "\"")
                .body(new ByteArrayResource(fileMultipart.getFiledata()));
    }

}




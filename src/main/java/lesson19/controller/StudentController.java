package lesson19.controller;

import lesson19.domain.Student;
import lesson19.service.StudentService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/register")
    public String uploadFile(
            @RequestParam(required=false, name = "firstName") String firstName,
            @RequestParam(required=false, name = "lastName") String lastName,
            @RequestParam(required=false, name = "age") Integer age,
                              @RequestParam("file") MultipartFile file, RedirectAttributes ra) throws IOException {
        Student student = new Student(firstName, lastName, age);
        studentService.create(student, file);

     return "redirect:/student?id=" + student.getId().toString();

    }

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirect() {
        return "redirect:student";
    }

    @GetMapping("/student")
    public @ResponseBody String studentForm(@RequestParam("id") String id, ModelMap model)
            throws FileNotFoundException, UnsupportedEncodingException {
      Student student = studentService.findById(id);
        byte[] fileBytes = student.getFiledata();
        byte[] fileEncodeBase64 = Base64.encodeBase64(fileBytes, true);
        String fileBase64Encoded = new String(fileEncodeBase64, "UTF-8");

//        model.addAttribute("student.firstName", student.getFirstName());
//        model.addAttribute("photo", fileBase64Encoded);

        return ""+ student.getId() + " " + student.getFirstName() + " " + student.getLastName() + " "
                + student.getAge() + " " + student.getFileName() + " ";
    }
}

//
//package lesson19.controller;
//
//        import jakarta.annotation.Resource;
//        import lesson19.domain.Student;
//        import lesson19.service.StudentService;
//        import org.apache.tomcat.util.codec.binary.Base64;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.http.ResponseEntity;
//        import org.springframework.stereotype.Controller;
//        import org.springframework.ui.ModelMap;
//        import org.springframework.web.bind.annotation.*;
//        import org.springframework.web.multipart.MultipartFile;
//        import org.springframework.web.servlet.ModelAndView;
//        import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//        import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//        import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//        import java.io.FileNotFoundException;
//        import java.io.IOException;
//        import java.io.UnsupportedEncodingException;
//
////@EnableWebMvc
//@RestController
//public class StudentController {
//
//    @Autowired
//    StudentService studentService;
//
//    @RequestMapping(value="/register", method= {RequestMethod.POST, RequestMethod.GET})
//    public   @ResponseBody ModelAndView uploadStudent(
//            @RequestParam(required=false, name = "firstName") String firstName,
//            @RequestParam(required=false, name = "lastName") String lastName,
//            @RequestParam(required=false, name = "age") Integer age,
//            @RequestParam("file") MultipartFile file, RedirectAttributes ra) throws IOException {
//        Student student = new Student(firstName, lastName, age);
//        studentService.create(student, file);
//
//        ModelAndView modelAndView= new ModelAndView("redirect:/student");
//        modelAndView.setViewName("student");
//        ra.addAttribute("id",student.getId());
//        ra.addAttribute("url",file.getName());
//
////      return "redirect:/student?id=" + student.getId().toString();
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
//    public String redirect() {
//        System.out.println("red");
//        return "redirect:student";
//    }
//
//    //    @GetMapping("/student")
////    public @ResponseBody String studentForm(@RequestParam("id") String id, ModelMap model)
////            throws FileNotFoundException, UnsupportedEncodingException {
////      Student student = studentService.findById(id);
////        System.out.println("hello");
////        byte[] fileBytes = student.getFiledata();
////        byte[] fileEncodeBase64 = Base64.encodeBase64(fileBytes, true);
////        String fileBase64Encoded = new String(fileEncodeBase64, "UTF-8");
////
////        System.out.println(id);
////        System.out.println(student);
////
////        model.addAttribute("student.firstName", student.getFirstName());
////        model.addAttribute("photo", fileBase64Encoded);
//    @RequestMapping(value="/student", method = RequestMethod.GET)
//    public ModelAndView  hiJack(
////	    		@RequestParam ModelMap model,
////	    		HttpServletRequest req
//    )
//    {
//        return new ModelAndView("student");
//    }
//}
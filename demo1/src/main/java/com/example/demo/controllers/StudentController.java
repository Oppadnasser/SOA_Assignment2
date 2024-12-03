package com.example.demo.controllers;

import com.example.demo.Bsl.StudentBsl;
import com.example.demo.models.Student;
import com.example.demo.models.searchInfo;
import com.example.demo.models.sortInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller

@RequestMapping("/")

public class StudentController {
    private StudentBsl studentBsl;

    public StudentController(StudentBsl studentBsl)
    {
        this.studentBsl = studentBsl;
    }

    @GetMapping("/")
    public String getall(ModelMap model){
        if (!model.containsAttribute("students")) {
            List<Student> students = studentBsl.getStudentTable();
            model.addAttribute("students",students);
        }
        if (!model.containsAttribute("searchInfo")) {
            searchInfo searchInfo = new searchInfo();
            model.addAttribute("searchInfo",searchInfo);
        }
        if(!model.containsAttribute("sortInfo")){
            model.addAttribute("sortInfo",new sortInfo());
        }
        return "index";
    }

    @PostMapping("/newStudent" )
    public String addStudent(@ModelAttribute Student student,ModelMap model){
        studentBsl.addStudent(student);
        model.remove("students");
        model.addAttribute("students",studentBsl.getStudentTable());
        return "redirect:/";
    }
    @GetMapping("/adding")
    public String addPage(){
        return "newStudent";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, ModelMap model){
        try {
            List<Student> students = studentBsl.getStudentTable();
            for (Student st : students) {
                if (st.getId() == id) {
                    model.addAttribute("toEdit", st);
                    return "edit";
                }
            }
        }catch (Exception e){
            return "error";

        }
        return "error";
    }
    @PostMapping("/update")
    public String update(ModelMap model , @ModelAttribute Student toEdit){
        studentBsl.updateStudent(toEdit);
        model.remove("students");
        model.addAttribute("students",studentBsl.getStudentTable());
        return "redirect:/";
    }
//    @PostMapping("/sort")
//    public String sort(ModelMap model , @RequestParam("selectedOption") String selectedOption, @RequestParam("type") Optional<String> type){
//        model.addAttribute("students",studentBsl.sort(selectedOption,type.isEmpty()));
//        return "redirect:/";
//    }
@PostMapping("/sort")
public String sort(RedirectAttributes redirectAttributes, @ModelAttribute("sortInfo") sortInfo info){
    redirectAttributes.addFlashAttribute("sortInfo",info);
    redirectAttributes.addFlashAttribute("students",studentBsl.sort(info.getSortBy(),info.isDesc()));
    return "redirect:/";
}

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id, ModelMap model){
        studentBsl.delete(id);
        model.addAttribute("students", studentBsl.getStudentTable());
        return "redirect:/";
    }

    @PostMapping("/search")
    public String search(@RequestParam("search-by") String searchBy, @RequestParam("word-search") String word, RedirectAttributes redirectAttributes){

        searchInfo info = new searchInfo(searchBy,word);
        redirectAttributes.addFlashAttribute("searchInfo",info);
        redirectAttributes.addFlashAttribute("students",studentBsl.search(searchBy,word));
        return "redirect:/";
    }



}

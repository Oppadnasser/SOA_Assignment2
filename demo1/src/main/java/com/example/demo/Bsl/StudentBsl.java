package com.example.demo.Bsl;
import com.example.demo.models.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service

public class StudentBsl {
    private XMLfile xmlfile;
    private List<Student> studentTable;
    public StudentBsl(){

    }
    public List<Student> addStudent (Student student)
    {
        xmlfile = new XMLfile();
        this.studentTable = xmlfile.getStudents();
        if(!student.getFirstName().matches("[a-z]+") || !student.getLastName().matches("[a-z]+") ||
                student.getGpa() < 0 || student.getGpa() > 4
                ){
            return null;
        }
        //check if the person is exist or not
        for(Student studentDB : studentTable)
        {
            if(studentDB.getId() == student.getId())
            {
                return null;
            }
        }
        studentTable.add(student);
        xmlfile.saveData(studentTable);
        return studentTable;

    }
    public List<Student> getStudentTable(){
        xmlfile = new XMLfile();
        this.studentTable = xmlfile.getStudents();
        return studentTable;
    }
    public List<Student> updateStudent(Student student){
        xmlfile = new XMLfile();
        this.studentTable = xmlfile.getStudents();
        int index = 0;
        for(Student st : this.studentTable){
            if(st.getId()==student.getId()){
                break;
            }
            else{
                index++;
            }
        }
        studentTable.get(index).setFirstName(student.getFirstName());
        studentTable.get(index).setLastName(student.getLastName());
        studentTable.get(index).setAddress(student.getAddress());
        studentTable.get(index).setGender(student.getGender());
        studentTable.get(index).setLevel(student.getLevel());
        studentTable.get(index).setGpa(student.getGpa());
        xmlfile.saveData(studentTable);
        return studentTable;
    }
    public List<Student> sort(String attribute, boolean desc){
        xmlfile = new XMLfile();
        studentTable = xmlfile.getStudents();
        if(!desc){
            switch (attribute) {
                case "id": {
                    studentTable.sort(Comparator.comparingInt(Student::getId));
                    break;
                }
                case "name": {
                    studentTable.sort(Comparator.comparing(Student::getFirstName));
                    break;
                }
                case "gender": {
                    studentTable.sort(Comparator.comparing(Student::getGender));
                    break;
                }
                case "gpa": {
                    studentTable.sort(Comparator.comparingDouble(Student::getGpa));
                    break;
                }
                case "level": {
                    studentTable.sort(Comparator.comparingInt(Student::getLevel));
                    break;
                }
                case "address": {
                    studentTable.sort(Comparator.comparing(Student::getAddress));
                    break;
                }
            }
        }else{
            switch (attribute) {
                case "id": {
                    studentTable.sort(Comparator.comparingInt(Student::getId).reversed());
                    break;
                }
                case "name": {
                    studentTable.sort(Comparator.comparing(Student::getFirstName).reversed());
                    break;
                }
                case "gender": {
                    studentTable.sort(Comparator.comparing(Student::getGender).reversed());
                    break;
                }
                case "gpa": {
                    studentTable.sort(Comparator.comparingDouble(Student::getGpa).reversed());
                    break;
                }
                case "level": {
                    studentTable.sort(Comparator.comparingInt(Student::getLevel).reversed());
                    break;
                }
                case "address": {
                    studentTable.sort(Comparator.comparing(Student::getAddress).reversed());
                    break;
                }
            }

        }
            xmlfile.saveData(studentTable);
            return studentTable;
    }
    public List<Student> delete(int id){
        xmlfile= new XMLfile();
        studentTable = xmlfile.getStudents();
        for(Student st: studentTable){
            if(st.getId() == id){
                studentTable.remove(st);
                break;
            }
        }
        xmlfile.saveData(studentTable);
        return studentTable;
    }
    public List<Student> search(String searchBy, String word){
        List<Student> result = new ArrayList<>();
        xmlfile= new XMLfile();
        studentTable = xmlfile.getStudents();
        switch(searchBy){
            case"id":{
                    for(Student st: studentTable){
                        if(String.valueOf(st.getId()).contains(word)){
                            result.add(st);
                        }
                    }
                    return result;
            }
            case"name":{
                for(Student st: studentTable){
                    String fullName = st.getFirstName() + " " + st.getLastName();
                    if(fullName.contains(word)){
                        result.add(st);
                    }
                }
                return result;
            }
            case"gpa":{
                for(Student st: studentTable){
                    if(String.valueOf(st.getGpa()).contains(word)){
                        result.add(st);
                    }
                }
                return result;
            }
            case"gender":{
                for(Student st: studentTable){
                    if(st.getGender().contains(word)){
                        result.add(st);
                    }
                }
                return result;
            }
            case"level":{
                for(Student st: studentTable){
                    if(String.valueOf(st.getLevel()).contains(word)){
                        result.add(st);
                    }
                }
                return result;
            }
            case"address":{
                for(Student st: studentTable){
                    if(st.getAddress().contains(word)){
                        result.add(st);
                    }
                }
                return result;
            }
        }
        return studentTable;
    }
}

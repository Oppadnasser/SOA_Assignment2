package com.example.demo.Bsl;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.example.demo.models.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLfile {
    private  File xmlFile;
    private Document doc;
    private Element rootElement;
    private DocumentBuilderFactory theDocFactory;
    private DocumentBuilder theDocBuilder;
    XMLfile() {

    }


    public List<Student> getStudents() {
        xmlFile = new File("Students.xml");
        List<Student> students = new ArrayList<Student>();
        try {
            theDocFactory = DocumentBuilderFactory.newInstance();
            theDocBuilder = theDocFactory.newDocumentBuilder();
//            Document doc;
            if (xmlFile.exists()) {
                doc = theDocBuilder.parse(xmlFile);
            } else {
                doc = theDocBuilder.newDocument();
                Element rootElement = doc.createElement("University");
                doc.appendChild(rootElement);
            }
            doc.getDocumentElement().normalize();
            NodeList studs = doc.getElementsByTagName("Student");
            for (int j = 0; j < studs.getLength(); j++) {
                Element student = (Element) studs.item(j);
                Student S = new Student(Integer.parseInt(student.getAttribute("ID")),
                        student.getElementsByTagName("FirstName").item(0).getTextContent(),student.getElementsByTagName("LastName").item(0).getTextContent(),
                        student.getElementsByTagName("Gender").item(0).getTextContent(),Float.parseFloat(student.getElementsByTagName("GPA").item(0).getTextContent()),Integer.parseInt(student.getElementsByTagName("Level").item(0).getTextContent()),
                        student.getElementsByTagName("Address").item(0).getTextContent());
                students.add(S);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            throw new Error("file can not be created");
        }
        return students;
    }
    public void saveData(List<Student> students) {
        xmlFile = new File("Students.xml");
        try {
            theDocFactory = DocumentBuilderFactory.newInstance();
            theDocBuilder = theDocFactory.newDocumentBuilder();
//            Document doc;
                doc = theDocBuilder.newDocument();
                Element rootElement = doc.createElement("University");
                doc.appendChild(rootElement);
            rootElement = doc.getDocumentElement();
            for (Student value : students) {
                Element student = doc.createElement("Student");
                student.setAttribute("ID", String.valueOf(value.getId()));
                rootElement.appendChild(student);

                Element firstName = doc.createElement("FirstName");
                firstName.appendChild(doc.createTextNode(value.getFirstName()));
                student.appendChild(firstName);

                Element lastName = doc.createElement("LastName");
                lastName.appendChild(doc.createTextNode(value.getLastName()));
                student.appendChild(lastName);

                Element gender = doc.createElement("Gender");
                gender.appendChild(doc.createTextNode(value.getGender()));
                student.appendChild(gender);

                Element gpa = doc.createElement("GPA");
                gpa.appendChild(doc.createTextNode(String.valueOf(value.getGpa())));
                student.appendChild(gpa);

                Element level = doc.createElement("Level");
                level.appendChild(doc.createTextNode(String.valueOf(value.getLevel())));
                student.appendChild(level);

                Element address = doc.createElement("Address");
                address.appendChild(doc.createTextNode(value.getAddress()));
                student.appendChild(address);
            }
            TransformerFactory theTranFact = TransformerFactory.newInstance();
            Transformer theTransformer = theTranFact.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(xmlFile);
            theTransformer.transform(source, file);
        }catch(TransformerException | ParserConfigurationException  e){
            e.printStackTrace();
        }
    }
}

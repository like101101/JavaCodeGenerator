package com.example.codegenerator.controller;

import com.example.codegenerator.entity.CodeTemplate;
import com.example.codegenerator.entity.EntityTemplate;
import com.example.codegenerator.mapper.CodeTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TemplateController {

    @Autowired
    private CodeTemplateMapper codeTemplateMapper;

    @GetMapping("/template")
    public String showForm(){
        return "generate_form";
    }

    @PostMapping("/template")
    public String generateCode(EntityTemplate entityTemplate) throws IOException {
        //Create the files and writers
        File testDirectory = new File(System.getProperty("user.dir")
                + File.separator + "results"
        );

        File entityFile = new File(testDirectory, entityTemplate.getName()+".java");
        File mapperFile = new File(testDirectory,entityTemplate.getName()+"Mapper.java");

        System.out.println(entityFile);
        System.out.println(mapperFile);

        FileWriter entityWriter = new FileWriter(entityFile);
        FileWriter mapperWriter = new FileWriter(mapperFile);

        //Use TemplateMapper to get code for each file
        CodeTemplate entityCodeTemp = codeTemplateMapper.selectById(1);
        CodeTemplate mapperCodeTemp = codeTemplateMapper.selectById(2);

        //Write the code for entity
        String entityCode = entityCodeTemp.getPrefix() + entityTemplate.getName() + " {\n" + entityTemplate.getFields() + "\n}";
        entityWriter.write(entityCode);
        entityWriter.close();

        //Write the code for mapper
        String mapperCode = mapperCodeTemp.getPrefix() + entityTemplate.getName() + mapperCodeTemp.getSuffix() + entityTemplate.getName() + ">{\n}";
        mapperWriter.write(mapperCode);
        mapperWriter.close();

        System.out.println("All files are successfully created");
        return "index";
    }
}

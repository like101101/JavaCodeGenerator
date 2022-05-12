package com.example.codegenerator;

import com.example.codegenerator.controller.TemplateController;
import com.example.codegenerator.entity.CodeTemplate;
import com.example.codegenerator.entity.EntityTemplate;
import com.example.codegenerator.mapper.CodeTemplateMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class CodeGeneratorApplicationTests {
    @Autowired
    private CodeTemplateMapper codeTemplateMapper;

    @Test
    //Test for connection to MySQL database
    void Load() {
        CodeTemplate codeTemplate = codeTemplateMapper.selectById(1);
        System.out.println(codeTemplate.getPrefix());
    }

    //Test for writing correct format
    @Test
    void generateUserFile() throws IOException {
        /**
         * In the test, we are creating an Entity of User, which contains id, username, and password
         */

        //Create the User Entity Template by hardcode it.
        String testName = "User";
        String testFields = "   private Long id\n" + "   private String username\n" + "   private String pwd\n";
        EntityTemplate userEntity = new EntityTemplate();
        userEntity.setName(testName);
        userEntity.setFields(testFields);

        //Create the files and writers
        File testDirectory = new File(System.getProperty("user.dir")
                + File.separator + "results"
        );

        File entityFile = new File(testDirectory, userEntity.getName()+".java");
        File mapperFile = new File(testDirectory,userEntity.getName()+"Mapper.java");

        System.out.println(entityFile);
        System.out.println(mapperFile);

        FileWriter entityWriter = new FileWriter(entityFile);
        FileWriter mapperWriter = new FileWriter(mapperFile);

        //Use TemplateMapper to get code for each file
        CodeTemplate entityCodeTemp = codeTemplateMapper.selectById(1);
        CodeTemplate mapperCodeTemp = codeTemplateMapper.selectById(2);

        //Write the code for entity
        String entityCode = entityCodeTemp.getPrefix() + userEntity.getName() + " {\n" + userEntity.getFields() + "\n}";
        entityWriter.write(entityCode);
        entityWriter.close();

        //Write the code for mapper
        String mapperCode = mapperCodeTemp.getPrefix() + userEntity.getName() + mapperCodeTemp.getSuffix() + userEntity.getName() + ">{\n}";
        mapperWriter.write(mapperCode);
        mapperWriter.close();

        System.out.println("All files are successfully created");

    }


}

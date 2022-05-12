package com.example.codegenerator.entity;

import lombok.Data;

@Data
public class CodeTemplate {
    /**
     * use id to identify the file is going to create
     *  1 --> entity file
     *  2 --> mapper file
     *  3 -->
     */
    private int id;
    private String prefix;
    private String suffix;

}

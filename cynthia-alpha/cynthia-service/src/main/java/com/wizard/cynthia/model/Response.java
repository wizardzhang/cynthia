package com.wizard.cynthia.model;

import lombok.Data;

@Data
public class Response {
    private int status;
    private Object jsonBody;

}
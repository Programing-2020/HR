package com.nphc.hr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nphc.hr.entity.Employee;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    //@InjectMocks
    //private EmployeeController employeeController;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
    //mockMvc= MockMvcBuilders.standaloneSetup(employeeController).build();
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    void helloWorld() throws Exception{
        //while (employeeController.helloWorld().getStatusCode()== HttpStatus.OK) {

            MvcResult mvcResult=mockMvc.perform(get("/users/hello"))
                    .andExpect(status().isOk()).andReturn()
                    ;
        System.out.println("Purushotham : "+mvcResult.getResponse());
            //verify(employeeController).hello();
        //}
    }

    @Test
    void getAllEmployeeInfo() throws Exception {
        MvcResult mvcResult=mockMvc
                            .perform(get("/users/").content(MediaType.APPLICATION_JSON_VALUE))
                            .andExpect(status().isOk()).andReturn();

        String resultContent=mvcResult.getResponse().getContentAsString();

    }
//
//    @Test
//    void getEmployeeById() {
//    }
//
    @Test
    void addEmployee() throws Exception{
        Employee employee = new Employee();
        employee.setId("123");
        employee.setLogin("Login1");
        employee.setName("Name1");
        employee.setSalary(234.89);
        employee.setStartDate(new Date());
        String jsonRequest=objectMapper.writeValueAsString(employee);
        MvcResult mvcResult=mockMvc.perform(post("/users/").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE)
                    ).andExpect(status().isOk()).andReturn();
        String result=mvcResult.getResponse().getContentAsString();
        //Response response=objectMapper.readValue(result,Response.class);
        //Assert.assertTrue(response.isDone());


    }
//
//    @Test
//    void deleteEmployeeById() {
//    }
//
//    @Test
//    void updateEmployee1() {
//    }
//
//    @Test
//    void updateEmployee() {
//    }
//
//    @Test
//    void uploadFile() {
//    }
}
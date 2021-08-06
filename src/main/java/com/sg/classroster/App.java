package com.sg.classroster;

import com.sg.classroster.controller.ClassRosterController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext ctx = 
           new ClassPathXmlApplicationContext("applicationContext.xml");
        ClassRosterController controller = 
           ctx.getBean("controller", ClassRosterController.class);
        controller.run();
    }
}

package com.jrpg.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @After("execution(* com.jrpg.service.SpaceshipService.getSpaceshipById(..)) && args(id)")
    public void logIfNegativeId(Long id) {
        if (id < 0) {
            System.out.println("Attempt to access spaceship with negative id: " + id);
        }
    }
}

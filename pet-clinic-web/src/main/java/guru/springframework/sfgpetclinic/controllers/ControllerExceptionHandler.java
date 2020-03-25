package guru.springframework.sfgpetclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBadRequest(Exception e){
        log.error("Handling Bad Request Exception");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception",e);
        return modelAndView;
    }
}

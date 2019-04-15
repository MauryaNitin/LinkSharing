package com.ttn.linksharing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("errors/errors");
        String errorMsg = "";
        String errorNumber="";
        int httpErrorCode = getErrorCode(httpRequest);
        switch (httpErrorCode) {
            case 400: {
                errorNumber = "400";
                errorMsg = " Bad Request";
                break;
            }
            case 401: {
                errorNumber = "401";
                errorMsg = " Unauthorized";
                break;
            }
            case 404: {
                errorNumber = "404";
                errorMsg = " Resource not found";
                break;
            }
            case 500: {
                errorNumber = "500";
                errorMsg = " Internal Server Error";
                break;
            }
            default: {
                errorMsg = " Server Error";
            }
        }
        errorPage.addObject("errorNumber", errorNumber);
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
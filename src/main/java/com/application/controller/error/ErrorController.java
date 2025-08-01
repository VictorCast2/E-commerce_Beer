package com.application.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/{Code}")
    public String handleError(@PathVariable int Code) {
        return switch (Code) {
            case 300 -> "/Error/300";
            case 400 -> "/Error/400";
            case 401 -> "/Error/401";
            case 403 -> "/Error/403";
            case 404 -> "/Error/404";
            case 500 -> "/Error/500";
            default -> "/Error/Error";
        };
    }

}
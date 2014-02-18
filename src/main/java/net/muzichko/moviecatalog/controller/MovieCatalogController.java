package net.muzichko.moviecatalog.controller;

import net.muzichko.moviecatalog.exception.MovieCatalogException;
import net.muzichko.moviecatalog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MovieCatalogController {

    
    @Autowired
    private MovieService movieService;
   

    @RequestMapping("/index")
    public String home() {
        return "redirect:/";
    }

    @RequestMapping("/*")
    public String error(){
        return "error";
    }

    @RequestMapping("/movies")
    public String allMovies() {
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listMovie(ModelMap model){
        try {
            model.addAttribute("listMovie", movieService.list());
            model.addAttribute("loggedUser", SecurityContextHolder.getContext().getAuthentication().getName());
            return "index";
        } catch (MovieCatalogException e) {
            model.addAttribute("stackTrace", e.getMessage()); // TODO : print stack trace
            return "error";
        }
    }
   
}
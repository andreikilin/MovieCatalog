package net.muzichko.moviecatalog.controller;

import java.util.List;

import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.MovieCatalogException;
import net.muzichko.moviecatalog.service.CountryService;
import net.muzichko.moviecatalog.service.GenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddMovieController {

	@Autowired
    private CountryService countryService;
	
	@Autowired
    private GenreService genreService;
	
	@RequestMapping(value = "/add/movie", method = RequestMethod.GET)
    public String addMovie(ModelMap model){

        try{
            model.addAttribute("movieFormActionTitle", "Add new movie");
            model.addAttribute("movieFormAction", "Add new movie");/// TODO: action adding ????????????
            model.addAttribute("countriesList", stringForSelect(countryService.list(), null));
            model.addAttribute("genresList", stringForSelect(genreService.list(), null));
            model.addAttribute("buttonAction", "add");
            return "/movieForm";
        } catch(MovieCatalogException e){
            model.addAttribute("stackTrace", e.getMessage());
            return "error";
        }
    }
	
	public static String stringForSelect(List<MovieCatalogEntity> list, MovieCatalogEntity selectedValue){

        String result = "";
        for(MovieCatalogEntity e : list){
            if((selectedValue != null) && (e.equals(selectedValue))){
                result += "<option selected value =" + e.getId() + ">" + e.getCaption() + "</option>";
            } else {
                result += "<option value =" + e.getId() + ">" + e.getCaption() + "</option>";
            }
        }
        return result;
    }
}

package net.muzichko.moviecatalog.controller;

import java.util.List;

import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.MovieCatalogException;
import net.muzichko.moviecatalog.service.GenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DeleteMovieController {

	@Autowired
    private GenreService genreService;
	
	@RequestMapping(value = "/delete/movie/{movieId}", method = RequestMethod.GET)
    public String deleteMovie(@PathVariable String movieId, ModelMap model){

        try{
            model.addAttribute("movieFormAction", "Delete movie");
            model.addAttribute("buttonAction", "delete");
            model.addAttribute("genresList", stringForSelect(genreService.list(),
                    genreService.getById(Integer.parseInt(movieId))));
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

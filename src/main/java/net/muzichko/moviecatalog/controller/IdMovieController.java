package net.muzichko.moviecatalog.controller;

import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.exception.MovieCatalogException;
import net.muzichko.moviecatalog.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IdMovieController {

	@Autowired
    private MovieService movieService;
	
	@RequestMapping("/movie/{movieId}")
    public String movie(@PathVariable Integer movieId, ModelMap model) {
        try{
            
        	Movie movie = movieService.getById(movieId);
        	Genre genre = movie.getGenre();
        	Country country = movie.getCountry();
        	model.addAttribute("name", movie.getName());
        	model.addAttribute("description", movie.getDescription());
        	model.addAttribute("year", movie.getYear());
        	model.addAttribute("staring", movie.getStarring());
        	model.addAttribute("genre", genre.getName());
        	model.addAttribute("country", country.getName());
            
            return "movie";
        } catch (MovieCatalogException e){
            model.addAttribute("stackTrace", e.getMessage());
            return "error";
        }
    }
}

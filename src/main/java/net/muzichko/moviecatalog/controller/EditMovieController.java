package net.muzichko.moviecatalog.controller;

import java.util.List;

import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.MovieCatalogException;
import net.muzichko.moviecatalog.service.CountryService;
import net.muzichko.moviecatalog.service.GenreService;
import net.muzichko.moviecatalog.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EditMovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private CountryService countryService;

	@RequestMapping(value = "/edit/movie/{movieId}", method = RequestMethod.GET)
	public String editMovie(@PathVariable String movieId, ModelMap model) {

		try {
			Movie movie = movieService.getById(Integer.parseInt(movieId));
			//List<Country> countyList = countryService.list();  
			
			model.addAttribute("movieFormAction", "Edit movie");
			model.addAttribute("countriesList", movie.getCountry());
			model.addAttribute(
					"genresList",
					stringForSelect(genreService.list(),
							genreService.getById(Integer.parseInt(movieId))));
			model.addAttribute("buttonAction", "Save");
			model.addAttribute("name", movie.getName());
			model.addAttribute("year", movie.getYear());
			model.addAttribute("starring", movie.getStarring());
			model.addAttribute("description", movie.getDescription());
			return "movieForm";
		} catch (MovieCatalogException e) {
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

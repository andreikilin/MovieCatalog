package net.muzichko.moviecatalog.web;

import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.MovieCatalogException;
import net.muzichko.moviecatalog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MovieCatalogController {

    @Autowired
    private GenreService genreService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ReviewService reviewService;

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping("/*")
    public String error(){
        return "error";
    }

    @RequestMapping("/movies")
    public String allMovies() {
        return "redirect:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String listMovie(ModelMap model){
        try {
            model.addAttribute("list", stringForJsp(movieService.list()));    // TODO: list size ????
            model.addAttribute("loggedUser", SecurityContextHolder.getContext().getAuthentication().getName());
            return "index";
        } catch (MovieCatalogException e) {
            model.addAttribute("stackTrace", e.getMessage()); // TODO : print stack trace
            return "error";
        }
    }

    @RequestMapping("/movie/{movieId}")
    public String movie(@PathVariable String movieId, ModelMap model) {
        try{
            model.addAttribute("movie", movieService.getById(Integer.parseInt(movieId)).toString());
            return "movie";
        } catch (MovieCatalogException e){
            model.addAttribute("stackTrace", e.getMessage());
            return "error";
        }
    }

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

    @RequestMapping(value = "/edit/movie/{movieId}", method = RequestMethod.GET)
    public String editMovie(@PathVariable String movieId, ModelMap model){

        try{
            Movie movie = movieService.getById(Integer.parseInt(movieId));

            model.addAttribute("movieFormAction", "Edit movie");
            model.addAttribute("genresList", stringForSelect(genreService.list(),
                    genreService.getById(Integer.parseInt(movieId))));
            model.addAttribute("buttonAction", "delete");
            model.addAttribute("name", movie.getName());
            model.addAttribute("year", movie.getYear());
            model.addAttribute("starring", movie.getStarring());
            model.addAttribute("description", movie.getDescription());
            return "/movieForm";
        } catch(MovieCatalogException e){
            model.addAttribute("stackTrace", e.getMessage());
            return "error";
        }
    }

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

    public static String stringForJsp(List<MovieCatalogEntity> list){

        String result = "";
        for(MovieCatalogEntity e : list){
            result += "<a href=\"/moviecatalog/movie/" + e.getId() + "\">" + e.getCaption() + "</a>  " +
            "<a href=\"/moviecatalog/edit/movie/" + e.getId() + "\">edit</a>  " +
            "<a href=\"/moviecatalog/delete/movie/" + e.getId() + "\">delete</a><br><br>";
        }
        return result;
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
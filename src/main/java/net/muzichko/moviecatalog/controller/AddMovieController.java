package net.muzichko.moviecatalog.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.exception.CantAddEntityException;
import net.muzichko.moviecatalog.exception.CantGetEntityListException;
import net.muzichko.moviecatalog.exception.EntityAlreadyExistsException;
import net.muzichko.moviecatalog.exception.MovieCatalogException;
import net.muzichko.moviecatalog.exception.MovieCatalogSystemException;
import net.muzichko.moviecatalog.exception.NoSuchEntityException;
import net.muzichko.moviecatalog.exception.ValidationMovieCatalogException;
import net.muzichko.moviecatalog.form.MovieEditForm;
import net.muzichko.moviecatalog.service.CountryService;
import net.muzichko.moviecatalog.service.GenreService;
import net.muzichko.moviecatalog.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddMovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
    private CountryService countryService;
	
	@Autowired
    private GenreService genreService;
	
	@RequestMapping(value = "/add/movie", method = RequestMethod.GET)
    public String addMovie(ModelMap model){
		try {
			model.addAttribute("countryList", countryService.list());
			model.put("genreList", genreService.list());
			List<Integer> yearList = new ArrayList<Integer>();
			for (Integer i = 1930; i <= Calendar.getInstance().get(
					Calendar.YEAR); i++) {
				yearList.add(i);
			}
			model.put("yearList", yearList);
			MovieEditForm addForm = new MovieEditForm();
			model.put("addMovie", addForm);
			model.put("movieFormAction", "add/movie");

			
			return "addMovieForm";
		} catch (MovieCatalogException e) {
			model.addAttribute("stackTrace", e.getMessage());
			return "error";
		}
    }
	
	@RequestMapping(value = "/add/movie", method = RequestMethod.POST)
	public String processAddMovive(
			@Valid @ModelAttribute("addMovie") MovieEditForm addForm,
			BindingResult result, ModelMap model)
			throws NoSuchEntityException, MovieCatalogSystemException,
			CantGetEntityListException, EntityAlreadyExistsException,
			ValidationMovieCatalogException, CantAddEntityException {

		if (result.hasErrors()) {
			model.put("countryList", countryService.list());
			model.put("genreList", genreService.list());
			//model.put("id", addForm.getId()); // TODO: How validate "id" in request
			List<Integer> yearList = new ArrayList<Integer>();
			for (Integer i = 1930; i <= Calendar.getInstance().get(
					Calendar.YEAR); i++) {
				yearList.add(i);
			}
			model.put("yearList", yearList);
			model.put("editMovie", addForm);
			model.put("movieFormAction", "add/movie/");
			return "addMovieForm";

		} else {
			Movie movie = new Movie(addForm.getName(),
					genreService.getById(addForm.getGenreId()),
					addForm.getDescription(), addForm.getStarring(),
					addForm.getYear(), countryService.getById(addForm
							.getCountryId()));
			movieService.add(movie);
			return "redirect:/";
		}

	}
}

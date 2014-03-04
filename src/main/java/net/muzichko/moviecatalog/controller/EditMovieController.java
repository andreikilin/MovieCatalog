package net.muzichko.moviecatalog.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.exception.CantGetEntityListException;
import net.muzichko.moviecatalog.exception.CantUpdateEntityException;
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
	public String editMovie(@PathVariable Integer movieId, ModelMap model) {

		try {
			Movie movie = movieService.getById(movieId);
			model.addAttribute("countryList", countryService.list());
			model.put("id", movie.getId()); // TODO: How validate "id" in POST?
			model.put("genreList", genreService.list());
			List<Integer> yearList = new ArrayList<Integer>();
			for (Integer i = 1930; i <= Calendar.getInstance().get(
					Calendar.YEAR); i++) {
				yearList.add(i);
			}
			model.put("yearList", yearList);
			MovieEditForm editForm = new MovieEditForm();
			model.put("editMovie", editForm);
			model.put("movieFormAction", "edit/movie/" + movieId);

			// Initialization form fields
			editForm.importMovie(movie);

			return "editMovieForm";
		} catch (MovieCatalogException e) {
			model.addAttribute("stackTrace", e.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/edit/movie/{movieId}", method = RequestMethod.POST)
	public String processEditMovie(
			@Valid @ModelAttribute("editMovie") MovieEditForm editForm,
			BindingResult result, ModelMap model, @PathVariable Integer movieId)
			throws NoSuchEntityException, MovieCatalogSystemException,
			CantGetEntityListException, EntityAlreadyExistsException,
			ValidationMovieCatalogException, CantUpdateEntityException {

		if (result.hasErrors()) {
			model.put("countryList", countryService.list());
			model.put("genreList", genreService.list());
			model.put("id", editForm.getId()); // TODO: How validate "id" in request
			List<Integer> yearList = new ArrayList<Integer>();
			for (Integer i = 1930; i <= Calendar.getInstance().get(
					Calendar.YEAR); i++) {
				yearList.add(i);
			}
			model.put("yearList", yearList);
			model.put("editMovie", editForm);
			model.put("movieFormAction", "edit/movie/" + movieId);
			return "editMovieForm";

		} else {
			Movie movie = new Movie(editForm.getId(), editForm.getName(),
					genreService.getById(editForm.getGenreId()),
					editForm.getDescription(), editForm.getStarring(),
					editForm.getYear(), countryService.getById(editForm
							.getCountryId()));
			movieService.update(movie);
			return "redirect:/";
		}

	}

}

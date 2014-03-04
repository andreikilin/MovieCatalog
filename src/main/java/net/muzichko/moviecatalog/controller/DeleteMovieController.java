package net.muzichko.moviecatalog.controller;

import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.exception.CantDeleteEntityException;
import net.muzichko.moviecatalog.exception.CantGetEntityListException;
import net.muzichko.moviecatalog.exception.MovieCatalogException;
import net.muzichko.moviecatalog.exception.MovieCatalogSystemException;
import net.muzichko.moviecatalog.exception.NoSuchEntityException;
import net.muzichko.moviecatalog.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DeleteMovieController {

	@Autowired
	private MovieService movieService;

	@RequestMapping(value = "/delete/movie/{movieId}", method = RequestMethod.GET)
	public String deleteMovie(@PathVariable Integer movieId, ModelMap model) {

		try {
			Movie movie = movieService.getById(movieId);
			Genre genre = movie.getGenre();
			Country country = movie.getCountry();
			model.addAttribute("name", movie.getName());
			model.addAttribute("description", movie.getDescription());
			model.addAttribute("year", movie.getYear());
			model.addAttribute("staring", movie.getStarring());
			model.addAttribute("genre", genre.getName());
			model.addAttribute("country", country.getName());
			model.addAttribute("movieFormAction", "delete/movie/" + movieId);
			
			return "deleteMovieForm";
		} catch (MovieCatalogException e) {
			model.addAttribute("stackTrace", e.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/delete/movie/{movieId}", method = RequestMethod.POST)
	public String deleteMovieProcess(@PathVariable Integer movieId,
			ModelMap model) {
		try {
			movieService.delete(movieService.getById(movieId));
		} catch (MovieCatalogSystemException | CantDeleteEntityException
				| NoSuchEntityException | CantGetEntityListException e) {
			model.addAttribute("stackTrace", e.getMessage());
			return "error";
		}
		return "redirect:/";
	}

}

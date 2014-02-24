package net.muzichko.moviecatalog.form;

import java.util.List;

import javax.validation.constraints.Size;

import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.Movie;

public class MovieEditForm {

	
	
	@Size(min = 2, max = 30, message = "Name can be more than 2 characters and less than 30 characters")
	private String name;

	@Size(min = 4, max = 50, message = "Starring can be more than 4 characters and less than 50 characters")
	private String starring;

	private int countryId;

	private int genreId;

	private int year;

	private int id;

	private List<Country> countryList;
	
	private List<Genre> genreList;
	
	@Size(min = 10, max = 200, message = "Description can be more than 10 characters and less than 200 characters")
	private String description;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStarring() {
		return this.starring;
	}

	public void setStarring(String starring) {
		this.starring = starring;
	}

	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getGenreId() {
		return this.genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Country> getCountryList() {
		return countryList;
	}

	public void setGenreList(List<Genre> genreList) {
		this.genreList = genreList;
	}
	
	public List<Genre> getGenreList() {
		return genreList;
	}

	public void setCountryList(List<Genre> genreList) {
		this.genreList = genreList;
	}

	public void importMovie(Movie movie) {
		this.id = movie.getId();
		this.year = movie.getYear();
		this.countryId = movie.getCountry().getId();
		this.description = movie.getDescription();
		this.genreId = movie.getGenre().getId();
		this.name = movie.getName();
		this.starring = movie.getStarring();
	}

}

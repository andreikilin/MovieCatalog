package net.muzichko.moviecatalog.domain;

public class Movie implements MovieCatalogEntity{

    private int id;
    private String name;
    private Genre genre;
    private String Description;
    private String Starring;
    private int year;
    private Country country;

    public Movie() {
    }

    public Movie(int id, String name, Genre genre, String description, String starring, int year, Country country) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        Description = description;
        Starring = starring;
        this.year = year;
        this.country = country;
    }

    public Movie(String name, Genre genre, String description, String starring, int year, Country country) {
        this.name = name;
        this.genre = genre;
        Description = description;
        Starring = starring;
        this.year = year;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getCaption() {
        return name + " " + year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStarring() {
        return Starring;
    }

    public void setStarring(String starring) {
        Starring = starring;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", Description='" + Description + '\'' +
                ", Starring='" + Starring + '\'' +
                ", year=" + year +
                ", country=" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (year != movie.year) return false;
        if (Description != null ? !Description.equals(movie.Description) : movie.Description != null) return false;
        if (Starring != null ? !Starring.equals(movie.Starring) : movie.Starring != null) return false;
        if (!country.equals(movie.country)) return false;
        if (!genre.equals(movie.genre)) return false;
        if (!name.equals(movie.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + genre.hashCode();
        result = 31 * result + (Description != null ? Description.hashCode() : 0);
        result = 31 * result + (Starring != null ? Starring.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + country.hashCode();
        return result;
    }
}

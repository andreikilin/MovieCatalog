package net.muzichko.moviecatalog.domain;

public class Movie implements MovieCatalogEntity{

    private int id;
    private String name;
    private Genre genre;
    private String description;
    private String starring;
    private int year;
    private Country country;

    public Movie() {
    }

    public Movie(int id, String name, Genre genre, String description, String starring, int year, Country country) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.starring = starring;
        this.year = year;
        this.country = country;
    }

    public Movie(String name, Genre genre, String description, String starring, int year, Country country) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.starring = starring;
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
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStarring() {
        return this.starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
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
                ", Description='" + description + '\'' +
                ", Starring='" + starring + '\'' +
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
        if (this.description != null ? !this.description.equals(movie.description) : movie.description != null) return false;
        if (this.starring != null ? !this.starring.equals(movie.starring) : movie.starring != null) return false;
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
        result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
        result = 31 * result + (this.starring != null ? this.starring.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + country.hashCode();
        return result;
    }
}

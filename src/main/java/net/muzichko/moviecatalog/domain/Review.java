package net.muzichko.moviecatalog.domain;


import java.sql.Timestamp;

public class Review{

    private int id;
    private Movie movie;
    private User user;
    private boolean viewed;
    private int rating;
    private String comment;
    private Timestamp timestamp;

    public Review(Movie movie, User user, boolean viewed, int rating, String comment) {
        this.movie = movie;
        this.user = user;
        this.viewed = viewed;
        this.rating = rating;
        this.comment = comment;
    }

    public Review(int id, Movie movie, User user, boolean viewed, int rating, String comment, Timestamp timestamp) {
        this.id = id;
        this.movie = movie;
        this.user = user;
        this.viewed = viewed;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", movie=" + movie +
                ", user=" + user +
                ", viewed=" + viewed +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (id != review.id) return false;
        if (rating != review.rating) return false;
        if (viewed != review.viewed) return false;
        if (comment != null ? !comment.equals(review.comment) : review.comment != null) return false;
        if (!movie.equals(review.movie)) return false;
        if (!timestamp.equals(review.timestamp)) return false;
        if (!user.equals(review.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + movie.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + (viewed ? 1 : 0);
        result = 31 * result + rating;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + timestamp.hashCode();
        return result;
    }
    
    public String getCaption() {

        String newLine = System.getProperty("line.separator");

        return movie.getCaption() + newLine +
                user.getCaption() + newLine +
                "Rating: " + rating + newLine +
                comment + newLine +
                timestamp;
    }
}

package alex.myapplication;

import java.util.List;

class Movie
{
    boolean adult;
    String backdrop_path;
    String belongs_to_collection;
    int budget;
    List<Genres> genres;
    String homepage;
    int id;
    String imdb_id;
    String original_language;
    String original_title;
    String overview;
    Double popularity;
    String poster_path;
    List<ProductionCompanies> production_companies;
    List<ProductionCountries> production_countries;
    String release_date;
    int revenue;
    int runtime;
    List<SpokenLanguages> spoken_languages;
    String status;
    String tagline;
    String title;
    boolean video;
    Double vote_average;
    int vote_count;

    class Genres
    {
        int id;
        String name;
    }

    class ProductionCompanies
    {
        String name;
        int id;
    }

    class ProductionCountries
    {
        String iso_3166_1;
        String name;
    }

    class SpokenLanguages
    {
        String iso_639_1;
        String name;
    }
}

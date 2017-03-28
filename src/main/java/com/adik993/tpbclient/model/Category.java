package com.adik993.tpbclient.model;

import com.adik993.tpbclient.exceptions.ParseException;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adrian on 2016-07-09.
 */
public enum Category {
    All(0),

    Audio(100),
    Music(101),
    AudioBooks(102),
    SoundClips(103),
    FLAC(104),
    AudioOther(199),

    Video(200),
    Movies(201),
    MoviesDVDR(202),
    MusicVideo(203),
    MovieClips(204),
    TVShows(205),
    VideoHandheld(206),
    HDMovies(207),
    HDTVShows(208),
    Movie3D(209),
    VideoOther(299),

    Applications(300),
    Windows(301),
    ApplicationsMac(302),
    UNIX(303),
    ApplicationsHandheld(304),
    ApplicationsiOS(305),
    ApplicationsAndroid(306),
    OtherOS(399),

    Games(400),
    PC(401),
    GamesMac(402),
    PSx(403),
    XBOX360(404),
    Wii(405),
    GamesHandheld(406),
    GamesIOS(407),
    GamesAndroid(408),
    GamesOther(499),

    Porn(500),
    PornMovies(501),
    PornMoviesDVDR(502),
    PornPictures(503),
    PornGames(504),
    PornHDMovies(505),
    PornMovieClip(506),
    PornOther(599),

    Other(600),
    OtherEBooks(601),
    OtherComics(602),
    OtherPictures(603),
    OtherCovers(604),
    OtherPhysibles(605),
    OtherOther(699),

    Unknown(9999);

    private static final Pattern PATTERN = Pattern.compile("/browse/([0-9]+)");
    private int id;

    Category(int id) {
        this.id = id;
    }

    public static Category parse(String href) throws ParseException {
        Matcher matcher = PATTERN.matcher(href);
        if (!matcher.matches()) throw new ParseException("Unbale to parse " + href);
        int id = Integer.parseInt(matcher.group(1));
        Optional<Category> optional = Arrays.stream(values())
                .filter(category -> category.getId() == id)
                .findFirst();
        return optional.orElse(Unknown);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getId() + "";
    }
}

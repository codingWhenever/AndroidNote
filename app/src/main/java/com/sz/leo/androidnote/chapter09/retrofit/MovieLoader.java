package com.sz.leo.androidnote.chapter09.retrofit;

import com.sz.leo.androidnote.chapter09.retrofit.http.BaseResponse;
import com.sz.leo.androidnote.chapter09.retrofit.http.ObjectLoader;
import com.sz.leo.androidnote.chapter09.retrofit.http.PayLoad;
import com.sz.leo.androidnote.chapter09.retrofit.http.RetrofitServiceManager;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author：leo
 * @date：2019/6/18
 * @email：lei.lu@e-at.com
 */
public class MovieLoader extends ObjectLoader {
    private MovieService mMovieService;

    public MovieLoader() {
        mMovieService = RetrofitServiceManager.getInstance().create(MovieService.class);
    }

    public Observable<List<Movie>> getMovie(int start, int count) {
//        return observe(mMovieService.getTop250(start, count))
//                .map(new PayLoad<BaseResponse<List<Movie>>>());
        return observe(mMovieService.getTop250(start, count))
                .map(new Func1<MovieSubject, List<Movie>>() {
                    @Override
                    public List<Movie> call(MovieSubject movieSubject) {
                        return movieSubject.subjects;
                    }
                });
    }

    public Observable<String> getWeatherList(String cityId, String key) {
        return observe(mMovieService.getWeather(cityId, key))
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s;
                    }
                });
    }
}

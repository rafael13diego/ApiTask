package com.spring.professional.services;

import com.spring.professional.dto.Root;
import io.reactivex.Single;
import org.springframework.http.ResponseEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface SateliteService {
    //consultar satelite
    //https://api.wheretheiss.at/v1/satellites/25544
    /*@GET("https://api.wheretheiss.at/v1/satellites/{id}")
    Single<Root> getSatelite (@Path("id") String id);*/

    //@GET("https://api.wheretheiss.at/v1/satellites/{id}")
    //Single<Root> getSatelite(@Path("id") String id);
    ResponseEntity<Root> getSatelite(@Path("id") String id);

}

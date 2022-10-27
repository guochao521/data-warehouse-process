package org.simulate.dw.rest;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import okhttp3.RequestBody;
import org.simulate.dw.dto.ResultMsg;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;


@RetrofitClient(baseUrl = "${simulate.baseUrl}")
public interface UserAction {

    @POST("/process")
    Response<ResultMsg> postAction(@Body RequestBody data);



}

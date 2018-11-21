package com.example.admin.mydailystudy.net;


import com.example.admin.mydailystudy.bean.CollectArticleBean;
import com.example.admin.mydailystudy.bean.HomeBannerDataBean;
import com.example.admin.mydailystudy.bean.HomeDataBean;
import com.example.admin.mydailystudy.bean.HotKeyDataBean;
import com.example.admin.mydailystudy.bean.ProjectDetailDataBean;
import com.example.admin.mydailystudy.bean.ProjectTabBean;
import com.example.admin.mydailystudy.bean.SearchByKeyDataBean;
import com.example.admin.mydailystudy.bean.SystemDataBean;
import com.example.admin.mydailystudy.bean.SystemDetailBean;
import com.example.admin.mydailystudy.bean.TodoListDataBean;
import com.example.admin.mydailystudy.bean.UserRegisterAndLoginBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MyGetNetData {

    @GET("article/list/{id}/json")
    Observable<HomeDataBean> getHomeData(@Path("id") int id);

    @GET("banner/json")
    Observable<HomeBannerDataBean> getHomeBannerData();

    @GET("tree/json")
    Observable<SystemDataBean> getSystemData();

    @GET("article/list/{curPage}/json?")
    Observable<SystemDetailBean> getSystemDetailData(@Path("curPage") int curPage, @Query("cid") int cid);

    @GET("project/tree/json")
    Observable<ProjectTabBean> getProjectTabData();

    @GET("project/list/{page}/json")
    Observable<ProjectDetailDataBean> getProjectDetailData(@Path("page") int page, @Query("cid") int cid);

    @GET("/hotkey/json")
    Observable<HotKeyDataBean> getHotKeyData();

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<SearchByKeyDataBean> getSearchData(@Path("page") int page, @Field("k") String keyWord);

    @POST("user/register")
    @FormUrlEncoded
    Observable<UserRegisterAndLoginBean> registerUser(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @POST("user/login")
    @FormUrlEncoded
    Observable<UserRegisterAndLoginBean> userLogin(@Field("username") String username, @Field("password") String password);

    @GET("user/logout/json")
    Observable<WanAndroidBaseResponse> logout();

    @POST("lg/collect/{id}/json")
    Observable<WanAndroidBaseResponse> collectArticle(@Path("id") int id);

    @GET("lg/collect/list/{page}/json")
    Observable<CollectArticleBean> getCollected(@Path("page") int page);

    //我的收藏页面取消收藏
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<WanAndroidBaseResponse> cancelColleted(@Path("id") int id, @Field("originId") int originId);

    //文章列表下取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    Observable<WanAndroidBaseResponse> cancelColleted(@Path("id") int id);

    @GET("lg/todo/list/{page}/json")
    Observable<TodoListDataBean> getTodolistData(@Path("page") int page);

    @POST("lg/todo/listnotdo/{type}/json/{page}")
    Observable<TodoListDataBean> getTodoList(@Path("type") int type, @Path("page") int page);

    @POST("lg/todo/listdone/{type}/json/{page}")
    Observable<TodoListDataBean> getTodoDoneList(@Path("type") int type, @Path("page") int page);

    //新增一条Todo
    @POST("lg/todo/add/json")
    @FormUrlEncoded
    Observable<WanAndroidBaseResponse> addTodo(@Field("title") String title,
                                               @Field("content") String content, @Field("date") String date, @Field("type") int type);

    //更新一条Todo内容
    @POST("lg/todo/update/{id}/json")
    @FormUrlEncoded
    Observable<WanAndroidBaseResponse> updateTodo(@Path("id") int id, @Field("title") String title, @Field("content") String content,
                                                  @Field("date") String date, @Field("status") int status, @Field("type") int type);

    //删除一条Todo内容
    @POST("lg/todo/delete/{id}/json")
    Observable<WanAndroidBaseResponse> deleteTodo(@Path("id") int id);
}

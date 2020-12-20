package com.everlastingseo.organicpandit.helper;

import com.everlastingseo.organicpandit.pojo.AplyBidResponse;
import com.everlastingseo.organicpandit.pojo.ForgotpasswordResponse;
import com.everlastingseo.organicpandit.pojo.buyproductinquiry.BuyProductinquryResponse;
import com.everlastingseo.organicpandit.pojo.certificate.CertificteResponse;
import com.everlastingseo.organicpandit.pojo.certificate_agency.CertificateAgencyResponse;
import com.everlastingseo.organicpandit.pojo.citylist.CityRespose;
import com.everlastingseo.organicpandit.pojo.contrylist.CountryResponse;
import com.everlastingseo.organicpandit.pojo.fetchuserdata.FetchUserDataResponse;
import com.everlastingseo.organicpandit.pojo.fetchuserdata_details.UserDataDetailsResponse;
import com.everlastingseo.organicpandit.pojo.login.LoginResponse;
import com.everlastingseo.organicpandit.pojo.organicInput_product.OrganicProductREsponse;
import com.everlastingseo.organicpandit.pojo.postrequirment.PostReqirmentResponse;
import com.everlastingseo.organicpandit.pojo.preparepaymentgatway.ResponsePrepareForPaymentGateway;
import com.everlastingseo.organicpandit.pojo.product.ProductResponse;
import com.everlastingseo.organicpandit.pojo.product_category.CategoryResponse;
import com.everlastingseo.organicpandit.pojo.searchuser_wise.SearchResponse;
import com.everlastingseo.organicpandit.pojo.sellproduct.SellProductResponse;
import com.everlastingseo.organicpandit.pojo.sellproductdetails.SellProductDetailsResponse;
import com.everlastingseo.organicpandit.pojo.shopdata.ShopDataDetails;
import com.everlastingseo.organicpandit.pojo.sliderimage.SliderImageResponse;
import com.everlastingseo.organicpandit.pojo.statelist.StateResponse;
import com.everlastingseo.organicpandit.pojo.usertypr.UserTypeResponse;
import com.everlastingseo.organicpandit.pojo.worth_details.WorthResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("api-v1/user/login")
    Single<LoginResponse> getLogin(@Field("username") String username,
                                   @Field("password") String password,
                                   @Field("user_type_id") String usertype);

    @FormUrlEncoded
    @POST("api-v1/user/forgot-password")
    Single<ForgotpasswordResponse> getforgotpassword(@Field("username") String username);

    @GET("api-v1/fetch-user-type-list")
    Single<UserTypeResponse> getUserTypeList();

    @GET("api-v1/fetch-countries")
    Single<CountryResponse> getContryList();

    @FormUrlEncoded
    @POST("api-v1/fetch-states")
    Single<StateResponse> getstateList(@Field("country_id") String country_id);

    @FormUrlEncoded
    @POST("api-v1/fetch-cities")
    Single<CityRespose> getcities(@Field("state_id") String country_id);

    @GET("api-v1/fetch-certification-agencies")
    Single<CertificateAgencyResponse> getAgencyList();


    @FormUrlEncoded
    @POST("api-v1/user/registration")
    Single<FetchUserDataResponse> getregistration(@Field("user_type_id") String user_type_id,
                                                  @Field("fullname") String fullname, @Field("username") String username,
                                                  @Field("email_id") String emailid, @Field("mobile_no") String mobile,
                                                  @Field("password") String password, @Field("confirm_password") String confimpassword,
                                                  @Field("address") String address, @Field("country_id") String contryID,
                                                  @Field("state_id") String stateID, @Field("city_id") String cityID,
                                                  @Field("certification_id") String cerificatio, @Field("agency_id") String agencyID);

    @GET("api-v1/fetch-certifications")
    Single<CertificteResponse> getcertificate();

    @GET("api-v1/fetch-categories")
    Single<CategoryResponse> getfetch_categories();

    @GET("api-v1/fetch-products")
    Single<ProductResponse> getproduct();

    @FormUrlEncoded
    @POST("api-v1/user/fetch-users-list")
    Single<SearchResponse> serchData(@Field("user_type_id") String user_type_id, @Field("state_id") String state_id, @Field("city_id") String city_id,
                                     @Field("search_brand") String search_brand);

    @FormUrlEncoded
    @POST("api-v1/user/fetch-users-list")
    Call<SearchResponse> TestserchData(@Field("user_type_id") String user_type_id, @Field("state_id") String state_id, @Field("city_id") String city_id,
                                       @Field("search_brand") String search_brand, @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST("api-v1/user/fetch-user")
    Single<UserDataDetailsResponse> fetchUserData(@Field("user_id") String user_id);

    @GET("api-v1/post-requirement/fetch-total-worth")
    Single<WorthResponse> fetchtotalworth();

    @GET("api-v1/fetch-app-slider-images")
    Single<SliderImageResponse> getSliderImage();


    @FormUrlEncoded
    @POST("api-v1/post-requirement/fetch-post-requirement-list")
    Single<PostReqirmentResponse> postrequirement_list(@Field("state_id") String state_id, @Field("city_id") String city_id,
                                                       @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST("api-v1/post-requirement/fetch-post-requirement-list")
    Call<PostReqirmentResponse> Testpostrequirement_list(@Field("state_id") String state_id, @Field("city_id") String city_id,
                                                         @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST("api-v1/buy-sell-product/fetch-buy-sell-product-list")
    Call<SellProductResponse> TestsellproductList(
            @Field("state_id") String state_id, @Field("delivery_location") String delivery_location,
            @Field("category_id") String user_id, @Field("product_id") String product_id, @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST("api-v1/buy-sell-product/fetch-buy-sell-product-list")
    Single<SellProductResponse> sellproductList(
            @Field("state_id") String state_id, @Field("delivery_location") String delivery_location,
            @Field("category_id") String user_id, @Field("product_id") String product_id, @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST("api-v1/user/fetch-user-organic-input-list")
    Single<OrganicProductREsponse> FetchorganicProduct(@Field("user_id") String user_id, @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST("api-v1/user/fetch-user-shops-list")
    Single<ShopDataDetails> fetchshopData(@Field("user_id") String user_id, @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST("api-v1/post-requirement/apply-bid")
    Single<AplyBidResponse> aplybid(@Field("user_id") String user_id, @Field("post_requirement_id") String post_requirement_id,
                                    @Field("amount") String amt, @Field("comment") String comment);


    @FormUrlEncoded
    @POST("api-v1/buy-sell-product/fetch-buy-sell-product-details")
    Single<SellProductDetailsResponse> sellproductDetails(
            @Field("sell_product_id") String sell_product_id);

    @FormUrlEncoded
    @POST("api-v1/post-requirement/insert")
    Single<CityRespose> getCreatePost(@Field("user_id") String userid,
                                      @Field("company_name") String companyname, @Field("product_id") String product_id,
                                      @Field("quality_specification") String qualityspeci, @Field("from_date") String framdate,
                                      @Field("to_date") String todate, @Field("price") String price,
                                      @Field("quantity") String quantity, @Field("total_price") String totalprice,
                                      @Field("delivery_address") String deliveryaddress, @Field("pincode") String pincode,
                                      @Field("payment_terms") String paymentterms, @Field("state_id") String stateId,
                                      @Field("city_id") String cityId, @Field("is_logistic") String logistic_id,
                                      @Field("certification_id") String certification_id, @Field("other_details") String others);

    @FormUrlEncoded
    @POST("api-v1/buy-sell-product/send-enquiry")
    Single<BuyProductinquryResponse> sellproductDetailsEnquiry(
            @Field("sell_product_id") String sell_product_id, @Field("user_id") String user_id, @Field("description") String description);


    @FormUrlEncoded
    @POST("api-v1/user/send-enquiry")
    Single<BuyProductinquryResponse> userSendInquiry(
            @Field("user_type_id") String usertype, @Field("user_id") String user_id, @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("mobile_no") String mobile_no, @Field("description") String description);



//    @FormUrlEncoded
    @POST("api-v1/organic-service-portal")
    Single<ResponsePrepareForPaymentGateway> getorderAPi(@Body JsonObject jsonObject);

    @POST("api-v1/organic-service-portal")
    Single<ResponsePrepareForPaymentGateway> getSubcriptionApi(@Body JsonObject jsonObject);

    @POST("api-v1/organic-service-portal")
    Single<ResponsePrepareForPaymentGateway> SubscriptionPaymentResponse(@Body JsonObject jsonObject);


    @POST("api-v1/organic-service-portal")
    Single<ResponsePrepareForPaymentGateway> getSubcriptionPlanApi(@Body JsonObject jsonObject);

    @POST("api-v1/organic-service-portal")
    Single<ResponsePrepareForPaymentGateway> sendPaymentresponse(@Body JsonObject jsonObject);

}

package com.everlastingseo.organicpandit.addproduct;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.pojo.product_category.CategoryResponse;
import com.everlastingseo.organicpandit.pojo.product_category.CategoryResponseData;
import com.everlastingseo.organicpandit.pojo.userfetchproduct.Datum;
import com.everlastingseo.organicpandit.pojo.userfetchproduct.UserAddProductFetchRespose;
import com.google.gson.JsonObject;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UserAddProductActivity extends AppCompatActivity {
    private static final int SELECT_FILE = 101;
    private static final int REQUEST_CAMERA = 1101;
    final Calendar myCalendar = Calendar.getInstance();
    Context mContext;
    String userChoosenTask = "";
    String productCategoryID = "";
    String productID = "";
    ApiService apiService;
    List<Datum> mProductList = new ArrayList<>();
    List<CategoryResponseData> categoryList = new ArrayList<>();
    ImageView mimgfromcamerandgallery;
    EditText mTxtfromDate, mTxtTodate;
    Button mBtnSubmit;
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    DatePickerDialog.OnDateSetListener Todatedate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            myCalendar.setTimeInMillis(System.currentTimeMillis() - 1000);
            updateLabelTodatedate();
        }

    };
    EditText mEditproductDescription, mEditproductQuality, mEditprice;
    AdapterView.OnItemSelectedListener listenerProductID = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (mProductList.size() > 0) {

                if (mProductList.get(position).getId() != null) {
                    productID = mProductList.get(position).getId();
                } else {

                }

            } else {

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private SearchableSpinner mSpinnerProductList, mSpinnerProductQuantity, mSpinnerProductCategoryList;
    AdapterView.OnItemSelectedListener listenerProduct = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (categoryList.size() > 0) {

                if (categoryList.get(position).getId() != null) {
                    getproductfromAPI(categoryList.get(position).getId());
                    productCategoryID = categoryList.get(position).getId();
                } else {
                    getproductfromAPI("1");

                }

            } else {

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Add Product");
        bindview();

    }

    private void bindview() {
        mContext = UserAddProductActivity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        getproductcategoryfromAPI();
        mSpinnerProductList = findViewById(R.id.SpinnerProductList);
        mSpinnerProductCategoryList = findViewById(R.id.SpinnerProductCategoryList);
        mSpinnerProductQuantity = findViewById(R.id.SpinnerProductQuantity);
        mEditproductDescription = findViewById(R.id.EditproductDescription);
        mEditproductQuality = findViewById(R.id.EditproductQuality);
        mEditprice = findViewById(R.id.Editprice);

        mTxtfromDate = findViewById(R.id.TxtfromDate);
        mTxtTodate = findViewById(R.id.TxtTodate);
        mBtnSubmit = findViewById(R.id.BtnSubmit);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mSpinnerProductCategoryList.getSelectedItem().toString().trim().equals("Select Category")) {
                    ((TextView) mSpinnerProductCategoryList.getSelectedView()).setError("Select Product category");
                } else {
                    if (mSpinnerProductList.getSelectedItem().toString().trim() == null || mSpinnerProductList.getSelectedItem().toString().trim().equals("Select Product")) {
                        ((TextView) mSpinnerProductList.getSelectedView()).setError("Select Product ");
                    } else {
                        if (mSpinnerProductQuantity.getSelectedItem().toString().trim() == null || mSpinnerProductQuantity.getSelectedItem().toString().trim().equals("Select Quantity")) {
                            ((TextView) mSpinnerProductQuantity.getSelectedView()).setError("Select Quantity");
                        } else {
                            if (TextUtils.isEmpty(mEditproductDescription.getText().toString().trim())) {
                                mEditproductDescription.setError("Enter Discription ");
                            } else {
                                if (TextUtils.isEmpty(mEditproductQuality.getText().toString().trim())) {
                                    mEditproductQuality.setError("Enter Quality ");
                                } else {
                                    if (TextUtils.isEmpty(mEditprice.getText().toString().trim())) {
                                        mEditprice.setError("Enter Price ");
                                    } else {
                                        if (TextUtils.isEmpty(mTxtfromDate.getText().toString().trim())) {
                                            mTxtfromDate.setError("Enter From Date ");
                                        } else {
                                            if (TextUtils.isEmpty(mTxtTodate.getText().toString().trim())) {
                                                mTxtTodate.setError("Enter To Date ");
                                            } else {
                                                calladdproductapi(mEditproductDescription.getText().toString().trim(), mEditproductQuality.getText().toString().trim(),
                                                        mEditprice.getText().toString().trim(), mTxtfromDate.getText().toString().trim(),
                                                        mTxtTodate.getText().toString().trim());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        });
        mTxtTodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(
                        UserAddProductActivity.this, Todatedate, myCalendar
                        .get(Calendar.YEAR), myCalendar
                        .get(Calendar.MONTH), myCalendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
                dialog.getDatePicker().setMinDate(
                        myCalendar.getTimeInMillis());

//                new DatePickerDialog(UserAddProductActivity.this, Todatedate, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mTxtfromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        UserAddProductActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar
                        .get(Calendar.MONTH), myCalendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
                dialog.getDatePicker().setMinDate(
                        myCalendar.getTimeInMillis());

            }
        });
//        mTxtTodate.foc(false);
//        mTxtfromDate.setEnabled(false);
        mSpinnerProductCategoryList.setOnItemSelectedListener(listenerProduct);
        mSpinnerProductList.setOnItemSelectedListener(listenerProductID);
        mimgfromcamerandgallery = findViewById(R.id.imgfromcamerandgallery1);
        mimgfromcamerandgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = Utility.checkPermission(UserAddProductActivity.this);
                if (result == true) {
                    selectImage();
                }
            }
        });

    }

    private void calladdproductapi(String desc, String quality, String price, String fromdate, String todate) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(UserAddProductActivity.this);
        progressDialog.show();

        JsonObject mainJsonObject = new JsonObject();
        mainJsonObject.addProperty(ApplicationConstatnt.Request_app_version, "1.0");
        JsonObject objAuth = new JsonObject();
        objAuth.addProperty(ApplicationConstatnt.Request_type, "user");
        objAuth.addProperty(ApplicationConstatnt.Request_token, PrefUtils.getFromPrefs(mContext, "userToken", ""));
        mainJsonObject.add(ApplicationConstatnt.Request_Auth, objAuth);
        JsonObject objMethod = new JsonObject();
        objMethod.addProperty(ApplicationConstatnt.Request_name, "AddUserProduct");

        mainJsonObject.add(ApplicationConstatnt.Request_method, objMethod);
        JsonObject objParameter = new JsonObject();
        objMethod.add(ApplicationConstatnt.Request_parameters, objParameter);
        objParameter.addProperty("user_id", PrefUtils.getFromPrefs(mContext, "user_id", ""));
        objParameter.addProperty("user_type_id", PrefUtils.getFromPrefs(mContext, "UserTYPE_ID", ""));
        objParameter.addProperty("product_id", productID);
        objParameter.addProperty("name", mSpinnerProductList.getSelectedItem().toString().trim());
        objParameter.addProperty("description", desc);

        objParameter.addProperty("from_date", fromdate);
        objParameter.addProperty("to_date", todate);
        objParameter.addProperty("quantity_id", "1");
        objParameter.addProperty("quality", quality);
        objParameter.addProperty("price", price);


        apiService.getAddProduct(mainJsonObject)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserAddProductFetchRespose>() {
                    @Override
                    public void onSuccess(UserAddProductFetchRespose loginResponse) {
                        progressDialog.dismiss();

                        if (loginResponse.getResponse().getData().getSuccess()) {
                            ApplicationConstatnt.getDialog(mContext, "", loginResponse.getResponse().getData().getMessage());
                            mEditproductDescription.setText("");
                            mEditproductQuality.setText("");
                            mEditprice.setText("");

                            mTxtfromDate.setText("");
                            mTxtTodate.setText("");
                        } else {
                            ApplicationConstatnt.getDialog(mContext, "", loginResponse.getResponse().getData().getMessage());

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getproductcategoryfromAPI() {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(UserAddProductActivity.this);
        progressDialog.show();

        apiService.getfetch_categories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CategoryResponse>() {
                    @Override
                    public void onSuccess(CategoryResponse userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            categoryList.clear();
                            categoryList = userTypeResponse.getData();
                            CategoryResponseData userTypeDatatest = new CategoryResponseData();
                            userTypeDatatest.setName("Select Category");
                            categoryList.add(0, userTypeDatatest);
                            ArrayAdapter<CategoryResponseData> adapter = new ArrayAdapter<CategoryResponseData>(mContext, R.layout.spinner_item_textcolorblck, categoryList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinnerProductCategoryList.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void getproductfromAPI(String id) {

        try {
            JsonObject mainJsonObject = new JsonObject();
            mainJsonObject.addProperty(ApplicationConstatnt.Request_app_version, "1.0");
            JsonObject objAuth = new JsonObject();
            objAuth.addProperty(ApplicationConstatnt.Request_type, "user");
            objAuth.addProperty(ApplicationConstatnt.Request_token, PrefUtils.getFromPrefs(mContext, "userToken", ""));
            mainJsonObject.add(ApplicationConstatnt.Request_Auth, objAuth);
            JsonObject objMethod = new JsonObject();

            JsonObject objParameter = new JsonObject();
            objParameter.addProperty("category_id", id);


            objMethod.addProperty(ApplicationConstatnt.Request_name, "FetchProductByCategory");
            mainJsonObject.add(ApplicationConstatnt.Request_method, objMethod);
            objMethod.add(ApplicationConstatnt.Request_parameters, objParameter);


            final ProgressDialog progressDialog = CustomProgressDialog.ctor(UserAddProductActivity.this);
            progressDialog.show();

            apiService.getuserproductcategory(mainJsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<UserAddProductFetchRespose>() {
                        @Override
                        public void onSuccess(UserAddProductFetchRespose loginResponse) {
                            progressDialog.dismiss();

                            if (loginResponse != null) {
                                mProductList.clear();
                                mProductList = loginResponse.getResponse().getData().getData();
                                Datum userTypeDatatest = new Datum();
                                userTypeDatatest.setName("Select Product");
                                mProductList.add(0, userTypeDatatest);
                                ArrayAdapter<Datum> adapter = new ArrayAdapter<Datum>(mContext, R.layout.spinner_item_textcolorblck, mProductList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mSpinnerProductList.setAdapter(adapter);

                            } else {
                                Toast.makeText(mContext, "Error ", Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onError(Throwable e) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationConstatnt.toast(mContext, "Somthing went wrong");

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserAddProductActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(UserAddProductActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mimgfromcamerandgallery.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mimgfromcamerandgallery.setImageBitmap(thumbnail);
    }

    private void updateLabelTodatedate() {
        String myFormat = "yy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mTxtTodate.setText("" + sdf.format(myCalendar.getTime()));
    }

    private void updateLabel() {
        String myFormat = "yy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mTxtfromDate.setText("" + sdf.format(myCalendar.getTime()));

    }
}
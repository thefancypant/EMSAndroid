package com.maintenancesolution.ems.Views;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.maintenancesolution.R;
import com.maintenancesolution.ems.Models.GenericResponse;
import com.maintenancesolution.ems.Models.Order;
import com.maintenancesolution.ems.Network.NetworkContract;
import com.maintenancesolution.ems.Network.NetworkService;
import com.maintenancesolution.ems.Utils.FileUtils;
import com.maintenancesolution.ems.Utils.GeneralUtils;
import com.maintenancesolution.ems.Utils.PreferenceUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectImagesActivity extends AppCompatActivity {


    private static int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    final String dir = Environment.getExternalStoragePublicDirectory(".Consulting") + "/Folder/";
    @BindView(R.id.imageViewBeforeOne)
    ImageView imageViewBeforeOne;
    @BindView(R.id.imageViewBeforeTwo)
    ImageView imageViewBeforeTwo;
    @BindView(R.id.imageViewBeforeThree)
    ImageView imageViewBeforeThree;
    @BindView(R.id.imageViewBeforeFour)
    ImageView imageViewBeforeFour;
    @BindView(R.id.imageViewBeforeFive)
    ImageView imageViewBeforeFive;
    @BindView(R.id.imageViewBeforeSix)
    ImageView imageViewBeforeSix;
    @BindView(R.id.imageViewBeforeSeven)
    ImageView imageViewBeforeSeven;
    @BindView(R.id.imageViewBeforeEight)
    ImageView imageViewBeforeEight;
    @BindView(R.id.beforeLayout)
    ConstraintLayout beforeLayout;
    @BindView(R.id.imageViewAfterOne)
    ImageView imageViewAfterOne;
    @BindView(R.id.imageViewAfterTwo)
    ImageView imageViewAfterTwo;
    @BindView(R.id.imageViewAfterThree)
    ImageView imageViewAfterThree;
    @BindView(R.id.imageViewAfterFour)
    ImageView imageViewAfterFour;
    @BindView(R.id.imageViewAfterFive)
    ImageView imageViewAfterFive;
    @BindView(R.id.imageViewAfterSix)
    ImageView imageViewAfterSix;
    @BindView(R.id.imageViewAfterSeven)
    ImageView imageViewAfterSeven;
    @BindView(R.id.imageViewAfterEight)
    ImageView imageViewAfterEight;
    @BindView(R.id.afterLayout)
    ConstraintLayout afterLayout;
    @BindView(R.id.buttonDone)
    Button buttonDone;
    @BindView(R.id.imageViewBeforeOneIcon)
    ImageView imageViewBeforeOneIcon;
    @BindView(R.id.imageViewBeforeTwoIcon)
    ImageView imageViewBeforeTwoIcon;
    @BindView(R.id.imageViewBeforeThreeIcon)
    ImageView imageViewBeforeThreeIcon;
    @BindView(R.id.imageViewBeforeFourIcon)
    ImageView imageViewBeforeFourIcon;
    @BindView(R.id.imageViewBeforeFiveIcon)
    ImageView imageViewBeforeFiveIcon;
    @BindView(R.id.imageViewBeforeSixIcon)
    ImageView imageViewBeforeSixIcon;
    @BindView(R.id.imageViewBeforeSevenIcon)
    ImageView imageViewBeforeSevenIcon;
    @BindView(R.id.imageViewBeforeEightIcon)
    ImageView imageViewBeforeEightIcon;
    @BindView(R.id.imageViewAfterOneIcon)
    ImageView imageViewAfterOneIcon;
    @BindView(R.id.imageViewAfterTwoIcon)
    ImageView imageViewAfterTwoIcon;
    @BindView(R.id.imageViewAfterThreeIcon)
    ImageView imageViewAfterThreeIcon;
    @BindView(R.id.imageViewAfterFourIcon)
    ImageView imageViewAfterFourIcon;
    @BindView(R.id.imageViewAfterFiveIcon)
    ImageView imageViewAfterFiveIcon;
    @BindView(R.id.imageViewAfterSixIcon)
    ImageView imageViewAfterSixIcon;
    @BindView(R.id.imageViewAfterSevenIcon)
    ImageView imageViewAfterSevenIcon;
    @BindView(R.id.imageViewAfterEightIcon)
    ImageView imageViewAfterEightIcon;
    MultipartBody.Part partBOne;
    MultipartBody.Part partBTwo;
    MultipartBody.Part partBThree;
    MultipartBody.Part partBFour;
    MultipartBody.Part partBFive;
    MultipartBody.Part partBSix;
    MultipartBody.Part partBSeven;
    MultipartBody.Part partBEight;
    MultipartBody.Part partAOne;
    MultipartBody.Part partATwo;
    MultipartBody.Part partAThree;
    MultipartBody.Part partAFour;
    MultipartBody.Part partAFive;
    MultipartBody.Part partASix;
    MultipartBody.Part partASeven;
    MultipartBody.Part partAEight;
    private File compressedFileBOne;
    private File compressedFileBTwo;
    private File compressedFileBThree;
    private File compressedFileBFour;
    private File compressedFileBFive;
    private File compressedFileBSix;
    private File compressedFileBSeven;
    private File compressedFileBEight;
    private File compressedFileAOne;
    private File compressedFileATwo;
    private File compressedFileAThree;
    private File compressedFileAFour;
    private File compressedFileAFive;
    private File compressedFileASix;
    private File compressedFileASeven;
    private File compressedFileAEight;
    private ImageView selectedImageView;
    private String cameraImage;
    private File cameraFile;
    private File cameraCompressedFile;
    private File galleryFile;
    private File galleryCompressedFile;
    private PreferenceUtils preferenceUtils;
    private String header;
    private Order order;
    private ConstraintLayout constraint_layout;
    private ProgressBar mProgressView;
    private boolean progress;
    private int MY_PERMISSIONS_GALLERY = 2;
    private int MY_PERMISSIONS_CAMERA = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_images);
        ButterKnife.bind(this);

        order = getIntent().getParcelableExtra("Order");
        getWork(order.getId());
        /*mProgressView = (ProgressBar) findViewById(R.id.list_progress_bar);
        mProgressView.setVisibility(View.VISIBLE);*/


    }

    private void setUI(Order order) {

        if (order.getBeforePhoto1() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getBeforePhoto1())
                    .into(imageViewBeforeOne);
            imageViewBeforeOneIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getBeforePhoto2() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getBeforePhoto2())
                    .into(imageViewBeforeTwo);
            imageViewBeforeTwoIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getBeforePhoto3() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getBeforePhoto3())
                    .into(imageViewBeforeThree);
            imageViewBeforeThreeIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getBeforePhoto4() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getBeforePhoto4())
                    .into(imageViewBeforeFour);
            imageViewBeforeFourIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getBeforePhoto5() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getBeforePhoto5())
                    .into(imageViewBeforeFive);
            imageViewBeforeFiveIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getBeforePhoto6() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getBeforePhoto6())
                    .into(imageViewBeforeSix);
            imageViewBeforeSixIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getBeforePhoto7() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getBeforePhoto7())
                    .into(imageViewBeforeSeven);
            imageViewBeforeSevenIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getBeforePhoto8() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getBeforePhoto8())
                    .into(imageViewBeforeEight);
            imageViewBeforeEightIcon.setVisibility(View.INVISIBLE);
        }


        if (order.getAfterPhoto1() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getAfterPhoto1())
                    .into(imageViewAfterOne);
            imageViewAfterOneIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getAfterPhoto2() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getAfterPhoto2())
                    .into(imageViewAfterTwo);
            imageViewAfterTwoIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getAfterPhoto3() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getAfterPhoto3())
                    .into(imageViewAfterThree);
            imageViewAfterThreeIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getAfterPhoto4() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getAfterPhoto4())
                    .into(imageViewAfterFour);
            imageViewAfterFourIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getAfterPhoto5() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getAfterPhoto5())
                    .into(imageViewAfterFive);
            imageViewAfterFiveIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getAfterPhoto6() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getAfterPhoto6())
                    .into(imageViewAfterSix);
            imageViewAfterSixIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getAfterPhoto7() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getAfterPhoto7())
                    .into(imageViewAfterSeven);
            imageViewAfterSevenIcon.setVisibility(View.INVISIBLE);
        }
        if (order.getAfterPhoto8() != null) {
            Picasso.with(getApplicationContext())
                    .load(NetworkContract.BASE_URL + order.getAfterPhoto8())
                    .into(imageViewAfterEight);
            imageViewAfterEightIcon.setVisibility(View.INVISIBLE);
        }
    }

    //@O
    @OnClick({R.id.imageViewBeforeOne
            , R.id.imageViewBeforeTwo
            , R.id.imageViewBeforeThree
            , R.id.imageViewBeforeFour
            , R.id.imageViewBeforeFive
            , R.id.imageViewBeforeSix
            , R.id.imageViewBeforeSeven
            , R.id.imageViewBeforeEight
            , R.id.imageViewAfterOne
            , R.id.imageViewAfterTwo
            , R.id.imageViewAfterThree
            , R.id.imageViewAfterFour
            , R.id.imageViewAfterFive
            , R.id.imageViewAfterSix
            , R.id.imageViewAfterSeven
            , R.id.imageViewAfterEight})
    public void click(ImageView imageView) {
        Log.d("id", "doSomething: clicked ID" + Integer.toString(imageView.getId()));
        Log.d("id", "doSomething: firstitem ID" + Integer.toString(R.id.imageViewBeforeOne));

        switch (imageView.getId()) {
            case R.id.imageViewBeforeOne:
                selectedImageView = imageViewBeforeOne;
                showPickImageDialog();


                break;
            case R.id.imageViewBeforeTwo:
                selectedImageView = imageViewBeforeTwo;
                showPickImageDialog();

                break;
            case R.id.imageViewBeforeThree:
                selectedImageView = imageViewBeforeThree;

                showPickImageDialog();
                break;
            case R.id.imageViewBeforeFour:
                selectedImageView = imageViewBeforeFour;

                showPickImageDialog();
                break;
            case R.id.imageViewBeforeFive:
                selectedImageView = imageViewBeforeFive;

                showPickImageDialog();
                break;
            case R.id.imageViewBeforeSix:
                selectedImageView = imageViewBeforeSix;

                showPickImageDialog();
                break;
            case R.id.imageViewBeforeSeven:
                selectedImageView = imageViewBeforeSeven;

                showPickImageDialog();
                break;
            case R.id.imageViewBeforeEight:
                selectedImageView = imageViewBeforeEight;

                showPickImageDialog();
                break;

            case R.id.imageViewAfterOne:
                selectedImageView = imageViewAfterOne;
                showPickImageDialog();


                break;
            case R.id.imageViewAfterTwo:
                selectedImageView = imageViewAfterTwo;
                showPickImageDialog();

                break;
            case R.id.imageViewAfterThree:
                selectedImageView = imageViewAfterThree;

                showPickImageDialog();
                break;
            case R.id.imageViewAfterFour:
                selectedImageView = imageViewAfterFour;

                showPickImageDialog();
                break;
            case R.id.imageViewAfterFive:
                selectedImageView = imageViewAfterFive;

                showPickImageDialog();
                break;
            case R.id.imageViewAfterSix:
                selectedImageView = imageViewAfterSix;

                showPickImageDialog();
                break;
            case R.id.imageViewAfterSeven:
                selectedImageView = imageViewAfterSeven;

                showPickImageDialog();
                break;
            case R.id.imageViewAfterEight:
                selectedImageView = imageViewAfterEight;

                showPickImageDialog();
                break;

        }
    }

    private void showPickImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(SelectImagesActivity.this);
        builderSingle.setTitle("Select One Option");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                SelectImagesActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Gallery");
        arrayAdapter.add("Camera");
        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            /*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto , 1);//one can be replaced with any action code*/
                            /*Intent pickPhoto = new Intent();
                            pickPhoto.setType("image*//*");
                            pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(pickPhoto,"Select a picture"), 1);*/
                            //Picking image from gallery
                            if (ContextCompat
                                    .checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat
                                        .requestPermissions(SelectImagesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_GALLERY);
                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select a Picture"), 1);
                            }
                        }
                        if (which == 1) {
                            //Getting Image from camera
                            /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);//zero can be replaced with any action code*/

                            if (ContextCompat
                                    .checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat
                                        .requestPermissions(SelectImagesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_CAMERA);
                            } else {
                                cameraImage = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".png";
                                File folder = new File(dir);
                                cameraFile = new File(cameraImage);
                                try {
                                    folder.mkdirs();
                                    cameraFile.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // Uri outputFileUri = Uri.fromFile(cameraFile);
                                Uri outputFileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", cameraFile);
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                startActivityForResult(cameraIntent, 0);
                            }
                        }
                    }
                });
        builderSingle.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode != 1) {

            Log.i("Demo Pic", Long.toString(cameraFile.getTotalSpace()));
            try {
                cameraCompressedFile = new Compressor(getApplicationContext())
                        .setQuality(50)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG).compressToFile(cameraFile);
                System.gc();

                Log.i("Demo Pic", Long.toString(cameraCompressedFile.length()));
            } catch (IOException e) {
                e.printStackTrace();
            }


            switch (selectedImageView.getId()) {

                //////////Before Camera Images////////////
                case R.id.imageViewBeforeOne:

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeOne);
                    imageViewBeforeOneIcon.setVisibility(View.INVISIBLE);
                    compressedFileBOne = cameraCompressedFile;


                    break;
                case R.id.imageViewBeforeTwo:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeTwo);
                    imageViewBeforeTwoIcon.setVisibility(View.INVISIBLE);

                    compressedFileBTwo = cameraCompressedFile;

                    break;
                case R.id.imageViewBeforeThree:

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeThree);
                    imageViewBeforeThreeIcon.setVisibility(View.INVISIBLE);
                    compressedFileBThree = cameraCompressedFile;

                    break;
                case R.id.imageViewBeforeFour:

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeFour);
                    imageViewBeforeFourIcon.setVisibility(View.INVISIBLE);
                    compressedFileBFour = cameraCompressedFile;

                    break;
                case R.id.imageViewBeforeFive:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeFive);
                    imageViewBeforeFiveIcon.setVisibility(View.INVISIBLE);
                    compressedFileBFive = cameraCompressedFile;
                    break;
                case R.id.imageViewBeforeSix:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeSix);
                    imageViewBeforeSixIcon.setVisibility(View.INVISIBLE);
                    compressedFileBSix = cameraCompressedFile;
                    break;
                case R.id.imageViewBeforeSeven:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeSeven);
                    imageViewBeforeSevenIcon.setVisibility(View.INVISIBLE);
                    compressedFileBSeven = cameraCompressedFile;
                    break;
                case R.id.imageViewBeforeEight:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeEight);
                    imageViewBeforeEightIcon.setVisibility(View.INVISIBLE);
                    compressedFileBEight = cameraCompressedFile;
                    break;

                ////////After Camera images//////////
                case R.id.imageViewAfterOne:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterOne);
                    imageViewAfterOneIcon.setVisibility(View.INVISIBLE);
                    compressedFileAOne = cameraCompressedFile;


                    break;
                case R.id.imageViewAfterTwo:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterTwo);
                    imageViewAfterTwoIcon.setVisibility(View.INVISIBLE);
                    compressedFileATwo = cameraCompressedFile;

                    break;
                case R.id.imageViewAfterThree:

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterThree);
                    imageViewAfterThreeIcon.setVisibility(View.INVISIBLE);
                    compressedFileAThree = cameraCompressedFile;

                    break;
                case R.id.imageViewAfterFour:

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterFour);
                    imageViewAfterFourIcon.setVisibility(View.INVISIBLE);
                    compressedFileAFour = cameraCompressedFile;

                    break;
                case R.id.imageViewAfterFive:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterFive);
                    imageViewAfterFiveIcon.setVisibility(View.INVISIBLE);

                    compressedFileAFive = cameraCompressedFile;
                    break;
                case R.id.imageViewAfterSix:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterSix);
                    imageViewAfterSixIcon.setVisibility(View.INVISIBLE);

                    compressedFileASix = cameraCompressedFile;
                    break;

                case R.id.imageViewAfterSeven:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterSeven);
                    imageViewAfterSevenIcon.setVisibility(View.INVISIBLE);

                    compressedFileASeven = cameraCompressedFile;
                    break;
                case R.id.imageViewAfterEight:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterEight);
                    imageViewAfterEightIcon.setVisibility(View.INVISIBLE);

                    compressedFileAEight = cameraCompressedFile;
                    break;
            }


        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            final InputStream imageStream;

            Uri uriPhoto = data.getData();
            String imageFilePath = FileUtils.getPath(SelectImagesActivity.this, uriPhoto);

            galleryFile = new File(imageFilePath);
            Log.d("OrderDetail", "onActivityResult: " + String.valueOf(galleryFile.length()));

            try {
                galleryCompressedFile = new Compressor(getApplicationContext())
                        .setQuality(50)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG).compressToFile(galleryFile);
                System.gc();
                Log.i("Demo Pic", Long.toString(galleryCompressedFile.length()));
            } catch (Exception e) {
            }
            //compressedBitmap = BitmapFactory.decodeFile(compressedFile.getAbsolutePath());

            switch (selectedImageView.getId()) {

                //////////Before Gallery Images//////////
                case R.id.imageViewBeforeOne:
                    imageViewBeforeOneIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeOne);
                    compressedFileBOne = galleryCompressedFile;


                    break;
                case R.id.imageViewBeforeTwo:
                    imageViewBeforeTwoIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeTwo);
                    compressedFileBTwo = galleryCompressedFile;

                    break;
                case R.id.imageViewBeforeThree:
                    imageViewBeforeThreeIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeThree);
                    compressedFileBThree = galleryCompressedFile;

                    break;
                case R.id.imageViewBeforeFour:
                    imageViewBeforeFourIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeFour);
                    compressedFileBFour = galleryCompressedFile;

                    break;
                case R.id.imageViewBeforeFive:
                    imageViewBeforeFiveIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeFive);
                    compressedFileBFive = galleryCompressedFile;
                    break;
                case R.id.imageViewBeforeSix:
                    imageViewBeforeSixIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeSix);
                    compressedFileBSix = galleryCompressedFile;
                    break;
                case R.id.imageViewBeforeSeven:
                    imageViewBeforeSevenIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeSeven);
                    compressedFileBSeven = galleryCompressedFile;
                    break;
                case R.id.imageViewBeforeEight:
                    imageViewBeforeEightIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeEight);
                    compressedFileBEight = galleryCompressedFile;
                    break;

                /////////After Gallery Images///////////
                case R.id.imageViewAfterOne:
                    imageViewAfterOneIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterOne);
                    compressedFileAOne = galleryCompressedFile;

                    break;
                case R.id.imageViewAfterTwo:
                    imageViewAfterTwoIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterTwo);
                    compressedFileATwo = galleryCompressedFile;

                    break;
                case R.id.imageViewAfterThree:
                    imageViewAfterThreeIcon.setVisibility(View.INVISIBLE);

                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterThree);
                    compressedFileAThree = galleryCompressedFile;

                    break;
                case R.id.imageViewAfterFour:
                    imageViewAfterFourIcon.setVisibility(View.INVISIBLE);

                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterFour);
                    compressedFileAFour = galleryCompressedFile;

                    break;
                case R.id.imageViewAfterFive:
                    imageViewAfterFiveIcon.setVisibility(View.INVISIBLE);

                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterFive);
                    compressedFileAFive = galleryCompressedFile;
                    break;
                case R.id.imageViewAfterSix:
                    imageViewAfterSixIcon.setVisibility(View.INVISIBLE);

                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterSix);
                    compressedFileASix = galleryCompressedFile;
                    break;

                case R.id.imageViewAfterSeven:
                    imageViewAfterSevenIcon.setVisibility(View.INVISIBLE);

                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterSeven);
                    compressedFileASeven = galleryCompressedFile;
                    break;
                case R.id.imageViewAfterEight:
                    imageViewAfterEightIcon.setVisibility(View.INVISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterEight);
                    compressedFileAEight = galleryCompressedFile;
                    break;
            }

            selectedImageView = null;
        }


    }

    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(SelectImagesActivity.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }

    @OnClick(R.id.buttonDone)
    public void checkImages() {
        makeNetworkParts();

        if (partBOne == null
                && partBTwo == null
                && partBThree == null
                && partBFour == null
                && partBFive == null
                && partBSix == null
                && partBSeven == null
                && partBEight == null
                && partAOne == null
                && partATwo == null
                && partAThree == null
                && partAFour == null
                && partAFive == null
                && partASix == null
                && partASeven == null
                && partAEight == null) {
            Toast.makeText(getApplicationContext(), "noBone", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(SelectImagesActivity.this, CustomerFeedbackActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            intent.putExtra("Order", order);
            startActivity(intent);
        } else {

            sendImages();
        }

    }

    public void sendImages() {
        getPrefUtils();

        showProgress(true);

        /*RequestBody imageFileBody =
                RequestBody.create(MediaType.parse("image*//*"), compressedFileBOne);
        MultipartBody.Part icon = MultipartBody
                .Part
                .createFormData("photo1", compressedFileBOne.getName(), imageFileBody);*/
        NetworkService
                .getInstance()
                .postBeforeImagesNetwork(header
                        , order.getId()
                        , partBOne
                        , partBTwo
                        , partBThree
                        , partBFour
                        , partBFive
                        , partBSix
                        , partBSeven
                        , partBEight
                        , partAOne
                        , partATwo
                        , partAThree
                        , partAFour
                        , partAFive
                        , partASix
                        , partASeven
                        , partAEight)
                .enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        Log.d("SendImage", "onResponse: ");
                        processSendImages(response);
                    }


                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        showProgress(false);
                        if (t.toString().equals("java.lang.IllegalStateException: Multipart body must have at least one part.")) {

                            AlertDialog alertDialog = new AlertDialog.Builder(SelectImagesActivity.this).create();
                            alertDialog.setTitle("Failure");
                            alertDialog.setMessage("Please select atleast one image");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(SelectImagesActivity.this).create();
                            alertDialog.setTitle("Failure");
                            alertDialog.setMessage("We are sorry, Something went wrong, please check your connection and try again.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //
                                        }
                                    });
                            alertDialog.show();
                        }
                        Log.d("SendImage", "onFailure: " + t.toString());
                    }
                });

        /*NetworkService
                .getInstance()
                .postAfterImagesNetwork(header
                        , 49
                        , partAOne
                        , partATwo
                        , partAThree
                        , partAFour
                        ,partAFive
                        ,partASix
                        ,partASeven
                        ,partAEight)
                .enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, retrofit2.Response<GenericResponse> response) {
                        Log.d("SendImage", "onResponse: ");
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        Log.d("SendImage", "onFailure: ");
                    }
                });*/

    }

    private void processSendImages(Response<GenericResponse> response) {
        if (response.body().getMessage().equals("Success")) {
            showProgress(false);
            Intent intent = new Intent(SelectImagesActivity.this, CustomerFeedbackActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            intent.putExtra("Order", order);
            startActivity(intent);
        }
    }

    public void makeNetworkParts() {
        if (compressedFileBOne != null) {
            RequestBody requestBodyBOne =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileBOne);

            partBOne = MultipartBody
                    .Part
                    .createFormData("before_photo1", compressedFileBOne.getName(), requestBodyBOne);
        }

        if (compressedFileBTwo != null) {
            RequestBody requestBodyBTwo =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileBTwo);
            partBTwo = MultipartBody
                    .Part
                    .createFormData("before_photo2", compressedFileBTwo.getName(), requestBodyBTwo);
        }

        if (compressedFileBThree != null) {
            RequestBody requestBodyBThree =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileBThree);
            partBThree = MultipartBody
                    .Part
                    .createFormData("before_photo3", compressedFileBThree.getName(), requestBodyBThree);
        }

        if (compressedFileBFour != null) {
            RequestBody requestBodyBFour =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileBFour);
            partBFour = MultipartBody
                    .Part
                    .createFormData("before_photo4", compressedFileBFour.getName(), requestBodyBFour);
        }

        if (compressedFileBFive != null) {
            RequestBody requestBodyBFive =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileBFive);
            partBFive = MultipartBody
                    .Part
                    .createFormData("before_photo5", compressedFileBFive.getName(), requestBodyBFive);
        }

        if (compressedFileBSix != null) {
            RequestBody requestBodyBSix =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileBSix);
            partBSix = MultipartBody
                    .Part
                    .createFormData("before_photo6", compressedFileBSix.getName(), requestBodyBSix);
        }

        if (compressedFileBSeven != null) {
            RequestBody requestBodyBSeven =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileBSeven);
            partBSeven = MultipartBody
                    .Part
                    .createFormData("before_photo7", compressedFileBSeven.getName(), requestBodyBSeven);
        }

        if (compressedFileBEight != null) {
            RequestBody requestBodyBEight =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileBEight);
            partBEight = MultipartBody
                    .Part
                    .createFormData("before_photo8", compressedFileBEight.getName(), requestBodyBEight);
        }


        if (compressedFileAOne != null) {
            RequestBody requestBodyAOne =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileAOne);

            partAOne = MultipartBody
                    .Part
                    .createFormData("after_photo1", compressedFileAOne.getName(), requestBodyAOne);
        }

        if (compressedFileATwo != null) {
            RequestBody requestBodyATwo =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileATwo);
            partATwo = MultipartBody
                    .Part
                    .createFormData("after_photo2", compressedFileATwo.getName(), requestBodyATwo);
        }

        if (compressedFileAThree != null) {
            RequestBody requestBodyAThree =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileAThree);
            partAThree = MultipartBody
                    .Part
                    .createFormData("after_photo3", compressedFileAThree.getName(), requestBodyAThree);
        }

        if (compressedFileAFour != null) {
            RequestBody requestBodyAFour =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileAFour);
            partAFour = MultipartBody
                    .Part
                    .createFormData("after_photo4", compressedFileAFour.getName(), requestBodyAFour);
        }

        if (compressedFileAFive != null) {
            RequestBody requestBodyAFive =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileAFive);
            partAFive = MultipartBody
                    .Part
                    .createFormData("after_photo5", compressedFileAFive.getName(), requestBodyAFive);
        }

        if (compressedFileASix != null) {
            RequestBody requestBodyASix =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileASix);
            partASix = MultipartBody
                    .Part
                    .createFormData("after_photo6", compressedFileASix.getName(), requestBodyASix);
        }

        if (compressedFileASeven != null) {
            RequestBody requestBodyASeven =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileASeven);
            partASeven = MultipartBody
                    .Part
                    .createFormData("after_photo7", compressedFileASeven.getName(), requestBodyASeven);
        }

        if (compressedFileAEight != null) {
            RequestBody requestBodyAEight =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileAEight);
            partAEight = MultipartBody
                    .Part
                    .createFormData("after_photo8", compressedFileAEight.getName(), requestBodyAEight);
        }


    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        // Setup Progress View
        //progress = !show;
        constraint_layout = findViewById(R.id.list_constraint_layout);
        mProgressView = findViewById(R.id.list_progress_bar);
        if (show) {
            constraint_layout.setVisibility(View.GONE);
            mProgressView.setVisibility(View.VISIBLE);
            mProgressView.animate();
        } else {
            constraint_layout.setVisibility(View.VISIBLE);
            mProgressView.animate().cancel();
            mProgressView.setVisibility(View.GONE);

        }
    }
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            constraint_layout.setVisibility(show ? View.GONE : View.VISIBLE);
            constraint_layout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    constraint_layout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            constraint_layout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                //Camera Permission result
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    cameraImage = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".png";
                    File folder = new File(dir);
                    cameraFile = new File(cameraImage);
                    try {
                        folder.mkdirs();
                        cameraFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Uri outputFileUri = Uri.fromFile(cameraFile);
                    Uri outputFileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", cameraFile);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(cameraIntent, 0);


                } else {

                    final AlertDialog alertDialog = new AlertDialog.Builder(SelectImagesActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("We need your permission to upload images.Do you want give this app the permissions?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    alertDialog.dismiss();
                                    if (ContextCompat
                                            .checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat
                                                .requestPermissions(SelectImagesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_CAMERA);
                                    }
                                    //
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
                return;
            }

            case 2: {

                //Gallery Permission result
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select a Picture"), 1);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    final AlertDialog alertDialog = new AlertDialog.Builder(SelectImagesActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("We need your permission to upload images.Do you want give this app the permissions?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    alertDialog.dismiss();
                                    if (ContextCompat
                                            .checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat
                                                .requestPermissions(SelectImagesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_GALLERY);
                                    }
                                    //
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        final String dire = Environment.getExternalStoragePublicDirectory(".Consulting") + "/Folder/";

        File file = new File(dire);
        new GeneralUtils(SelectImagesActivity.this).deleteTempFolder(file);


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // getWork(order.getId());

    }

    private void getWork(Integer id) {
        NetworkService
                .getInstance()
                .getSelectedUserWork(header, id)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        processSelectedUser(response);
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });

    }

    private void processSelectedUser(Response<Order> response) {
        order = response.body();
        setUI(order);
    }
}


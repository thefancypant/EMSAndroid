package com.android.maintenancesolution.Views;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.android.maintenancesolution.Models.GenericResponse;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.R;
import com.android.maintenancesolution.Utils.FileUtils;
import com.android.maintenancesolution.Utils.PreferenceUtils;
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

public class SelectImagesActivity extends AppCompatActivity {


    private static int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    final String dir = Environment.getExternalStoragePublicDirectory(".HopConsulting") + "/Folder/";
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_images);
        ButterKnife.bind(this);


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
                                        .requestPermissions(SelectImagesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select a Picture"), 1);

                        }
                        if (which == 1) {
                            //Getting Image from camera
                            /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);//zero can be replaced with any action code*/

                            if (ContextCompat
                                    .checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat
                                        .requestPermissions(SelectImagesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }

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
                    compressedFileBOne = cameraCompressedFile;


                    break;
                case R.id.imageViewBeforeTwo:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeTwo);
                    compressedFileBTwo = cameraCompressedFile;

                    break;
                case R.id.imageViewBeforeThree:

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeThree);
                    compressedFileBThree = cameraCompressedFile;

                    break;
                case R.id.imageViewBeforeFour:

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeFour);
                    compressedFileBFour = cameraCompressedFile;

                    break;
                case R.id.imageViewBeforeFive:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeFive);
                    compressedFileBFive = cameraCompressedFile;
                    break;
                case R.id.imageViewBeforeSix:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeSix);
                    compressedFileBSix = cameraCompressedFile;
                    break;
                case R.id.imageViewBeforeSeven:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeSeven);
                    compressedFileBSeven = cameraCompressedFile;
                    break;
                case R.id.imageViewBeforeEight:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewBeforeSeven);
                    compressedFileBSeven = cameraCompressedFile;
                    break;

                ////////After Camera images//////////
                case R.id.imageViewAfterOne:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterOne);
                    compressedFileAOne = cameraCompressedFile;


                    break;
                case R.id.imageViewAfterTwo:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterTwo);
                    compressedFileATwo = cameraCompressedFile;

                    break;
                case R.id.imageViewAfterThree:

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterThree);
                    compressedFileAThree = cameraCompressedFile;

                    break;
                case R.id.imageViewAfterFour:

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterFour);
                    compressedFileAFour = cameraCompressedFile;

                    break;
                case R.id.imageViewAfterFive:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterFive);
                    compressedFileAFive = cameraCompressedFile;
                    break;
                case R.id.imageViewAfterSix:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterSix);
                    compressedFileASix = cameraCompressedFile;
                    break;

                case R.id.imageViewAfterSeven:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterSeven);
                    compressedFileASeven = cameraCompressedFile;
                    break;
                case R.id.imageViewAfterEight:
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewAfterSeven);
                    compressedFileASeven = cameraCompressedFile;
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
                Log.i("Demo Pic", Long.toString(galleryCompressedFile.length()));
            } catch (Exception e) {
            }
            //compressedBitmap = BitmapFactory.decodeFile(compressedFile.getAbsolutePath());

            switch (selectedImageView.getId()) {

                //////////Before Gallery Images//////////
                case R.id.imageViewBeforeOne:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeOne);
                    compressedFileBOne = galleryCompressedFile;


                    break;
                case R.id.imageViewBeforeTwo:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeTwo);
                    compressedFileBTwo = galleryCompressedFile;

                    break;
                case R.id.imageViewBeforeThree:

                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeThree);
                    compressedFileBThree = galleryCompressedFile;

                    break;
                case R.id.imageViewBeforeFour:

                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeFour);
                    compressedFileBFour = galleryCompressedFile;

                    break;
                case R.id.imageViewBeforeFive:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeFive);
                    compressedFileBFive = galleryCompressedFile;
                    break;
                case R.id.imageViewBeforeSix:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeSix);
                    compressedFileBSix = galleryCompressedFile;
                    break;
                case R.id.imageViewBeforeSeven:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeSeven);
                    compressedFileBSeven = galleryCompressedFile;
                    break;
                case R.id.imageViewBeforeEight:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewBeforeSeven);
                    compressedFileBSeven = galleryCompressedFile;
                    break;

                /////////After Gallery Images///////////
                case R.id.imageViewAfterOne:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterOne);
                    compressedFileAOne = galleryCompressedFile;

                    break;
                case R.id.imageViewAfterTwo:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterTwo);
                    compressedFileATwo = galleryCompressedFile;

                    break;
                case R.id.imageViewAfterThree:

                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterThree);
                    compressedFileAThree = galleryCompressedFile;

                    break;
                case R.id.imageViewAfterFour:

                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterFour);
                    compressedFileAFour = galleryCompressedFile;

                    break;
                case R.id.imageViewAfterFive:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterFive);
                    compressedFileAFive = galleryCompressedFile;
                    break;
                case R.id.imageViewAfterSix:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterSix);
                    compressedFileASix = galleryCompressedFile;
                    break;

                case R.id.imageViewAfterSeven:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterSeven);
                    compressedFileASeven = galleryCompressedFile;
                    break;
                case R.id.imageViewAfterEight:
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewAfterSeven);
                    compressedFileASeven = galleryCompressedFile;
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
    public void sendImages() {
        getPrefUtils();
        RequestBody imageFileBody =
                RequestBody.create(MediaType.parse("image/*"), compressedFileBOne);
        MultipartBody.Part icon = MultipartBody
                .Part
                .createFormData("photo1", compressedFileBOne.getName(), imageFileBody);
        NetworkService
                .getInstance()
                .postBeforeAfterImagesNetwork(header, 902, icon, null, null, null)
                .enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, retrofit2.Response<GenericResponse> response) {
                        Log.d("SendImage", "onResponse: ");
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        Log.d("SendImage", "onFailure: ");
                    }
                });

    }
}


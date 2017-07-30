package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CreateServiceActivity extends AppCompatActivity implements View.OnClickListener {

    /* This will define the variables that will use in the creation of the service */
    private ImageView serviceImage;
    private EditText servicePrice, serviceUnit, serviceAddress, serviceTitle, serviceDescription;
    private Button serviceBtn;

    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        This will define the fonts calling CalligraphyConfig() method to change the letter font
        defined in the assets/fonts/ directory.
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/MyriadPro-SemiExt.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setContentView(R.layout.activity_create_service);

        /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        /* This will handler the components corresponding of the layout with the variables defined. */
        serviceImage = (ImageView) findViewById(R.id.create_service_image);
        servicePrice = (EditText) findViewById(R.id.create_service_price);
        serviceUnit = (EditText) findViewById(R.id.create_service_unit);
        serviceAddress = (EditText) findViewById(R.id.create_service_address);
        serviceTitle = (EditText) findViewById(R.id.create_service_title);
        serviceDescription = (EditText) findViewById(R.id.create_service_description);
        serviceBtn = (Button) findViewById(R.id.create_service_button);

        serviceImage.setOnClickListener(this);
        serviceBtn.setOnClickListener(this);
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
       case is used for to start the activity corresponding.
           @date[25/07/2017]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [void]
   */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_service_button) {
            Toast.makeText(this, "Click Create service", Toast.LENGTH_LONG).show();

        } else if (v.getId() == R.id.create_service_image) {
            // Declaration of the alert dialog
            AlertDialog.Builder Selector = new AlertDialog.Builder(this, R.style.ListAlertDialogStyle);
            Selector.setTitle(R.string.upload);
            String [] optionsArray = { getString(R.string.camera), getString(R.string.gallery) };
            // This will handler the behavior corresponding to each option.
            Selector.setItems(optionsArray, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            cameraIntent();
                            break;
                        case 1:
                            galleryIntent();
                            break;
                    }
                }
            });
            // This will define and handler the cancel option.
            Selector.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            Selector.create().show();

        } else {
            super.onBackPressed();
        }
    }

    /*  Method that use for call the intent of the device camera activity and execute its.
           @date[26/07/2017]
           @reference [https://developer.android.com/training/camera/photobasics.html]
           @author[ChiSeng Ng]
           @param [None]
           @return [void]
   */
    private void cameraIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Handle the returned image in the Activity "onActivityResult" method.
        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
    }

    /*  Method that use for call the intent of the device gallery activity and execute its.
          @date[26/07/2017]
          @author[ChiSeng Ng]
          @param [None]
          @return [void]
  */
    private void galleryIntent() {

        // Take the user to their chosen image selection app (gallery or file manager)
        Intent pickIntent = new Intent();
        // Select  Android image gallery or a file manager application(the first in this case).
        pickIntent.setType("image/*");
        // By specifying "ACTION_GET_CONTENT", we are instructing the app to return whatever the user selects.
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        // handle the returned image in the Activity "onActivityResult" method.
        startActivityForResult(Intent.createChooser(pickIntent, "Select File"), SELECT_FILE);
    }

    /*  Method that receive the photo encoded into the data intent as a small Bitmap in extras,
    under the key data, and finally call the methods corresponding.
          @date[26/07/2017]
          @author[ChiSeng Ng]
          @param [int] requestCode Integer that identify if the operation was a capture or pick
          photo.
          @param [int] resultCode Integer that identify the state of the capture or pick photo
          operation.
          @return [void]
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    /*  Method that receive the photo encoded into the data intent as a small Bitmap in extras,
    under the key data, and processes it to show them in the screen, this case was capture by the
    camera.
          @date[26/07/2017]
          @author[ChiSeng Ng]
          @param [Intent] data Intent with the photo encoded in a small Bitmap in extras, under the
          key data.
          @return [void]
    */
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert thumbnail != null;
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        serviceImage.setImageBitmap(thumbnail);
    }

    /*  Method that receive the photo encoded into the data intent as a small Bitmap in extras,
    under the key data, and processes it to show them in the screen, this case was obtain in the
    gallery.
          @date[26/07/2017]
          @author[ChiSeng Ng]
          @param [Intent] data Intent with the photo encoded in a small Bitmap in extras, under the
          key data.
          @return [void]
    */
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                serviceImage.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*  Method that use the Calligraphy dependence to attach the base context and to can change the
    letter's font corresponding.
           @date[25/07/2017]
           @reference [https://github.com/chrisjenx/Calligraphy/]
           @author[ChiSeng Ng]
           @param [Context] newBase Base Context of the Parent Activity.
           @return [void]
   */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

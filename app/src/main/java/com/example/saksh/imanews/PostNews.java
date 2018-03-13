package com.example.saksh.imanews;


import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostNews extends Fragment {


    EditText editText;
    FirebaseAuth auth;
    Bitmap photo;
    String urlImage, username;
    StorageReference storageReference;
    ImageView imageView;
    Button uploadbutton, submit;
    DatabaseReference newsDatabase;
    FirebaseDatabase database;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Location location;
    Geocoder geocoder;
    static String fulladdress;
    FirebaseUser user;
    String id1;
    List<android.location.Address> addresses;
    public Double userlatitude, userlongitude;
    EditText Title, Decritption;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public Uri fileUri, uri;
    public File imageFile;
    private final static int CameraRequestCode = 234;

    public PostNews() {
        // Required empty public constructor
    }

    public static String getFulladdress() {
        return fulladdress;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        newsDatabase = database.getReference("News");
        storageReference = FirebaseStorage.getInstance().getReference();
        setLoc();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_post_news, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Initializing*/
        uploadbutton = (Button) view.findViewById(R.id.uploadbutton);
        submit = (Button) view.findViewById(R.id.submit);
        Title = (EditText) view.findViewById(R.id.titleNews);
        Decritption = (EditText) view.findViewById(R.id.decpNews);
        /*Listeners*/
        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, CameraRequestCode);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNews();
            }
        });
    }


    void setLoc() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                geocoder = new Geocoder(getContext(), Locale.getDefault());
                userlatitude = location.getLatitude();
                userlongitude = location.getLongitude();
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    fulladdress = addresses.get(0).getAddressLine(0);
                    MyNews myNews = new MyNews();
                    myNews.setAddressofNews(fulladdress);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void addNews()
    {
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        username=user.getDisplayName();
        String titleNews = Title.getText().toString().trim();
        String decpNews = Decritption.getText().toString().trim();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        Date date = new Date();
        String uploadDate = dateFormat.format(date);
        String addr=fulladdress;
        String url=urlImage;
        id1=newsDatabase.push().getKey();
        MyNews myNews=new MyNews(titleNews,decpNews,uploadDate,addr,url,user.getDisplayName());
        newsDatabase.child(id1).setValue(myNews);
        Toast.makeText(getContext(),"News Uploaded",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CameraRequestCode && resultCode==RESULT_OK)
        {

            photo=(Bitmap)data.getExtras().get("data");
            //imageView.setImageBitmap(photo);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] data1=byteArrayOutputStream.toByteArray();
            String id2= UUID.randomUUID().toString();
            StorageReference filepath=storageReference.child("Photos").child(id2);
            filepath.putBytes(data1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri download=taskSnapshot.getDownloadUrl();
                    urlImage=download.toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}

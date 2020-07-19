package com.example.buyfresh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


public class SignInActivity extends AppCompatActivity {

    private EditText email ;
    private EditText password;
    private  EditText username;
    private EditText passwordConfirm ;

    private Button register ;
    private Button signIn;
    private ImageView profileImage;
    private Button upload;
    private Button choose;
    private StorageTask uploadTask;

    private ProgressBar mProgressBar ;
    private FirebaseAuth mAuth;
    StorageReference mStorageRef;
    public Uri imgure;



    private ImageView black_square;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        signIn = findViewById(R.id.signIn);
        mProgressBar = findViewById(R.id.progressBar);
        username = findViewById(R.id.userName);
        black_square = findViewById(R.id.black_square);
        passwordConfirm = findViewById(R.id.passwordTwo);
        profileImage = findViewById(R.id.profile_image);
        upload = findViewById(R.id.uploadPhoto);
        choose = findViewById(R.id.choosePhote);
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
//        inProgress(false);

        // Image upload:
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask!= null && uploadTask.isInProgress()) {
                Toast.makeText(SignInActivity.this, "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    FileUploader();
                }

            }
        });



        // sign in purposes
        signIn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(isEmpty ()) return;
                inProgress (true);
                mAuth.signInWithEmailAndPassword (email.getText ().toString (),
                        password.getText ().toString ())
                        .addOnSuccessListener (new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignInActivity.this, "user signed in",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent( SignInActivity.this, MainActivity.class);
                                intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity (intent);
                                finish ();return;
                            }
                        }).addOnFailureListener (new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress (false);
                        Toast.makeText(SignInActivity.this, "sign in failed",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

        register.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(isEmpty ()) return;
                inProgress (true);
                mAuth.createUserWithEmailAndPassword (email.getText ().toString (),
                        password.getText ().toString ())
                        .addOnSuccessListener (new OnSuccessListener<AuthResult> () {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                String emailTxt = email.getText().toString();
                                String userName_Text = username.getText().toString();
                                User user = new User(emailTxt, userName_Text);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user);
                                Toast.makeText(SignInActivity.this, "user registered ",Toast.LENGTH_LONG).show();
                                inProgress (false);
                                // Create object of SharedPreferences.
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SignInActivity.this);
                                //now get Editor
                                SharedPreferences.Editor editor = sharedPref.edit();
                                //put your value
                                editor.putString("userName", userName_Text);

                                //commits your edits
                                editor.commit();

                                // Create object of SharedPreferences.
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SignInActivity.this);
                                //now get Editor
                                SharedPreferences.Editor edit = sharedPref.edit();
                                //put your value
                                editor.putString("email", emailTxt);

                                //commits your edits
                                editor.commit();
                            }
                        }).addOnFailureListener (new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress (false);
                        Toast.makeText(SignInActivity.this, "register failed",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });




    }

    private void inProgress (boolean x ){
        if(x) {
            if (black_square != null ) {black_square.setVisibility(View.VISIBLE);}
            mProgressBar.setVisibility (View.VISIBLE);

            signIn.setEnabled (false);
            register.setEnabled (false);
        }else{
            if (black_square != null) {black_square.setVisibility(View.GONE);}
            mProgressBar.setVisibility (View.GONE);

            signIn.setEnabled (true);
            register.setEnabled (true);
        }
    }
    private boolean isEmpty(){
        if(TextUtils.isEmpty (email.getText ().toString ())){
            email.setError ("REQUIRED");
            return true;
        }
        if(TextUtils.isEmpty (password.getText ().toString ())){
            password.setError ("REQUIRED");
            return true;
        }
        if(TextUtils.isEmpty (passwordConfirm.getText ().toString ())){
            passwordConfirm.setError ("REQUIRED");
            return true;
        }

        if(!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            passwordConfirm.setError("PASSWORD DOESN'T MATCH");
            return true;
        }
        return false;
    }

    private String getExtension (Uri uri) {

        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void FileUploader(){

        StorageReference Ref = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(imgure));
//        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
//        StorageReference riversRef = storageRef.child("images/rivers.jpg");

       uploadTask =  Ref.putFile(imgure)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();




                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void FileChooser(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgure = data.getData();
            profileImage.setImageURI(imgure);
            String path = imgure.getPath();
            Toast.makeText(SignInActivity.this, "Image Uploaded Successfully", Toast.LENGTH_LONG).show();

            // Create object of SharedPreferences.
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SignInActivity.this);
            //now get Editor
            SharedPreferences.Editor editor = sharedPref.edit();
            //put your value
            editor.putString("imgure", String.valueOf(profileImage));

            //commits your edits
            editor.commit();



        }
    }
}
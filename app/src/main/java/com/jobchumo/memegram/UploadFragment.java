package com.jobchumo.memegram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class UploadFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    protected ImageView imageView;
    protected EditText caption;
    protected Button postBtn, chooseBtn;
    protected static final int GALLERY_REQUEST_CODE = 1;
    protected Bitmap bitmap;
    protected StorageReference mStorageRef;
    protected Uri uri = null;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        if (!SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            getActivity().finish();
            startActivity(new Intent(getContext(), Login.class));
        }
        caption = rootView.findViewById(R.id.desc);
        imageView = rootView.findViewById(R.id.imageChosen);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Spinner category = rootView.findViewById(R.id.category_Spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(this);

        postBtn = rootView.findViewById(R.id.postButton);
        chooseBtn = rootView.findViewById(R.id.choosephoto);
        chooseBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choosephoto:
                selectImage();
                break;
        }
    }

    private void postImage(String imgCaption, String username, String imageUrl, String categorySelect, String memeText) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading your meme...");
        progressDialog.show();
        String url = "https://memes.mobisoko.co.ke/upload/index.php";

        Log.d("dataPost", imgCaption);
        Log.d("username", username);
        Log.d("imageurl", imageUrl);
        Log.d("selectcategory", categorySelect);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_name", username);
            jsonObject.put("Pic_VId", imageUrl);
            jsonObject.put("caption", imgCaption);
            jsonObject.put("category", categorySelect);
            jsonObject.put("meme_text", memeText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("ResponseMemeUpload", response.toString());
                        Toast.makeText(getContext(), "Meme Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("ErrorMemeUpload", error.toString());
                Toast.makeText(getContext(), "Post not successful", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        final String categorySelected = parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(), "Category Selected: "+categorySelected, Toast.LENGTH_SHORT).show();
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseVisionImage image = null;
                try {
                    image = FirebaseVisionImage.fromFilePath(getContext(), uri);
                } catch (IOException e) {
                    Log.d("ImageVision", e.getMessage());
                }
                FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getCloudTextRecognizer();
                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                String text = "";
                                for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                                    text = text + block.getText();
                                }
                                text = text.replace(System.lineSeparator(), " ");
                                Log.d("MemeContent", text);
                                MemeContent memeContent = new MemeContent(text);
                                SharedPrefManager.getInstance(getContext()).isMemeContent(memeContent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("VisionFailure", e.getMessage());
                            }
                        });

                StorageReference filepath = mStorageRef.child("memes").child(uri.getLastPathSegment());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        User user = SharedPrefManager.getInstance(getContext()).getUser();
                                        MemeContent memeContent = SharedPrefManager.getInstance(getContext()).getMemeContent();
                                        final String imgCaption = caption.getText().toString().trim();
                                        final String username = user.getUsername();
                                        final String memeText = memeContent.getMemeContent();
                                        Toast.makeText(getContext(), memeText, Toast.LENGTH_LONG).show();
                                        final String imageUrl = uri.toString();
                                        Log.d("ImageUrl", imageUrl);
                                        postImage(imgCaption, username, imageUrl, categorySelected, memeText);
                                    }
                                });
                            }
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            uri = data.getData();
            imageView.setImageURI(uri);
        }
    }

    public void selectImage () {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    public String imageToString (Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
        byte [] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}

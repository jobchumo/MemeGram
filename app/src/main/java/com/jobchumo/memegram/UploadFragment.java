package com.jobchumo.memegram;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class UploadFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    protected ImageView imageView;
    protected EditText caption;
    protected Button postBtn, chooseBtn, trialB;
    protected static final int GALLERY_REQUEST_CODE = 1;
    protected Bitmap bitmap;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_add, container, false);

//        if (!SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
//            getActivity().finish();
//            startActivity(new Intent(getContext(), Login.class));
//        }
        caption = rootView.findViewById(R.id.desc);
        imageView = rootView.findViewById(R.id.imageChosen);
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

    private void postImage(String imgCaption, String username, String imageUrl, String categorySelect) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading your meme...");
        progressDialog.show();
        String url = "https://postman-echo.com/post";

        Log.d("dataPost", imgCaption);
        Log.d("username", username);
        Log.d("imageurl", imageUrl);
        Log.d("selectcategory", categorySelect);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Pic_VId", imageUrl);
            jsonObject.put("user_name", username);
            jsonObject.put("caption", imgCaption);
            jsonObject.put("category", categorySelect);
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
                User user = SharedPrefManager.getInstance(getContext()).getUser();
                String imgCaption = caption.getText().toString().trim();
                String username = "cjkipu";
                String imageUrl = imageToString(bitmap);
                postImage(imgCaption, username, imageUrl, categorySelected);
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
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

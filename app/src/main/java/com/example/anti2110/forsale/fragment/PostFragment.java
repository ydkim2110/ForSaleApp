package com.example.anti2110.forsale.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.anti2110.forsale.R;
import com.example.anti2110.forsale.util.SelectPhotoDialog;
import com.example.anti2110.forsale.util.UniversalImageLoader;

public class PostFragment extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener {

    private static final String TAG = "PostFragment";

    //widgets
    private ImageView mPostImage;
    private EditText mTitle, mDescription, mPrice, mCountry, mStateProvince, mCity, mContactEmail;
    private Button mPost;
    private ProgressBar mProgressBar;

    //vars
    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mPostImage = view.findViewById(R.id.post_image);
        mTitle = view.findViewById(R.id.input_title);
        mDescription = view.findViewById(R.id.input_description);
        mPrice = view.findViewById(R.id.input_price);
        mCountry = view.findViewById(R.id.input_country);
        mStateProvince = view.findViewById(R.id.input_state_province);
        mCity = view.findViewById(R.id.input_city);
        mContactEmail = view.findViewById(R.id.input_email);
        mPost = view.findViewById(R.id.btn_post);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init();

        return view;
    }

    private void init() {
        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog to choose new photo");
                SelectPhotoDialog dialog = new SelectPhotoDialog();
                dialog.show(getFragmentManager(), getString(R.string.dialog_select_photo));
                dialog.setTargetFragment(PostFragment.this, 1);

            }
        });
    }

    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePath: setting the image to imageview");
        UniversalImageLoader.setImage(imagePath.toString(), mPostImage);
        // assign to a global variable
        mSelectedBitmap = null;
        mSelectedUri = imagePath;
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        mPostImage.setImageBitmap(bitmap);
        // assign to a global variable
        mSelectedUri = null;
        mSelectedBitmap = bitmap;
    }
}

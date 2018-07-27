package com.example.anti2110.forsale.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.anti2110.forsale.R;
import com.example.anti2110.forsale.account.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    private static final String TAG = "AccountFragment";

    //firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //widgets
    private Button mSignOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mSignOut = (Button) view.findViewById(R.id.sign_out);

        setupFirebaseListenter();

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to sign out ther user.");
                    FirebaseAuth.getInstance().signOut();
            }
        });

        return view;
    }

    private void setupFirebaseListenter() {
        Log.d(TAG, "setupFirebaseListenter: setting up the auth state Listener");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d(TAG, "onAuthStateChanged: signed_in : " + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                    Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }
}

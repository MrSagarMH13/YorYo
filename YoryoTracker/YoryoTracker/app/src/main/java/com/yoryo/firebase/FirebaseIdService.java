package com.yoryo.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String tokenId = FirebaseInstanceId.getInstance().getToken();
        System.out.println("TOKENID"+tokenId);
    }
}
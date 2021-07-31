package com.example.functionsample;

import android.telecom.Call;

public class PlaceDetail {
    Call.Details result;

    public PlaceDetail(Call.Details result) {
        this.result = result;
    }

    public Call.Details getResult() {
        return result;
    }
}

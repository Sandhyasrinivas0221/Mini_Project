package com.example.functionsample;

import java.util.ArrayList;


public class Request {
    ArrayList<Result> results;

    public Request(ArrayList<Result> results) {

        this.results = results;
    }

    public ArrayList<Result> getResults() {
        return results;
    }
}

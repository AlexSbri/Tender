package com.example.tenderapp.entities;

import com.example.tender.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Images {
    public static int randomImage() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.drink);
        imageList.add(R.drawable.cocktail_png57);
        imageList.add(R.drawable.cocktail_png18);
        imageList.add(R.drawable.cocktail_png39);
        return imageList.get(new Random().nextInt(imageList.size()));
    }
}

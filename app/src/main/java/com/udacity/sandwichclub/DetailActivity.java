package com.udacity.sandwichclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ImageView ingredientsIv;
    TextView origin_tv,also_known_tv,ingredients_tv,description_tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        origin_tv = findViewById(R.id.origin_tv);
        also_known_tv = findViewById(R.id.also_known_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);
        description_tv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.get().load(sandwich.getImage()).into(ingredientsIv);
        origin_tv.setText(sandwich.getPlaceOfOrigin());
        List<String> aka = sandwich.getAlsoKnownAs();
        String aka_str = TextUtils.join(", ", aka);
        also_known_tv.setText(aka_str);
        description_tv.setText(sandwich.getDescription());
        List<String> ingredientsList = sandwich.getIngredients();
        String ingredientString = TextUtils.join(",",ingredientsList);
        ingredients_tv.setText(ingredientString);
    }
}

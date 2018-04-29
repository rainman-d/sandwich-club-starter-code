package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import com.udacity.sandwichclub.utils.StringUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mOriginTextView;
    private TextView mIngredientsTextView;
    private TextView mAkaTextView;
    private TextView mDescriptionTextView;

    private Sandwich sandwichChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        // set the sandwich data returned to the member variable
        this.sandwichChosen = sandwich;

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mAkaTextView = (TextView) findViewById(R.id.also_known_tv);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        mOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);

        mAkaTextView.setText(StringUtils.getListAsCommaSeparatedString(sandwichChosen.getAlsoKnownAs()));
        mIngredientsTextView.setText(StringUtils.getListAsCommaSeparatedString(sandwichChosen.getIngredients()));
        mOriginTextView.setText(sandwichChosen.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwichChosen.getDescription());

        this.setDefaultTextInViews();
    }

    private void setDefaultTextInViews(){
        this.setDefaultText(mAkaTextView);
        this.setDefaultText(mIngredientsTextView);
        this.setDefaultText(mOriginTextView);
        this.setDefaultText(mDescriptionTextView);
    }

    private void setDefaultText(TextView view){
        System.out.println(view.getText().toString().isEmpty());
        if(view.getText().toString().isEmpty()){
            view.setText("Unknown");
        }
    }
}

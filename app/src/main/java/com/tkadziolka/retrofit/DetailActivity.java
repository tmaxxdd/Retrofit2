package com.tkadziolka.retrofit;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tkadziolka.retrofit.data.ResponseCallback;
import com.tkadziolka.retrofit.data.controller.StackOverflowController;
import com.tkadziolka.retrofit.data.model.Question;
import com.tkadziolka.retrofit.data.model.QuestionDetail;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private StackOverflowController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final TextView titleView = findViewById(R.id.detail_title);
        final TextView contentView = findViewById(R.id.detail_content);
        final TextView nickView = findViewById(R.id.detail_nick);

        Question question = (Question) getIntent().getSerializableExtra("question");

        controller = new StackOverflowController();

        if (question != null) {
            controller.loadContent(new ResponseCallback<List<QuestionDetail>>() {
                @Override
                public void onSuccess(@NonNull List<QuestionDetail> list) {
                    QuestionDetail detail = list.get(0);
                    if (detail != null) {
                        titleView.setText(detail.getTitle());
                        contentView.setText(Html.fromHtml(detail.getBody()));
                        nickView.setText(detail.getOwner().getDisplay_name());
                    }
                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    Toast.makeText(DetailActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }, question.getQuestion_id(), ApiFilters.slightQuestionDetail);
        }

        controller.loadPosts(new ResponseCallback<QuestionDetail>() {

            @Override
            public void onSuccess(@NonNull QuestionDetail response) {

            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }
        });
    }
}

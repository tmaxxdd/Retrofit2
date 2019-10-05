package com.tkadziolka.retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tkadziolka.retrofit.data.api.ResponseCallback;
import com.tkadziolka.retrofit.data.controller.AuthController;
import com.tkadziolka.retrofit.data.controller.StackOverflowController;
import com.tkadziolka.retrofit.data.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ResponseCallback<Question> {

    private int pageSize = 5;

    private QuestionsAdapter adapter;
    private List<Question> questions = new ArrayList<>();
    private StackOverflowController stackOverflowController;
    private AuthController authController;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.questions_list);

        final Intent detailsActivity = new Intent(this, DetailActivity.class);

        adapter = new QuestionsAdapter(questions);
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();

                Question question = questions.get(position);
                detailsActivity.putExtra("question", question);
                startActivity(detailsActivity);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        stackOverflowController = new StackOverflowController();
        authController = new AuthController();

    }

    @Override
    protected void onStart() {
        super.onStart();

        authController.authorize("Example token");

        stackOverflowController.loadQuestions(MainActivity.this, pageSize);

        recyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            pageSize += 5;
                            stackOverflowController.loadQuestions(MainActivity.this, pageSize);
                        }
                    }
                }
        );

    }

    @Override
    public void onSuccess(@NonNull List<Question> questionList) {
        Log.d("Callback", "OK");
        questions.addAll(questionList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

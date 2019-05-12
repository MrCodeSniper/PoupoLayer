package com.github.codesniper.poplayerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.codesniper.poplayer.PopLayerView;
import com.github.codesniper.poplayer.config.LayerConfig;
import com.github.codesniper.poplayer.pop.Popi;
import com.github.codesniper.poplayer.task.Task;
import com.github.codesniper.poplayer.task.TaskManagerV1;
import com.github.codesniper.poplayer.task.TaskManager;
import com.github.codesniper.poplayerview.R;

import static com.github.codesniper.poplayer.config.LayerConfig.COUNTDOWN_CANCEL;
import static com.github.codesniper.poplayer.config.LayerConfig.TRIGGER_CANCEL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void toSyncSubject(View view){
        startActivity(new Intent(this,SyncActivity.class));
    }

    public void toDialogSubject(View view){
        startActivity(new Intent(this,DialogActivity.class));
    }

    public void toWebSubject(View view){
        startActivity(new Intent(this,WebviewActivity.class));
    }
}

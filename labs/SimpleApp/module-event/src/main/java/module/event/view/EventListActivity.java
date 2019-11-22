package module.event.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import module.event.R;

public class EventListActivity extends AppCompatActivity {

    public static void start(Activity source) {
        Intent intent = new Intent(source, EventListActivity.class);
        source.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
    }

    public void onClickEvent(View view) {
        EventHomeActivity.start(this);
        finish();
    }
}

package droids.rizz.youtubeplayer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import droids.rizz.youtubeplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUp();
    }

    private void setUp() {
        getSupportFragmentManager().beginTransaction().add(R.id.container, VideoPlayerFragment.newInstance(null, null)).commit();
    }
}

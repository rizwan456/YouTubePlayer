package droids.rizz.youtubeplayer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.facebook.drawee.backends.pipeline.Fresco;

import droids.rizz.youtubeplayer.adadpters.MainAdadpter;
import droids.rizz.youtubeplayer.databinding.ActivityMainBinding;
import droids.rizz.youtubeplayer.fragment.VideoPlayerFragment;
import droids.rizz.youtubeplayer.supporters.Utility;

public class MainActivity extends AppCompatActivity {
    String tag = "MainActivity";
    public ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Fresco.initialize(this);
        setUp();
    }

    private void setUp() {
        //getSupportFragmentManager().beginTransaction().add(R.id.container, BlankFragment.newInstance(null, null)).commit();
        mainBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerview.setAdapter(new MainAdadpter(this, Utility.generateVideoList()));
    }

    public void setVisible(String url) {
        getSupportFragmentManager().beginTransaction().add(R.id.container, VideoPlayerFragment.newInstance(url, null,true)).commit();
    }


}

package droids.rizz.youtubeplayer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import droids.rizz.youtubeplayer.adadpters.MainAdadpter;
import droids.rizz.youtubeplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    String tag="MainActivity";
    public ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Fresco.initialize(this);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, VideoPlayerFragment.newInstance(null, null)).commit();
        }
        setUp();
    }

    private void setUp() {
        mainBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerview.setAdapter(new MainAdadpter(this, Utility.generateVideoList()));
    }

}

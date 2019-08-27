package droids.rizz.youtubeplayer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import droids.rizz.youtubeplayer.adadpters.MainAdadpter;
import droids.rizz.youtubeplayer.databinding.ActivityMainBinding;
import droids.rizz.youtubeplayer.fragment.BlankFragment;
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

   /* public void setVisible(String url, String title) {
        Toast.makeText(this, "in video", Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().add(R.id.container,
                VideoPlayerFragment.newInstance(url, title, true))
                .commit();
    }*/

    public void addVideoFragment(String url, String title) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                VideoPlayerFragment.newInstance(url, title, true))
                .commit();
        Toast.makeText(this, "Video Fragment Added", Toast.LENGTH_SHORT).show();
    }

    public void addBlankFrgment(Fragment fragment) {
        if (fragment instanceof BlankFragment) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,
                    BlankFragment.newInstance(null, null))
                    .commit();
            Toast.makeText(this, "Blank Fragment Added", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeVideoFragment(Fragment fragment) {
        if (fragment instanceof VideoPlayerFragment) {
            getSupportFragmentManager().beginTransaction().
                    remove(fragment).commit();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                BlankFragment.newInstance(null, null)).commit();

        Toast.makeText(this, "Fragment Removed with Blank Fragment", Toast.LENGTH_SHORT).show();
    }


}

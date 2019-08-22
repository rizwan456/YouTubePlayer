package droids.rizz.youtubeplayer.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import droids.rizz.youtubeplayer.R;
import droids.rizz.youtubeplayer.databinding.ActivityDummyBinding;
import droids.rizz.youtubeplayer.fragment.BlankFragment;

public class DummyActivity extends AppCompatActivity {
ActivityDummyBinding dummyBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dummyBinding= DataBindingUtil.setContentView(this, R.layout.activity_dummy);
        setUp();
    }

    private void setUp() {
        getSupportFragmentManager().beginTransaction().add(R.id.dummyFrame, BlankFragment.newInstance(null,null)).commit();
    }
}

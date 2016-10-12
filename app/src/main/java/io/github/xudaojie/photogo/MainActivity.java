package io.github.xudaojie.photogo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    protected Activity mActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        final ImageView imageView = (ImageView) findViewById(R.id.image);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Animator animator = AnimatorInflater.loadAnimator(mActivity, R.animator.test);
                    animator.setTarget(imageView);
                    animator.start();
                } else {
                    Animator animator = AnimatorInflater.loadAnimator(mActivity, R.animator.test_reverse);
                    animator.setTarget(imageView);
                    animator.start();
                }
            }
        });
    }
}

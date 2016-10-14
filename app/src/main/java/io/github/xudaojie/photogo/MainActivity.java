package io.github.xudaojie.photogo;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected Activity mActivity = this;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4));

//        Switch switch1 = (Switch) findViewById(R.id.switch1);
//        final ImageView imageView = (ImageView) findViewById(R.id.image);

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {
            mRecyclerView.setAdapter(new GridAdapter(getThumbnails()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mRecyclerView.setAdapter(new GridAdapter(getThumbnails()));
        }
    }

    private List<String> getImages() {
        return null;
    }

    private List<String> getThumbnails() {
        String[] columns = new String[]{
                MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.DATA,
                MediaStore.Images.Thumbnails.IMAGE_ID};
        Cursor cursor = getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                columns, null, null, null);
        List<String> paths = new ArrayList<>();
        while (cursor.moveToNext()) {
            paths.add(cursor.getString(1));
        }
        return paths;
    }

    private static class GridAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<String> mPaths;
        Context mContext;

        public GridAdapter(List<String> paths) {
            mPaths = paths;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.mImageView.setImageURI(Uri.parse(mPaths.get(position)));

            holder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.test);
                        animator.setTarget(holder.mImageView);
                        animator.start();
                    } else {
                        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.test_reverse);
                        animator.setTarget(holder.mImageView);
                        animator.start();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mPaths == null ? 0 : mPaths.size();
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private Switch mSwitch;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mSwitch = (Switch) itemView.findViewById(R.id.switch1);
        }
    }
}

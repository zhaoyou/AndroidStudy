package me.zhaoyou.adaptivescenery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZhaoYou on 11/01/2017.
 */

public class ImageFragment extends Fragment{

  public ImageFragment() { }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_image, container, false);
  }
}

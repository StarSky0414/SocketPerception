package com.tts.starsky.apperceive.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.view.MyTrendsActivity;
import com.tts.starsky.apperceive.view.SendTrendActivity;

public class MyFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "===================MyFragment: ";

    public static MyFragment newInstance (){
        MyFragment my = new MyFragment();
        return my;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.activity_my, container, false);
        RelativeLayout viewById = inflate.findViewById(R.id.rl_my_trends);
        viewById.setOnClickListener(this);
        Log.d("============","=========");
        return inflate;
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: "+v.getId());
        switch (v.getId()){
            case R.id.rl_my_trends:
                //里面写点击后想要实现的效果
                Toast.makeText(this.getContext(),
                        "按钮被点击", Toast.LENGTH_SHORT).show();
                //这里是弹出一个消息---"按钮被点击"
                Intent intent = new Intent(getActivity(),MyTrendsActivity.class) ;
                startActivity(intent);
        }
    }
}

package com.tts.starsky.apperceive.view.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.FindUserInfo;
import com.tts.starsky.apperceive.bean.json.request.UserInfoEntity;
import com.tts.starsky.apperceive.bean.service.SendMessageBean;
import com.tts.starsky.apperceive.lib.swipeadapterview.SwipeAdapterView;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.manager.FindFragmentManager;
import com.tts.starsky.apperceive.service.EvenBusEnumService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import lombok.ast.Message;

public class FindFragment extends Fragment implements SwipeAdapterView.onFlingListener,
        SwipeAdapterView.OnItemClickListener {


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static FindFragment newInstance() {
        FindFragment find = new FindFragment();
        return find;
    }

//    String [] headerIcons = {"http://www.5djiaren.com/uploads/2015-04/17-115301_29.jpg",
//            "http://img1.dzwww.com:8080/tupian_pl/20160106/32/4152697013403556460.jpg",
//            "http://c.hiphotos.baidu.com/zhidao/pic/item/72f082025aafa40f191362cfad64034f79f019ce.jpg",
//            "http://img.article.pchome.net/new/w600/00/35/15/66/pic_lib/wm/122532981493137o3iegiyx.jpg",
//            "http://img0.imgtn.bdimg.com/it/u=3382799710,1639843170&fm=21&gp=0.jpg",
//            "http://i2.sinaimg.cn/travel/2014/0918/U7398P704DT20140918143217.jpg",
//            "http://photo.l99.com/bigger/21/1415193165405_4sg3ds.jpg",
//            "http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1305/15/c2/20949108_20949108_1368599174341.jpg",
//            "http://pic29.nipic.com/20130501/12558275_114724775130_2.jpg",
//            "http://photo.l99.com/bigger/20/1415193157174_j2fa5b.jpg"};
//
//    String [] names = {"星空","星空"};
//
//    String [] citys = {"北京", "上海", "广州", "深圳"};
//
//    String [] edus = {"23","23"};
//
//    String [] years = {"摄影","摄影"};
//
//    Random ran = new Random();

    private int cardWidth;
    private int cardHeight;

    private SwipeAdapterView swipeView;
    private InnerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_find, container, false);
        initView(inflate);
        LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SYNC_FINDINFO,null);
//        loadData();
        changeData(null);
        EventBus.getDefault().register(this);
        return inflate;
    }

    private void initView(View inflate) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        cardHeight = (int) (dm.heightPixels - (338 * density));


        swipeView = (SwipeAdapterView) inflate.findViewById(R.id.swipe_view);
//        swipeView.setIsNeedSwipe(false);
        swipeView.setFlingListener(this);
        swipeView.setOnItemClickListener(this);

        adapter = new InnerAdapter();
        swipeView.setAdapter(adapter);
    }


    @Override
    public void onItemClicked(View v, Object dataObject) {
        Log.i("tag", "click top view");
    }

    @Override
    public void removeFirstObjectInAdapter(View topView) {
        adapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {
        Log.i("tag", "swipe left");
    }

    @Override
    public void onRightCardExit(Object dataObject) {
        Log.i("tag", "swipe right");
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 3) {
            Toast.makeText(getActivity(), "轻点翻，要没有了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {
    }

//    private void loadData() {
//        new AsyncTask<Void, Void, List<Talent>>() {
//            @Override
//            protected List<Talent> doInBackground(Void... params) {
//                ArrayList<Talent> list = new ArrayList<>(4);
//                Talent talent;
//                for (int i = 0; i < 10; i++) {
//                    talent = new Talent();
//                    talent.headerIcon = headerIcons[i];
//                    talent.nickname = names[ran.nextInt(names.length-1)];
//                    talent.cityName = citys[ran.nextInt(citys.length-1)];
//                    talent.educationName = edus[ran.nextInt(edus.length-1)];
//                    talent.workYearName = years[ran.nextInt(years.length-1)];
//                    list.add(talent);
//                }
//                return list;
//            }
//
//            @Override
//            protected void onPostExecute(List<Talent> list) {
//                super.onPostExecute(list);
//                adapter.addAll(list);
//            }
//        }.execute();
//    }


    private class InnerAdapter extends BaseAdapter implements View.OnClickListener {

        ArrayList<Talent> objs;

        public InnerAdapter() {
            objs = new ArrayList<>();
        }

        public void addAll(Collection<Talent> collection) {
            if (isEmpty()) {
                objs.addAll(collection);
                notifyDataSetChanged();
            } else {
                objs.addAll(collection);
            }
        }

        public void replist(ArrayList<Talent> collection) {
            if (isEmpty()) {
                objs = collection;
                notifyDataSetChanged();
            } else {
                objs.addAll(collection);
            }
        }


        public void clear() {
            objs=null;
        }

        public boolean isEmpty() {
            return objs.isEmpty();
        }

        public void remove(int index) {
            if (index > -1 && index < objs.size()) {
                objs.remove(index);
                notifyDataSetChanged();
            }
        }


        @Override
        public int getCount() {
            return objs.size();
        }

        @Override
        public Talent getItem(int position) {
            if (objs == null || objs.size() == 0) return null;
            return objs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            Talent talent = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find, parent, false);
                holder = new ViewHolder();
                convertView.setTag(holder);
                convertView.getLayoutParams().width = cardWidth;
                holder.portraitView = (ImageView) convertView.findViewById(R.id.portrait);
                //holder.portraitView.getLayoutParams().width = cardWidth;
                holder.portraitView.getLayoutParams().height = cardHeight;
                holder.nameView = (TextView) convertView.findViewById(R.id.name);
                //parentView.getLayoutParams().width = cardWidth;
                //holder.jobView = (TextView) convertView.findViewById(R.id.job);
                //holder.companyView = (TextView) convertView.findViewById(R.id.company);
                holder.cityView = (TextView) convertView.findViewById(R.id.city);
                holder.eduView = (TextView) convertView.findViewById(R.id.yearOld);
                holder.workView = (TextView) convertView.findViewById(R.id.hobby);
                holder.collectView = (CheckedTextView) convertView.findViewById(R.id.favorite);
                holder.collectView.setTag(position);
                holder.collectView.setOnClickListener(this);
            } else {
                //Log.e("tag", "recycler convertView");
                holder = (ViewHolder) convertView.getTag();
            }

            Glide.with(parent.getContext()).load(talent.headerIcon)
//                    .centerCrop().placeholder(R.drawable.default_card)
                    .into(holder.portraitView);
            holder.nameView.setText(String.format("%s", talent.nickname));

            final CharSequence no = "暂无";

            holder.cityView.setHint(no);
            holder.cityView.setText(talent.cityName);
//            holder.cityView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_location,0,0);

            holder.eduView.setHint(no);
            holder.eduView.setText(talent.educationName);
//            holder.eduView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_edu,0,0);

            holder.workView.setHint(no);
            holder.workView.setText(talent.workYearName);
//            holder.workView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_work_year,0,0);


            return convertView;
        }

        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            Talent item = adapter.getItem(index);
            String sendUserId = item.id;
            String messageString = "我有故事你有酒吗？";
            SendMessageBean sendMessageBean = new SendMessageBean(UserStateInfo.getUserId(), sendUserId, messageString, null, 0);
            LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SEND_MESSAGE, sendMessageBean);
            Log.i("tag", "onClick: " + v.getTag());
            Toast.makeText(getActivity(), "关注成功，快去看看吧！", Toast.LENGTH_SHORT).show();
            v.setEnabled(false);
            v.setBackgroundColor(Color.parseColor("#00ffffff"));
        }
    }

    private static class ViewHolder {
        ImageView portraitView;
        TextView nameView;
        TextView cityView;
        TextView eduView;
        TextView workView;
        CheckedTextView collectView;
    }

    public static class Talent {
        public String id;
        public String headerIcon;
        public String nickname;
        public String cityName;
        public String educationName;
        public String workYearName;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeDataListen(FindUserInfo findUserInfo) {
        changeData(findUserInfo);
    }


    public void changeData(FindUserInfo findUserInfo) {
        List<UserInfoEntity> userInfoEntityList;
        if (findUserInfo == null) {
            userInfoEntityList = FindFragmentManager.getDataList();
        } else {
            userInfoEntityList = findUserInfo.getUserInfoEntityList();
        }
        ArrayList<Talent> list = new ArrayList<>();
        for (UserInfoEntity userInfoEntity : userInfoEntityList) {
            Talent talent = new Talent();

            String id = userInfoEntity.getId();
            String nickName = userInfoEntity.getNickName();
            String photoUser = userInfoEntity.getPhotoUser();
            int sex = userInfoEntity.getSex();
            int constellation = userInfoEntity.getConstellation();

            talent.id = id;
            talent.headerIcon = photoUser != null ? photoUser : "";
            talent.cityName = sex == 1 ? "男" : "女";
            talent.nickname = nickName != null ? nickName : "一个不想透露名字的家伙";
            talent.workYearName = "摄影，画画";
            talent.educationName = "本溪";
            list.add(talent);
        }
        adapter.replist(list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.clear();
        EventBus.getDefault().unregister(this);
    }

}

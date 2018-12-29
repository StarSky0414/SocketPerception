package com.tts.starsky.apperceive.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tts.starsky.apperceive.R;


public class main extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.active_test);
//        System.out.println("run.........onCreate");

    }


//    public void test(View view){
//        Realm realm=Realm.getDefaultInstance();
//        Dog user = new Dog();
//        user.setId("12");
//        user.setName("123123");
//        user.setAge(12);
////        user.setEmail("john@corporation.com");
//
//// Copy the object to Realm. Any further changes must happen on realmUser
//        realm.beginTransaction();
//        realm.insert(user);
//        realm.commitTransaction();
//
//        System.out.println("插入已执行");
//        List<Dog> dogs = queryAllDog();
//        for (Dog a :
//                dogs) {
//            System.out.println(a.getName());
//        }
//        System.out.println("select已执行");
//
//
//    }
//
//
//    public List<Dog> queryAllDog() {
//        Realm  mRealm=Realm.getDefaultInstance();
//
//        RealmResults<Dog> dogs = mRealm.where(Dog.class).findAll();
//
//        return mRealm.copyFromRealm(dogs);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

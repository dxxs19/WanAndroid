package com.wei.wanandroid.activity.db;

import android.os.Bundle;
import android.util.Log;

import com.wei.wanandroid.R;
import com.wei.wanandroid.WanApplication;
import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.bean.User;
import com.wei.wanandroid.bean.User_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

public class ObjectBoxActivity extends BaseActivity
{
    private Box<User> mUserBox;
    private Query<User> mUserQuery;
    private List<User> mUsers;
    private int mSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_box);
        mUserBox = WanApplication.sBoxStore.boxFor(User.class);
//        User user = new User(0, "cxw", "man", 28);
        User user = new User("cxw", "man", 28);
        // 新增
        mUserBox.put(user);
        Log.e(TAG, "Inserted new User, ID: " + user.getId());
        mUserQuery = mUserBox.query().order(User_.id).build();
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 查找
        mUsers = mUserQuery.find();
        if (null != mUsers)
        {
            mSize = mUsers.size();
            for (User user:mUsers) {
                Log.e(TAG, user.toString());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0;i < mSize; i ++)
        {
            if (i % 2 == 0)
            { // 删除
                mUserBox.remove(mUsers.get(i));
            }
            else
            { // 更新
                User user = mUsers.get(i);
                user.setSex("女");
                mUserBox.put(user);
            }
        }
    }
}

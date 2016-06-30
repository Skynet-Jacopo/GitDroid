package com.feicuiedu.gitdroid.repo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicuiedu.gitdroid.repo.pager.RepoListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuqun on 6/30/2016.
 */
public class HotRepoPagerAdapter extends FragmentPagerAdapter {

    private final List<String> languages;
    public HotRepoPagerAdapter(FragmentManager fm) {
        super(fm);
        languages =new ArrayList<>();
        languages.add("Java1");
        languages.add("Java2");
        languages.add("Java3");
        languages.add("Java4");
        languages.add("Java5");
        languages.add("Java6");
        languages.add("Java7");
    }

    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position);
    }
}

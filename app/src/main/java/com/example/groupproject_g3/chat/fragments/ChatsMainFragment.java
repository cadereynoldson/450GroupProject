package com.example.groupproject_g3.chat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentChatsMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.function.IntFunction;

/**
 * A simple {@link Fragment} subclass.
  */
public class ChatsMainFragment extends Fragment {

    private FragmentChatsMainBinding binding;
    private ChatsMainFragment.ChatsPagerAdapter adapter;
    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChatsMainBinding.inflate(inflater, container, false);
        adapter = new ChatsMainFragment.ChatsPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager = binding.getRoot().findViewById(R.id.chats_main_viewpager);
        adapter.initFrags();
        pager.setAdapter(adapter);
        TabLayout tabs = binding.getRoot().findViewById(R.id.chats_tabs);
        tabs.setupWithViewPager(pager);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public class ChatsPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragments;

        private String[] titles;

        public ChatsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void initFrags() {
            IntFunction<String> getString = getResources()::getString;
            fragments = new Fragment[2];
            titles = new String[2];
            fragments[0] = new ChatFragment();
            fragments[1] = new AddChatFragment();
//            fragments[2] = new ChatListFragment();
            titles[0] = getString.apply(R.string.tab_show_chats);
            titles[1] = getString.apply(R.string.tab_add_chat);
//            titles[2] = getString.apply(R.string.tab_show_chatlist);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
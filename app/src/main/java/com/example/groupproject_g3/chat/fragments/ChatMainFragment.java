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
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentChatMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.function.IntFunction;

/**
 * A simple {@link Fragment} subclass.
  */
public class ChatMainFragment extends Fragment {

    private FragmentChatMainBinding binding;
    private ChatMainFragment.ChatsPagerAdapter adapter;
    private ViewPager pager;


    public ChatMainFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatMainBinding.inflate(inflater, container, false);
        pager = binding.getRoot().findViewById(R.id.chats_main_viewpager);
        adapter = new ChatMainFragment.ChatsPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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

    public void navigateToChat(final int chatID, View view){
        ChatMainFragmentDirections.ActionNavigationChatsToChatFragment directions =
                ChatMainFragmentDirections.actionNavigationChatsToChatFragment(chatID);
        directions.setChatID(chatID);
        Navigation.findNavController(view).navigate(directions);
    }

    public class ChatsPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragments;

        private String[] titles;

        public ChatsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void initFrags() {
            IntFunction<String> getString = getResources()::getString;
            fragments = new Fragment[3];
            titles = new String[3];
            fragments[0] = new ChatListFragment();
            fragments[1] = new ChatAddFragment();
            fragments[2] = new ChatAddDeleteGetContactFragment();
            titles[0] = getString.apply(R.string.tab_show_chatlist);
            titles[1] = getString.apply(R.string.tab_add_chat);
            titles[2] = getString.apply(R.string.tab_add_delete_contact);
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
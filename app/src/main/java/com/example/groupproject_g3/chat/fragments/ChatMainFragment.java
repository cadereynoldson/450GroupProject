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

    /**
     * Binding for fragment access.
     */
    private FragmentChatMainBinding binding;

    /**
     * Pager adapter to load the tabs.
     */
    private ChatMainFragment.ChatsPagerAdapter adapter;

    /**
     * Viewpager for tabs display
     */
    private ViewPager pager;


    /**
     * Required empty constructor
     */
    public ChatMainFragment() {
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
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

    /**
     * Navigation to the chat fragment upon button click.
     *
     * @param chatID the chat id.
     * @param view the view.
     */
    public void navigateToChat(final int chatID, final View view) {
        ChatMainFragmentDirections.ActionChatMainFragmentToChatFragment directions =
                ChatMainFragmentDirections.actionChatMainFragmentToChatFragment(chatID);
        directions.setChatID(chatID);
        Navigation.findNavController(view).navigate(directions);
    }

    /**
     * Inner calss for view pager adapter development.
     */
    public class ChatsPagerAdapter extends FragmentPagerAdapter {

        /**The fragment array.*/
        private Fragment[] fragments;

        /**The title array.*/
        private String[] titles;

        /**
         * Constructor.
         *
         * @param fm fragment manager.
         * @param behavior the behavior.
         */
        public ChatsPagerAdapter(@NonNull FragmentManager fm, final int behavior) {
            super(fm, behavior);
        }

        /**
         * Initialize the fragments.
         */
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
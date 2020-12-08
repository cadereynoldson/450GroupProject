package com.example.groupproject_g3.contact.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentContactMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.function.IntFunction;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContactMainFragment extends Fragment {

    /** Binding for direct reference of the components of this fragment. */
    private FragmentContactMainBinding binding;

    /** The pager adapter for tab navigation. */
    private ContactsPagerAdapter adapter;

    /** View pager for tab navigation */
    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactMainBinding.inflate(inflater, container, false);
        adapter = new ContactsPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager = binding.getRoot().findViewById(R.id.contacts_main_viewpager);
        adapter.initFrags();
        pager.setAdapter(adapter);
        TabLayout tabs = binding.getRoot().findViewById(R.id.contacts_tabs);
        tabs.setupWithViewPager(pager);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * A pager adapter for navigation using tabs.
     */
    public class ContactsPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragments;

        private String[] titles;

        public ContactsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void initFrags() {
            IntFunction<String> getString = getResources()::getString;
            fragments = new Fragment[3];
            titles = new String[3];
            fragments[0] = new ContactListFragment();
            fragments[1] = new ContactPendingFragment();
            fragments[2] = new ContactAddFragment();
            titles[0] = getString.apply(R.string.tab_all_contacts);
            titles[1] = getString.apply(R.string.tab_pending_contacts);
            titles[2] = getString.apply(R.string.tab_add_contact);
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
package com.example.tenderapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tenderapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TipoDrinkFragment extends Fragment {
    private ViewPager2 viewPager2 ;
    private final CocktailFragment cocktailFragment = CocktailFragment.newInstance();
    private final FrullatoFragment frullatoFragment = FrullatoFragment.newInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tipo_drink, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.viewPager2);
        List<String> titlesTab = new ArrayList<>();
        titlesTab.add("Cocktail");
        titlesTab.add("Frullati");
        setViewPagerAdapter();
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(titlesTab.get(position))).attach();
        return view;
    }
    public void setViewPagerAdapter() {
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(cocktailFragment);
        fragmentList.add(frullatoFragment);
        viewPager2Adapter.setData(fragmentList);
        viewPager2.setAdapter(viewPager2Adapter);
    }

    public static class ViewPager2Adapter extends FragmentStateAdapter{
        private ArrayList<Fragment> fragments;

        public ViewPager2Adapter(@NonNull Fragment fragment) {
            super(fragment);
        }
        public void setData(ArrayList<Fragment> fragments) {
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}

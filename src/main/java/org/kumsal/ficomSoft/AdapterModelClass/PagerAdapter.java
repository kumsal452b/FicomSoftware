package org.kumsal.ficomSoft.AdapterModelClass;

import javafx.scene.layout.Pane;
import org.kairos.core.FragmentManager;
import org.kairos.core.FragmentTransaction;

public class PagerAdapter extends org.kairos.layouts.PagerAdapter {
    private FragmentManager fragmentManager;
    private int currentItem;

    public PagerAdapter(FragmentManager fragmentManager)
    {
        this.fragmentManager=fragmentManager;
    }


    @Override
    public String getPageTitle(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object instantiateItem(Pane pane, int i) {
        if(i<getCount()){
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(pane.getId(),getItem(position));
            transaction.commit();
        }
        return null;
    }
}

package com.example.qimou;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

class SimpData extends SimpleAdapter {
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */

    public SimpData(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int[] i= {R.id.tv_1,R.id.tv_2,R.id.tv_3};
        if(position==0) {
            for(int j=0;j<i.length;j++){
                TextView tv = view.findViewById(i[j]);
                tv.setTextColor(Color.RED);
                tv.setTextSize(23);
            }
        }
        if(position==1) {
            for(int j=0;j<i.length;j++) {
                TextView tv = view.findViewById(i[j]);
                tv.setTextColor(Color.rgb(255, 192, 203));
                tv.setTextSize(22);
            }
        }
        if(position==2) {
            for(int j=0;j<i.length;j++) {
                TextView tv = view.findViewById(i[j]);
                tv.setTextColor(Color.YELLOW);
                tv.setTextSize(21);
            }
        }
        return view;
    }
}

package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ChiSeng Ng on 22/6/2017.
 * Class that will receive the Announce objects and support to create and handler the corresponding
 * elements to the ListView and ListView's element corresponding.
 *
 * references [https://jarroba.com/listview-o-listado-en-android/]
 */

public class PublicationDonationAdapter extends BaseAdapter {

    /*  This will declare the global and basic variables that will use in the adapter.
    Note: the handler variables will not declared in this class, because the idea is make a general
    adapter that will work for any type of elements format.
     */
    private ArrayList<Announce> entries;
    private int idListLayout;
    private Context context;

    static class ViewHolder {
        private ImageView tabItemImage;
        private TextView tabItemTitle;
        private TextView tabItemPrice;
        private TextView tabItemFirstDate;
        private TextView tabItemSecondDate;
    }

    /*  Method that create the AnnouncesAdapter's object(constructor).
           @date[22/06/2017]
           @author[ChiSeng Ng]
           @param [Context] context Base Context of the Parent Activity.
           @param [int] idListLayout Id of the ListLayout that will content the elements.
           @param [ArrayList<?>] Generic type ArraysList with the elements's data or information
           added.
           @return [void]
   */
    public PublicationDonationAdapter(Context context, int idListLayout, ArrayList<Announce> entries){
        super();
        this.context = context;
        this.idListLayout = idListLayout;
        this.entries = entries;
    }

    /*  Method that inflate and create an unique element on the list elements view with the position
     and the components handled(by onEntry) corresponding of the ListView.
           @date[22/06/2017]
           @author[ChiSeng Ng]
           @param [int] position Position on the ListView.
           @param [View] view View corresponding to handler the components(in this case announce.xml).
           @param [ViewGroup] parent Parent of the View.
           @return [View] View Inflated with the param handled.
   */
    @Override
    public View getView(int position, View view, ViewGroup parent){

        Announce actualEntry = getItem(position);
        ViewHolder holder;

        final View result;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(idListLayout,null);
            holder.tabItemImage = (ImageView) view.findViewById(R.id.tab_item_image);
            holder.tabItemTitle = (TextView) view.findViewById(R.id.tab_item_title);
            holder.tabItemPrice = (TextView) view.findViewById(R.id.tab_item_others);
            holder.tabItemFirstDate = (TextView) view.findViewById(R.id.tab_item_first_date);
            holder.tabItemSecondDate = (TextView) view.findViewById(R.id.tab_item_second_date);
            result = view;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            result = view;
        }
        holder.tabItemImage.setImageResource(actualEntry.getImage());
        holder.tabItemTitle.setText(actualEntry.getTitle());
        String text0 = actualEntry.getCurrencyPrice() + " Recolectados, " + actualEntry.getCurrencyUnit() + " Requeridos";
        holder.tabItemPrice.setText(text0);
        String text1 =  "Fecha Creacion: " + actualEntry.getName();
        holder.tabItemFirstDate.setText(text1);
        String text2 = "Fecha de Modificacion: " + actualEntry.getAddress();
        holder.tabItemSecondDate.setText(text2);
        return view;

    }

    /*  Method that will return the number of elements on the ArrayList used.
           @date[22/06/2017]
           @author[ChiSeng Ng]
           @param [None]
           @return [int] Number of entries or elements on the ArrayList.
   */
    @Override
    public int getCount(){
        return entries.size();
    }

    /*  Method that will return the elements corresponding to the position on the ArrayList.
           @date[22/06/2017]
           @author[ChiSeng Ng]
           @param [int] position Position of an element on the entries ArrayList.
           @return [Object] An element on the entries ArrayList.
   */
    @Override
    public Announce getItem(int position){
        return entries.get(position);
    }


    /*  Method that will return the elements's identifiers corresponding to the position on the
    ArrayList.
           @date[22/06/2017]
           @author[ChiSeng Ng]
           @param [int] position Position of an element on the entries ArrayList.
           @return [long] An element identifier.
   */
    @Override
    public long getItemId(int position) { return position;}

    /*  Abstract Method that will be extender in the fragment or activity in the will use, for
    handler the components corresponding in the ElementView(In this case announce.xml). This is for
    make an generic Adapter that will work with any type of object with any View components.
          @date[22/06/2017]
          @author[ChiSeng Ng]
          @param [Object] entry Object or entry of the  entries ArrayList, whose attributes will handler
          with the screen or view components(widgets).
          @param [View] view View that will content the components or widget that will handler.
          @return [Void]
    */
    //public abstract void onEntry(Object entry, View view);

    public void updateAdapter(ArrayList<Announce> newEntries) {
        entries.clear();
        entries.addAll(newEntries);
        this.notifyDataSetChanged();
    }

}

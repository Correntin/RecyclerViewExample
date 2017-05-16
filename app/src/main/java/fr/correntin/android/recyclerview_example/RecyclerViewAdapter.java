package fr.correntin.android.recyclerview_example;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter of main recycler view created in {@link MainActivity}<br/>
 * This adapter manages cities
 * <p>
 * Created by corentin on 10/05/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CityHolder>
{
    /**
     * We define a ViewHolder to store main item view
     * and other information about this
     * ViewHolder must extends RecyclerView.ViewHolder
     */
    public static class CityHolder extends RecyclerView.ViewHolder
    {
        public CityHolder(View itemView)
        {
            super(itemView);
        }
    }

    /**
     * Adapter data
     */
    private final List<City> cities;

    /**
     * Listener for internal buttons on items view
     */
    private View.OnClickListener onClickListener;

    /**
     * Default constructor
     *
     * @param defaultCities   Default data
     * @param onClickListener Listener for moreInfo and shareButton on each items
     */
    RecyclerViewAdapter(City[] defaultCities, View.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
        this.cities = new ArrayList<>();

        if (defaultCities != null)
            Collections.addAll(this.cities, defaultCities);
    }

    /**
     * In this function, we create the ViewHolder of an item<br/>
     * If in your RecyclerView, you want different items with different layout<br/>
     * you can inflate the desired layout. Use @param viewType to define which view inflate
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerViewAdapter.CityHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = null;

        if (viewType == 1)
            itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_city_item, parent, false);
        return new CityHolder(itemLayoutView);
    }

    /**
     * Return the viewType about the position<br/>
     * In this example, we use just one layout
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position)
    {
        return 1;
    }

    /**
     * Here, we update all subviews of itemView with data from ViewHolder or with position
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.CityHolder holder, int position)
    {
        City city = this.cities.get(position);

        TextView descriptionTextView = (TextView) holder.itemView.findViewById(R.id.recycler_view_city_item_description);
        TextView nameTextView = (TextView) holder.itemView.findViewById(R.id.recycler_view_city_item_name);
        ImageView imageImageView = (ImageView) holder.itemView.findViewById(R.id.recycler_view_city_item_image);
        View moreInfoButton = holder.itemView.findViewById(R.id.recycler_view_city_item_more_infos_button);
        View shareButton = holder.itemView.findViewById(R.id.recycler_view_city_item_share_button);

        // To know which city data is using in view, we store position in tag to retrieve easily in onClick function
        moreInfoButton.setTag(position);
        moreInfoButton.setOnClickListener(this.onClickListener);
        shareButton.setTag(position);
        shareButton.setOnClickListener(this.onClickListener);

        // Set data in subviews
        descriptionTextView.setText(city.getDescription());
        nameTextView.setText(city.getName());
        imageImageView.setImageResource(city.getImage());
    }

    @Override
    public int getItemCount()
    {
        return cities.size();
    }

    public void addCity(City city)
    {
        if (this.cities.size() > 0)
        {
            this.cities.add(0, city);
            this.notifyItemInserted(0);
        }
        else
        {
            this.cities.add(city);
            this.notifyItemInserted(0);
        }
    }

    public void removeCity()
    {
        this.cities.remove(0);
        this.notifyItemRemoved(0);
    }

    public City getCity(int position)
    {
        if (position >= 0 && position < this.cities.size())
            return this.cities.get(position);
        return null;
    }
}

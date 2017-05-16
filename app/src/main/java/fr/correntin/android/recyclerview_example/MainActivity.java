package fr.correntin.android.recyclerview_example;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.security.SecureRandom;

/**
 * Main activity of App
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constants
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String TAG = MainActivity.class.getName();

    /**
     * Default data
     **/
    private static final City[] PREDEFINED_CITIES = new City[5];

    static
    {
        PREDEFINED_CITIES[0] = (new City("Lille", "A city in northern France", R.mipmap.lille));
        PREDEFINED_CITIES[2] = (new City("Paris", "Most populous city of France", R.mipmap.paris));
        PREDEFINED_CITIES[1] = (new City("Brest", "Second French military port", R.mipmap.brest));
        PREDEFINED_CITIES[3] = (new City("Toulouse", "Airbus city", R.mipmap.toulouse));
        PREDEFINED_CITIES[4] = (new City("Marseille", "Second largest city in France", R.mipmap.marseille));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private RecyclerViewAdapter recyclerViewAdapter;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Activity life cycle
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the recyclerView from contentView
        final RecyclerView recyclerView = this.getRecyclerView();

        // Create the adapter
        this.recyclerViewAdapter = new RecyclerViewAdapter(PREDEFINED_CITIES, this);

        // Attach a layout Manager to recycler view
        // LayoutManager defines properties about recycler view example if it's linear or a grid
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Attach the adapter
        recyclerView.setAdapter(this.recyclerViewAdapter);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Events
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onClick(View v)
    {
        /**
         * To retrieve data of an item view, we can use the position previously stored in view tag
         * With this, we can retrieve our city with simple getCity(position)
         */
        if (v.getId() == R.id.recycler_view_city_item_more_infos_button)
        {
            City city = this.recyclerViewAdapter.getCity((Integer) v.getTag());
            Toast.makeText(this, city.getName() + " : " + city.getDescription(), Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.recycler_view_city_item_share_button)
        {
            City city = this.recyclerViewAdapter.getCity((Integer) v.getTag());

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, city.getName() + " : " + city.getDescription());
            sendIntent.setType("text/plain");
            try
            {
                startActivity(sendIntent);
            }
            catch (Exception e)
            {
                Log.e(TAG, "onClick: ", e);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.menu_main_action_add_city)
        {
            this.recyclerViewAdapter.addCity(this.getNewCity());
            this.getRecyclerView().scrollToPosition(0);
            return true;
        }
        else if (id == R.id.menu_main_action_del_city)
        {
            if (this.recyclerViewAdapter.getItemCount() > 0)
                this.recyclerViewAdapter.removeCity();
            return true;
        }
        else if (id == R.id.menu_main_action_dev_info)
        {
            AlertDialog devInfoDialog = new AlertDialog.Builder(MainActivity.this).create();
            devInfoDialog.setMessage(this.getResources().getString(R.string.dev_info_message));
            devInfoDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Utility functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public RecyclerView getRecyclerView()
    {
        return (RecyclerView) findViewById(R.id.recyclerView);
    }


    private City getNewCity()
    {
        SecureRandom secureRandom = new SecureRandom();
        return PREDEFINED_CITIES[(secureRandom.nextInt(4))];
    }
}

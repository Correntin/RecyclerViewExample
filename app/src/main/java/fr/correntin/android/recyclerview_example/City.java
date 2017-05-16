package fr.correntin.android.recyclerview_example;

/**
 * Basic object to store information about a city
 * <p>
 * Created by corentin on 10/05/2017.
 */

public class City
{
    private String name;
    private String description;
    private int image;

    public City(String name, String description, int image)
    {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getImage()
    {
        return image;
    }
}

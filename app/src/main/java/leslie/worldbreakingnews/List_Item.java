package leslie.worldbreakingnews;

/**
 * Created by Leslie on 5/21/2021.
 */

public class List_Item {
    private String image;
    private String title;

    public  List_Item(String image,String title)
    {
        this.image = image;
        this.title = title;
    }
    public  String getImage()
    {
        return image;
    }
    public  void setImage()
    {
        this.image=image;
    }
    public  String getTitle()
    {
        return title;
    }
    public  void setTitle()
    {
        this.title=title;
    }

}

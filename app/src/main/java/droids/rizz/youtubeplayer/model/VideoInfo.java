package droids.rizz.youtubeplayer.model;

public class VideoInfo {
    private String name;
    private String title;
    private String subTitle;
    private String imageUrl;

    public VideoInfo(String title, String subTitle, String imageUrl) {
        this.title = title;
        this.subTitle = subTitle;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

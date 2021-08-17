package sg.edu.rp.c346.id20046523.mycomment;

public class Show {

    private int id;
    private String name;
    private int yor;
    private int stars;
    private String voice;

    public Show(int id, String name, int yor,int stars, String voice) {
        this.id = id;
        this.name = name;
        this.yor = yor;
        this.stars = stars;
        this.voice = voice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYor() {
        return yor;
    }

    public int getStars() {
        return stars;
    }

    public String getVoice() {
        return voice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYor(int yor) {
        this.yor = yor;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }


}

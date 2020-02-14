package progmatic.hegymaszas.dto;

public class FeedbackCreateDto {

    private int rating;
    private String text;


    public FeedbackCreateDto() {
    }


    public int getRating() {
        return rating;
    }


    public void setRating(int rating) {
        this.rating = rating;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }
}

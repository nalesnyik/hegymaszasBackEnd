package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.messages.Feedback;

public class FeedbackShowDto extends MessageShowDto {
    private long routeId;
    private int rating;


    public FeedbackShowDto(Feedback feedback) {
        id = feedback.getId();
        routeId = feedback.getRoute().getId();
        rating = feedback.getRating();
        text = feedback.getText();
        creationDate = feedback.getCreationDate();
        username = feedback.getUser().getName();
    }


    public FeedbackShowDto() {
    }


    public long getRouteId() {
        return routeId;
    }


    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }


    public int getRating() {
        return rating;
    }


    public void setRating(int rating) {
        this.rating = rating;
    }
}

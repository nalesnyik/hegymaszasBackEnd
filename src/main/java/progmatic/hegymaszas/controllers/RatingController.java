package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progmatic.hegymaszas.modell.Rating;
import progmatic.hegymaszas.services.RatingService;

import javax.validation.Valid;

@RestController
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/route/rating")
    public Rating showCreatedRating() {
        return new Rating();
    }

    @PostMapping("route/rating")
    public void createRating(@RequestBody Rating rating) {
        ratingService.addRating(rating);
    }
}
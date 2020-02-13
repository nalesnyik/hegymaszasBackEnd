package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progmatic.hegymaszas.dto.RatingCreateDto;
import progmatic.hegymaszas.dto.RatingModifyDto;
import progmatic.hegymaszas.dto.RatingShowDto;
import progmatic.hegymaszas.exceptions.NotAppropriateNumberOfStarsForRatingException;
import progmatic.hegymaszas.exceptions.RatingNotFoundException;
import progmatic.hegymaszas.exceptions.RouteNotFoundException;
import progmatic.hegymaszas.exceptions.RouteRatingByUserExistsException;
import progmatic.hegymaszas.services.RatingService;

@RestController
public class RatingController {

    private RatingService ratingService;


    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @GetMapping("/route/rating/{routeId}")
    public Double showCreatedRating(@PathVariable long routeId) {
        return (Double) 0.0;
    }


    @PostMapping("/route/{routeId}/rating")
    public RatingShowDto createRating(
            @PathVariable("routeId") long Id,
            @RequestBody RatingCreateDto rating) throws RouteNotFoundException, NotAppropriateNumberOfStarsForRatingException, RouteRatingByUserExistsException {
        return ratingService.addRating(rating);
    }


    @PatchMapping("/route/{routeId}/rating")
    public RatingShowDto modifyRating(
            @PathVariable("routeId") long Id,
            @RequestBody RatingCreateDto rating) throws NotAppropriateNumberOfStarsForRatingException, RatingNotFoundException {
        return ratingService.modifyRating(rating);
    }
}

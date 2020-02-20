package progmatic.hegymaszas.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import progmatic.hegymaszas.exceptions.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler({ClimbingPlaceNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleClimbingPlaceNotFound(ClimbingPlaceNotFoundException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "Climbingplace not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({ImageNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleImageNotFound(ImageNotFoundException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "Image not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({NotAppropriateNumberOfStarsForRatingException.class})
    public ResponseEntity<Map<String, String>> handleNotAppropriateNumberOfStarsForRating(NotAppropriateNumberOfStarsForRatingException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "Number of stars are invalid.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({RatingNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleRouteRatingByUserExists(RatingNotFoundException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "Rating not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({RouteNameForSectorAlreadyExistsException.class})
    public ResponseEntity<Map<String, String>> handleRouteNameForSectorAlreadyExists(RouteNameForSectorAlreadyExistsException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({RouteNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleRouteNotFound(RouteNotFoundException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "Route not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({RouteRatingByUserExistsException.class})
    public ResponseEntity<Map<String, String>> handleRouteRatingByUserExists(RouteRatingByUserExistsException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "Rating has already been created for this route.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({SectorNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleSectorNotFound(SectorNotFoundException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "Sector not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({SectorProfilePictureAlreadyExistsException.class})
    public ResponseEntity<Map<String, String>> handleSectorProfilePictureAlreadyExists(SectorProfilePictureAlreadyExistsException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "Profile picture for this sector already exists.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }


    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "User not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({WrongAscentTypeException.class})
    public ResponseEntity<Map<String, String>> handleWrongAscentType(WrongAscentTypeException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "Wrong ascenttype.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


}

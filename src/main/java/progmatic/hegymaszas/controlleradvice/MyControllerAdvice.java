package progmatic.hegymaszas.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import progmatic.hegymaszas.exceptions.RouteNameForSectorAlreadyExistsException;
import progmatic.hegymaszas.exceptions.SectorNotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler({RouteNameForSectorAlreadyExistsException.class})
    public ResponseEntity<Map<String, String>> handleRouteNameForSectorAlreadyExists(RouteNameForSectorAlreadyExistsException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler({SectorNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleSectorNotFound(SectorNotFoundException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }
}

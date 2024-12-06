package fr.diginamic.hello.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(InsertUpdateException.class)
    public ResponseEntity<String> handleFunctionnalException(InsertUpdateException ex) {
        return ResponseEntity.badRequest().body("Erreur d'insertion/mise à jour : " + ex.getMessage());
    }

    @ExceptionHandler(VilleNotFoundException.class)
    public ResponseEntity<String> handleFunctionnalException(VilleNotFoundException ex) {
        return ResponseEntity.badRequest().body("Erreur Ville non trouvée : " + ex.getMessage());
    }

    @ExceptionHandler(DepartementNotFoundException.class)
    public ResponseEntity<String> handleFunctionnalException(DepartementNotFoundException ex) {
        return ResponseEntity.badRequest().body("Erreur Département non trouvée : " + ex.getMessage());
    }


    @ModelAttribute
    public void addUserToModel(Model model, Authentication auth) {
        if (auth != null) {
            model.addAttribute("authentification", auth);
        }
    }


}

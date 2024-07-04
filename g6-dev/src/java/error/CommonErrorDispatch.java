package error;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonErrorDispatch {
    public static void ErrorDispatch(List<String> errors, HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getRequestURI());
        try {
            request.setAttribute("errors", errors);
            dispatcher.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CommonErrorDispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package servlet;

import com.google.gson.Gson;
import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String brand = req.getParameter("brand");
        String licensePlate = req.getParameter("licensePlate");
        String model = req.getParameter("model");
        long price = Long.parseLong(req.getParameter("price"));
        try {
            CarService.getInstance().addCar(new Car(brand, model, licensePlate, price));
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(403);
        }
    }
}

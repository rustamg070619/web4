package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {


    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public void buyCar(Car purchased) {
        Car carWithPrice = getCarFromBase(purchased);
        new CarDao(sessionFactory.openSession()).buyCar(carWithPrice);
    }

    public void addCar(Car added) throws Exception {
        new CarDao(sessionFactory.openSession()).addCar(added);
    }

    public Car getCarFromBase(Car car) {
        return new CarDao(sessionFactory.openSession()).getCarFromBase(car);
    }

    public void clearAllCars() {
        new CarDao(sessionFactory.openSession()).clearAllCars();
    }
}
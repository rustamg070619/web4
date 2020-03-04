package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.DailyReportService;

import java.util.Collections;
import java.util.List;

public class CarDao {

    private Session session;

    private static long earnings;
    private static long soldCars;

    public static void setEarnings(long earnings) {
        CarDao.earnings = earnings;
    }

    public static void setSoldCars(long soldCars) {
        CarDao.soldCars = soldCars;
    }

    public static long getEarnings() {
        return earnings;
    }

    public static long getSoldCars() {
        return soldCars;
    }

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return cars;
    }

    public void buyCar(Car purchased) {
        Transaction transaction = session.beginTransaction();
        session.delete(purchased);
        earnings += purchased.getPrice();
        soldCars++;
        CarDao.setEarnings(earnings);
        CarDao.setSoldCars(soldCars);
        transaction.commit();
        session.close();
    }

    public void addCar(Car added) throws Exception {
        Transaction transaction = session.beginTransaction();
        List<Integer> list = session.createQuery("Select count(*) from Car group by brand").list();
        if (list.contains(new Long(10))) {
            throw new Exception();
        } else {
            session.save(added);
            transaction.commit();
            session.close();
        }
    }


    public Car getCarFromBase(Car car) {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("FROM Car WHERE brand = :brandParam AND model = :modelParam AND licensePlate = :licensePlateParam")
                .setParameter("brandParam", car.getBrand())
                .setParameter("modelParam", car.getModel())
                .setParameter("licensePlateParam", car.getLicensePlate()).list();
        transaction.commit();
        session.close();
        return cars.get(0);

    }

    public void clearAllCars() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        transaction.commit();
        session.close();
    }
}

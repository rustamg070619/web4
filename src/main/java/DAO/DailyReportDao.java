package DAO;

import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> list = session.createQuery("FROM DailyReport ORDER BY id DESC")
                                        .setMaxResults(2)
                                        .list();
        DailyReport lastReport = list.get(0);
        transaction.commit();
        session.close();
        return lastReport;
    }


    public void UpdateReport() {
        Transaction transaction = session.beginTransaction();
        session.save(new DailyReport(CarDao.getEarnings(), CarDao.getSoldCars()));
        transaction.commit();
        session.close();
        clearReport();
    }

    public void clearReport() {
        CarDao.setEarnings(0);
        CarDao.setSoldCars(0);
    }

    public void clearAllReports() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM DailyReport").executeUpdate();
        transaction.commit();
        session.close();
    }
}

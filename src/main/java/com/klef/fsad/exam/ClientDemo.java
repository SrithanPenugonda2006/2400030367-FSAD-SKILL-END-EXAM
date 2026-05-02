package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.Scanner;

public class ClientDemo {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Vehicle.class)
                .buildSessionFactory();

        Session session = factory.openSession();
        Scanner sc = new Scanner(System.in);

        int choice = 0;

        while (choice != 3) {
            System.out.println("\n--- VEHICLE MENU ---");
            System.out.println("1. Insert Vehicle");
            System.out.println("2. Update Vehicle");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sc.nextLine(); // clear buffer

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Description: ");
                    String desc = sc.nextLine();

                    System.out.print("Enter Status: ");
                    String status = sc.nextLine();

                    session.beginTransaction();

                    Vehicle v = new Vehicle(name, desc, new Date(), status);
                    session.save(v);

                    session.getTransaction().commit();

                    System.out.println("Vehicle Inserted Successfully!");
                    break;

                case 2:
                    System.out.print("Enter Vehicle ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    session.beginTransaction();

                    Vehicle vehicle = session.get(Vehicle.class, id);

                    if (vehicle != null) {
                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter New Status: ");
                        String newStatus = sc.nextLine();

                        vehicle.setName(newName);
                        vehicle.setStatus(newStatus);

                        session.update(vehicle);
                        System.out.println("Vehicle Updated Successfully!");
                    } else {
                        System.out.println("Vehicle Not Found!");
                    }

                    session.getTransaction().commit();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        session.close();
        factory.close();
        sc.close();
    }
}

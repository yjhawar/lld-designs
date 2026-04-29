

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import Entities.Cinema;
import Entities.ScreenLayout;
import Entities.Seat;
import Entities.SeatType;
import Entities.Show;
import Interfaces.BookMyShowService;
import Services.BookMyShowServiceImpl;
import Strategy.SeatBasedPricingStrategy;
import Strategy.SurgePricingStrategy;
import Entities.Ticket;
import java.util.*;


public class Demo {
	public static void main (String[]args){
        System.out.println("\n--- Movie Booking Demo ---");
    
        BookMyShowService service = BookMyShowServiceImpl.getInstance();

        // Add movies
        service.addMovie("1", "Avengers", 180);
        service.addMovie("2", "Inception", 150);    
    
        // Add cinema
        ScreenLayout layout = new ScreenLayout("screen1",3,3, new HashMap<>(){{
            put(1, SeatType.PREMIUM);
            put(2, SeatType.RECLINER);
        }});
        Cinema cinema = service.addCinema(
               "PVR", "BLR",
                List.of(layout,layout) // Adding 2 screens with same layout for simplicity
        );

        Show show1 = service.addShow("1", "Avengers", cinema.getScreens().get(0),LocalDateTime.now(), new SeatBasedPricingStrategy());
        Show show2 = service.addShow("2", "Avengers", cinema.getScreens().get(1),LocalDateTime.now().plusHours(2), new SurgePricingStrategy(new SeatBasedPricingStrategy(), 1.5));
        Show show3 = service.addShow("3", "Inception", cinema.getScreens().get(0),LocalDateTime.now().plusHours(4), new SeatBasedPricingStrategy());


        // 1. Search for shows and seats
        List<Show> availableShows = service.findShows("Avengers", "BLR");
        System.out.println("Available shows for 'Avengers' in BLR: " + availableShows.stream()
                .map(show -> String.format("%s at %s", show.getMovie().getTitle(), show.getStartTime()))
                .collect(Collectors.toList()));
        List<Seat> seats = service.getAvailableSeats(show1.getId());
        System.out.println("Available seats for show1: " + seats.stream().map(Seat::getId).toList());


        Runnable userTask = () -> {
            try {
                Ticket ticket = service.bookTickets(
                        Thread.currentThread().getName(),
                        show1.getId(),
                        new ArrayList<>(show1.getScreen().getSeats().subList(0, 2))
                );
                System.out.println("Booking succeeded for movie: " + show1.getMovie().getTitle() + " with ticket: " + ticket.getBarcode());
            } catch (Exception e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        };

        Runnable userTask2 = () -> {
            try {
                Ticket ticket = service.bookTickets(
                        Thread.currentThread().getName(),
                        show3.getId(),
                        new ArrayList<>(show3.getScreen().getSeats().subList(0, 4))
                );
                System.out.println("Booking succeeded for movie: " + show3.getMovie().getTitle() + " with ticket: " + ticket.getBarcode());
            } catch (Exception e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(userTask);
        executor.submit(userTask);
        executor.submit(userTask2);
        executor.shutdown();
    }
}

// class UserTask implements Runnable {
//     private final BookMyShowService service;
//     private final String userId;

//     public UserTask(BookMyShowService service, String userId) {
//         this.service = service;
//         this.userId = userId;
//     }

//     @Override
//     public void run() {
//         try {
//             List<Show> shows = service.getShows("Avengers");

//             List<Seat> seats = service.getAvailableSeats(shows.get(0).getId());

//             // pick 2 seats
//             List<String> seatIds = List.of(seats.get(0).getId(), seats.get(1).getId());

//             service.bookSeats(userId, shows.get(0).getId(), seatIds);

//             System.out.println(userId + " booked seats successfully");

//         } catch (Exception e) {
//             System.out.println(userId + " failed: " + e.getMessage());
//         }
//     }
// }

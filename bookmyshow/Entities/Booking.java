package Entities;

import java.util.List;

public class Booking {
    private final String id;
    private final String user;
    private final Show show;
    private final List<Seat> seats;
    private final double totalAmount;
    // private final Payment payment;

    // Private constructor to be used by the Builder
    private Booking(String id, String user, Show show, List<Seat> seats, double totalAmount) {
        this.id = id; // TODO: Generate unique ID
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.totalAmount = totalAmount;
        // this.payment = payment;
    }

    public String getId() { return id; }
    public String getUser() { return user; }
    public Show getShow() { return show; }
    public List<Seat> getSeats() { return seats; }
    public double getTotalAmount() { return totalAmount; }
    // public Payment getPayment() { return payment; }

    // Static inner Builder class
    public static class BookingBuilder {
        private String id;
        private String user;
        private Show show;
        private List<Seat> seats;
        private double totalAmount;
        // private Payment payment;

        public BookingBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public BookingBuilder setUser(String user) {
            this.user = user;
            return this;
        }

        public BookingBuilder setShow(Show show) {
            this.show = show;
            return this;
        }

        public BookingBuilder setSeats(List<Seat> seats) {
            this.seats = seats;
            return this;
        }

        public BookingBuilder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        // public BookingBuilder setPayment(Payment payment) {
        //     this.payment = payment;
        //     return this;
        // }

        public Booking build() {
            // Validations can be added here
            return new Booking(id, user, show, seats, totalAmount);
        }
    }
}
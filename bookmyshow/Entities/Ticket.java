package Entities;

public class Ticket {
	private final String bookingId;
	private final String barCode;

	public Ticket(String bookingId) {
		this.barCode = "BARCODE-" + bookingId;
		this.bookingId = bookingId;
	}

	public String getBarcode() { return barCode; }
	public String getBooking() { return bookingId; }
}
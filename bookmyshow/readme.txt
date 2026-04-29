Feature
* Book a ticket
* Search by city for movie or show etc (optional)

Out of scope
* payment mechanism
* notification system. Notify when movie is released.
* Schedulor to unlock seats to be implemented


Considerations
* How to avoid Double Booking


Entities:

1. Movie (title, cast:vector<string>, duration)
2. Booking (id)
3. Cinema (Screen:vector )
4. Screen (2D matrix of seat, Cinema)
5. Seat (seatStatus, type, number)
6. Show (id, movie, Screen, startTime, pricingStrategy)
7. User???
8. Ticket (price)
9. Payment mode(optional)
enum:
SeatStatus
SeatType

Questions:
* Should service contain a function that shows available seats to user or demo must handle it or assume
user would give valid seats? 
=> Assume user will give valid seats
* Should  screen contain a variable for cinema
=> Yes, the reference from screen to cinema is needed as you can navigate from show to screen to cinema
while seat need not have reference to screen.
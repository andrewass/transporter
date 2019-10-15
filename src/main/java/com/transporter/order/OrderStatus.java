package com.transporter.order;

public enum OrderStatus {
    /*
    Status used while awaiting confirmation from the driver
     */
    AWAITING_CONFIRMATION,
    /*
    Status used while awaiting trip activation
     */
    AWAITING_TRIP,
    /*
    Status when trip is activated and in session
     */
    TRIP_ACTIVATED,
    /*
    Status when trip is complete
     */
    COMPLETE,
    /*
    Status when order is cancelled
     */
    CANCELLED
}

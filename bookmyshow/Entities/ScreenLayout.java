package Entities;

import java.util.Map;

public class ScreenLayout {
    public String screenId;
    public int rows;
    public int cols;
    public Map<Integer, SeatType> rowToSeatType;
    public ScreenLayout(String screenId, int rows, int cols, Map<Integer, SeatType> rowToSeatType) {
        this.screenId = screenId;
        this.rows = rows;
        this.cols = cols;
        this.rowToSeatType = rowToSeatType;
    }
}

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;
import java.util.Vector;

/**
 * Created by nthangavelu on 4/19/17.
 */
public class ParkingLot
{

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    Vector<ParkingSpace> vacantParkingSpaces = null;
    Vector<ParkingSpace> fullParkingSpaces = null;

    int parkingSpaceCount = 0;

    boolean isFull;
    boolean isEmpty;
    boolean isRegular;
    boolean isCompact;
    boolean isHandicaped;

    public ParkingLot(Vector<ParkingSpace> vacantParkingSpaces, Vector<ParkingSpace> fullParkingSpaces) {
        this.vacantParkingSpaces = vacantParkingSpaces;
        this.fullParkingSpaces = fullParkingSpaces;
    }

    Optional<ParkingSpace> findNearestVacant(ParkingType type)
    {
        return vacantParkingSpaces.stream()
                .filter(p -> p.parkingType.equals(type) && p.isVacant == true)
                .findFirst();
    }

    String parkVehicle(ParkingType type, Vehicle vehicle)
    {
        if(!isFull())
        {
            return findNearestVacant(type)
                    .map(p -> {
                        p.vehicle = vehicle;
                        p.isVacant = false;
                        p.date = LocalDateTime.now();
                        vacantParkingSpaces.remove(p);
                        fullParkingSpaces.add(p);
                        if(fullParkingSpaces.size() == parkingSpaceCount)
                            isFull = true;
                        isEmpty = false;
                        return "Success allocation of space: Spot No : " +  p.spaceNumber + ", time : " + LocalDateTime.now();
                    })
                    .orElseGet(() -> {
                        updateStatus(type, true);
                        return "Parking type : " + type + " is not available at the moment.";
                    });
        } else {
            return "Parking is full.";
        }
    }

    String findVehicle(String val) {
        return fullParkingSpaces.stream()
                .filter(p -> p.vehicle.vehicleNumber.equals(val))
                .findFirst()
                .map(p -> "Vehicle Number : " + val + "\nSpot Number : " + p.spaceNumber + "\nCheckin : " + p.date
                        + "\nParking Type : " + p.parkingType)
                .orElse("Not Found");
    }

    private void updateStatus(ParkingType type, boolean val) {
        switch (type) {
            case REGULAR:
                this.isRegular = val;
                break;
            case  COMPACT:
                this.isCompact = val;
                break;
            case HANDICAPPED:
                this.isHandicaped = val;
                break;
        }
    }

    String releaseVehicle(Vehicle vehicle)
    {
        if(!isEmpty())
        {
            return fullParkingSpaces.stream()
                    .filter(p -> p.vehicle.vehicleNumber == vehicle.vehicleNumber)
                    .findFirst()
                    .map(p -> {
                        fullParkingSpaces.remove(p);
                        vacantParkingSpaces.add(p);
                        String message = getCalculatedAmount(p);
                        p.isVacant = true;
                        p.vehicle = null;
                        if(vacantParkingSpaces.size() == parkingSpaceCount)
                            isEmpty = true;
                        isFull = false;
                        updateStatus(p.parkingType, false);
                        return message;
                    })
                    .orElse("Sorry Vehicle number provide is not found.");
        }
        return "Parking lot is Empty.";
    }

    private String getCalculatedAmount(ParkingSpace p) {
        Period period = Period.between(LocalDateTime.now().toLocalDate(), p.date.toLocalDate());
        Duration duration = Duration.between(LocalDateTime.now(), p.date);
        long hours = duration.getSeconds() / SECONDS_PER_HOUR;
        long minutes = ((duration.getSeconds() % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        float calculatedValue = (period.getDays() * 24) + (hours * 4) + (minutes * 1);
        return "Vehicle : " + p.vehicle.vehicleNumber + "\nCheckin Datetime : " + p.date + "\ncheckout DateTime : "
                +  LocalDateTime.now() + "\nAmount : $ " + calculatedValue;
    }

    String status() {
        return "Regular : " + (this.isRegular ? "Full" : "Available(" + (getAvailableParkingSpots(ParkingType.REGULAR)) + ")") + "\nCompact : "
                + (this.isCompact ? "Full" : "Available(" + (getAvailableParkingSpots(ParkingType.COMPACT)) + ")") + "\nHandicapped : " +
                (this.isHandicaped ? "Full" : "Available(" + (getAvailableParkingSpots(ParkingType.HANDICAPPED)) + ")");
    }

    long getAvailableParkingSpots(ParkingType type) {
        return vacantParkingSpaces.stream().filter(p -> p.parkingType.equals(type))
                .count();
    }

    boolean isFull()
    {
        return isFull;
    }

    boolean isEmpty()
    {
        return isEmpty;
    }

    public static void main(String[] args) {
        Vector<ParkingSpace> vacantParkingSpaces = new Vector<>();

        int count=1;
        count = addData(vacantParkingSpaces, ParkingType.REGULAR, count);
        count = addData(vacantParkingSpaces, ParkingType.COMPACT, count);
        count = addData(vacantParkingSpaces, ParkingType.MAX_PARKING_TYPE, count);
        count = addData(vacantParkingSpaces, ParkingType.HANDICAPPED, count);


        ParkingLot parkingLot = new ParkingLot(vacantParkingSpaces, new Vector<>());
        parkingLot.parkingSpaceCount = count;

        System.out.println(parkingLot.findVehicle("1234"));
        System.out.println(parkingLot.parkVehicle(ParkingType.REGULAR, new Vehicle("1234")));
        System.out.println(parkingLot.parkVehicle(ParkingType.HANDICAPPED, new Vehicle("2345")));
        System.out.println(parkingLot.parkVehicle(ParkingType.COMPACT, new Vehicle("9807")));
        System.out.println(parkingLot.parkVehicle(ParkingType.REGULAR, new Vehicle("5688")));
        System.out.println(parkingLot.findVehicle("1234"));
        System.out.println(parkingLot.releaseVehicle( new Vehicle("5688")));
        System.out.println(parkingLot.status());


        Promise
    }

    private static int addData(Vector<ParkingSpace> vacantParkingSpaces, ParkingType type, int count) {
        for (int i = 0; i < 10; i++) {
            vacantParkingSpaces.add(getParkingSpace(type, count));
            count++;
        }
        return count;
    }

    public static ParkingSpace getParkingSpace(ParkingType type, int count) {
        ParkingSpace ps = new ParkingSpace();
        ps.parkingType = type;
        ps.isVacant = true;
        ps.spaceNumber = count;
        return ps;
    }
}

class ParkingSpace
{
    int spaceNumber;
    boolean isVacant;
    Vehicle vehicle;
    ParkingType parkingType;
    int distance;
    LocalDateTime date;
}

class Vehicle
{
    Vehicle(String num) {
        this.vehicleNumber = num;
    }
    String vehicleNumber;
}

enum ParkingType
{
    REGULAR,
    HANDICAPPED,
    COMPACT,
    MAX_PARKING_TYPE,
}

public class Car {
    final private String carName;
    final private String carColor;

    public Car(String carName, String carColor) {
        this.carName = carName;
        this.carColor = carColor;
    }

    @Override
    public String toString() {
        return carName.substring(0,1).toUpperCase()
                + carName.substring(1).toLowerCase()
                + " в цвете " + carColor.toLowerCase();
    }
}

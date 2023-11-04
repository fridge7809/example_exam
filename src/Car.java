public class Car {
	private String plate;
	private int milage, lastServiceMilage, tankCapacity, gasLevel;
	private float consumption;

	public Car(String plate, int tankCapacity, float consumption) {
		this.plate = plate;
		this.milage = 0;
		this.lastServiceMilage = 0;
		this.tankCapacity = tankCapacity;
		this.gasLevel = 0;
		this.consumption = consumption;
	}

	public String getPlate() {
		return plate;
	}

	public int getTankCapacity() {
		return tankCapacity;
	}

	public int getGasLevel() {
		return gasLevel;
	}

	public int kmSinceService() {
		return (milage - lastServiceMilage);
	}

	public void fillTank(int gasAmount) {
		if (gasLevel + gasAmount > tankCapacity) {
			gasLevel += gasAmount;
		} else {
			throw new GasOverFlowException();
		}
	}

	public void service() {
		lastServiceMilage = milage;
	}

	public boolean needsService() {
		return kmSinceService() > 30000;
	}

	public boolean drive(int d) {
		int gasRequired = Math.round(d * consumption);
		if (gasRequired <= gasLevel) {
			gasLevel -= gasRequired;
			return true;
		}
		return false;
	}
}

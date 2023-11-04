import java.util.*;
import java.util.stream.Stream;

public class ServiceCenter {
	private List<Car> serviceQueue;
	private Map<String, Integer> carsPriority;
	private int gasPrice;

	public ServiceCenter(int gasPrice) {
		this.gasPrice = gasPrice;
		serviceQueue = new ArrayList<>();
		carsPriority = new HashMap<>();
	}

	public void addToServiceQueue(Car c) {
		if (carsPriority.containsKey(c.getPlate())) {
			serviceQueue.add(c);
		} else {
			System.out.printf("Car %s not found", c.getPlate());
		}
	}

	public int fillGas(Car c) {
		int amountToFill = c.getTankCapacity() - c.getGasLevel();
		c.fillTank(amountToFill);
		return amountToFill * gasPrice;
	}

	public void updatePriority(Car c, int p) {
		if (!carsPriority.containsKey(c.getPlate())) {
			carsPriority.put(c.getPlate(), p);
		} else {
			carsPriority.put(c.getPlate(), p);
		}
	}

	public void serviceCar(Car c) {
		int price = fillGas(c);
		if (c.needsService()) {
			c.service();
			price += 500;
		}
		System.out.printf("Car %s serviced for %d dollars", c.getPlate(), price);
	}

	public void serviceAll() {
		Iterator<Car> iterator = serviceQueue.iterator();
		iterator.forEachRemaining(car -> {
			serviceCar(car);
			iterator.remove();
		});
	}

	public int findHighestPriority() {
		int maxPrio = serviceQueue.stream()
				.mapToInt(car -> carsPriority.get(car.getPlate()))
				.max()
				.orElse(-1);
		if (maxPrio == -1) {
			return maxPrio;
		}
		List<String> plates =
				carsPriority.entrySet().stream()
						.filter(entry -> entry.getValue() == maxPrio)
						.sorted(Map.Entry.comparingByValue())
						.map(Map.Entry::getKey)
						.toList();
		return serviceQueue.indexOf(plates.get(0));
	}

	public void serviceAllWithPriority() {
		carsPriority.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.map(entry -> {
					for (Car car : serviceQueue) {
						if (car.getPlate().equals(entry.getKey())) {
							return car;
						}
					}
					return null;
				})
				.forEach(car -> {
					serviceCar(car);
					serviceQueue.remove(car);
				});
	}
}

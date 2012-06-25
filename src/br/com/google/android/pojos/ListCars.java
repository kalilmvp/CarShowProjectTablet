package br.com.google.android.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListCars implements Serializable {

	private static final long serialVersionUID = 4733530978546366340L;
	
	public static final String KEY = "cars";
	
	private List<Car> cars = new ArrayList<Car>();
	
	public ListCars(List<Car> cars) {
		this.cars = cars;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
}
package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService;

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}


	public void processInvoice(CarRental carRental) {
		long h1 = carRental.getStart().getTime();
		long h2 = carRental.getFinish().getTime();
		double total = (double)(h2 - h1) / 1000 / 60 / 60;
		
		double basicPayment;
		if(total <= 12.0) {
			basicPayment = Math.ceil(total) * pricePerHour;
		}
		else {
			basicPayment = Math.ceil(total / 24) * pricePerDay; 
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
		
	}

}

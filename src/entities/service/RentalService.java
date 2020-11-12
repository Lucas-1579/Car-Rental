package entities.service;

import entities.models.CarRental;
import entities.models.Invoice;

public class RentalService {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private BrazilTaxService taxService;

	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public BrazilTaxService getTaxService() {
		return taxService;
	}

	public void setTaxService(BrazilTaxService taxService) {
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

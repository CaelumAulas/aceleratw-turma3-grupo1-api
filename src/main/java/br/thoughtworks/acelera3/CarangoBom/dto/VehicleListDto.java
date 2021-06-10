package br.thoughtworks.acelera3.CarangoBom.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;

public class VehicleListDto {

	private List<VehicleDto> content;
	private int page;
	private int pageSize;
	private int totalPages;
	private boolean hasNext;
	private boolean hasPrevious;
	
	public VehicleListDto() { }
	
	public VehicleListDto(Page<Vehicle> input) {
		content = input.getContent().stream().map(VehicleDto::new).collect(Collectors.toList());
		page = input.getNumber();
		pageSize = input.getSize();
		totalPages = input.getTotalPages();
		hasNext = input.hasNext();
		hasPrevious = input.hasPrevious();
	}
	
	public List<VehicleDto> getContent() {
		return content;
	}
	public int getPage() {
		return page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public boolean hasNext() {
		return hasNext;
	}
	public boolean hasPrevious() {
		return hasPrevious;
	}
}

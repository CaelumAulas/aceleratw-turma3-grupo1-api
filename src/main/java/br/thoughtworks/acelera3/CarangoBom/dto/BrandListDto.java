package br.thoughtworks.acelera3.CarangoBom.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.thoughtworks.acelera3.CarangoBom.models.Brand;

public class BrandListDto {

	private List<BrandDto> content;
	private int page;
	private int pageSize;
	private int totalPages;
	private boolean hasNext;
	private boolean hasPrevious;
	
	public BrandListDto() { }
	
	public BrandListDto(Page<Brand> input) {
		content = input.getContent().stream().map(BrandDto::new).collect(Collectors.toList());
		page = input.getNumber();
		pageSize = input.getSize();
		totalPages = input.getTotalPages();
		hasNext = input.hasNext();
		hasPrevious = input.hasPrevious();
	}
	
	public List<BrandDto> getContent() {
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
	public boolean getHasNext() {
		return hasNext;
	}
	public boolean getHasPrevious() {
		return hasPrevious;
	}
	
}

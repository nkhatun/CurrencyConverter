package nkhatun.demo.v1.dto;

import java.util.List;

public class DataDto <T> {
	
    private List<T> dataItems;

	public DataDto(List<T> singletonList) {
		this.dataItems = singletonList ;
	}

	public List<T> getDataItems() {
		return dataItems;
	}

	public void setDataItems(List<T> dataItems) {
		this.dataItems = dataItems;
	}
    
}

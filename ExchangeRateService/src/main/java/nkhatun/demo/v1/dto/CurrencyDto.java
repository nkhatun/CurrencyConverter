package nkhatun.demo.v1.dto;

import java.io.Serializable;

public class CurrencyDto implements Serializable {
	private String code;
	private String name;
	private Long usageCount;
	
	public CurrencyDto() {
		super();
	}

	public CurrencyDto(String code, String name, Long usageCount) {
		this.code = code;
		this.name = name;
		this.usageCount = usageCount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUsageCount() {
		return usageCount;
	}

	public void setUsageCount(Long usageCount) {
		this.usageCount = usageCount;
	}
	
	

}

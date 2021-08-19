package nkhatun.demo.v1.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import nkhatun.demo.v1.dto.ResponseDto;

@ControllerAdvice
public class GlobalControllerAdvice {
	private static Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public @ResponseBody ResponseDto handleException(Exception e) {
		logger.error("Global Exception", e);
		return new ResponseDto("error", null, e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MethodArgumentNotValidException.class, MissingServletRequestParameterException.class })
	public @ResponseBody ResponseDto handleException(MethodArgumentNotValidException e) {
		logger.error("Global Validation Exception", e);
		return new ResponseDto("error", null, e.getMessage());
	}

}

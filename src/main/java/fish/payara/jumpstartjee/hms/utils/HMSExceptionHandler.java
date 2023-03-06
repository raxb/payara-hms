package fish.payara.jumpstartjee.hms.utils;

import java.util.Iterator;

import jakarta.faces.FacesException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;

public class HMSExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public HMSExceptionHandler(ExceptionHandler exception) {
		this.wrapped = exception;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {

		Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			ExceptionQueuedEvent event = i.next();
			Throwable t = getThrowable(event);

			HMSException exception = getBusinessException(t);
			if (exception != null) {
				try {
					if (exception instanceof ValidationViolationException) {
						handleValidations((ValidationViolationException) exception);
					} else {
						handleMessage(exception);
					}
				} finally {
					// remove it from queue
					i.remove();
				}
			}
		}
		// parent handle
		getWrapped().handle();
	}

	private HMSException getBusinessException(Throwable t) {
		HMSException result = null;
		Throwable stack = t;
		do {
			if (stack instanceof HMSException) {
				result = (HMSException) stack;
			} else {
				stack = stack.getCause();
			}
		} while (result == null && stack != null);
		return result;
	}

	private void handleMessage(HMSException businessException) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		externalContext.getFlash().setKeepMessages(true);

		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, businessException.getMessage(),
				businessException.getMessage()));

	}

	private void handleValidations(ValidationViolationException validationException) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		externalContext.getFlash().setKeepMessages(true);

		for (String message : validationException.getMessages()) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
		}
	}

	private Throwable getThrowable(ExceptionQueuedEvent event) {
		ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

		return context.getException();
	}

}
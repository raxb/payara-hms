package fish.payara.jumpstartjee;

import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.extern.java.Log;

@Interceptor
@Logged
@Log
public class LoggingInterceptor {
	
	Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@AroundInvoke
	public Object log(InvocationContext context) throws Exception {
		
		logger.info("Before ",context.getMethod());
		log.log(Level.INFO, () -> "<-- Before --> " + context.getMethod());
		System.out.println("Entering method: "
                + context.getMethod().getName() + " in class "
                + context.getMethod().getDeclaringClass().getName());
		Object result = context.proceed();
		log.log(Level.INFO, () -> "<-- After --> " + context.getMethod());

		return result;

	}

}

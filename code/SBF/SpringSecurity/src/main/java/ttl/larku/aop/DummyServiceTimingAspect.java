package ttl.larku.aop;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//@Component
public class DummyServiceTimingAspect {

    public DummyServiceTimingAspect() {
        int i = 0;
    }

    @Around("execution(* ttl.larku.aop.dumservice.*.*(..))")
    public Object timeDummyCall(ProceedingJoinPoint pjp) throws Throwable {
        Instant start = Instant.now();
        System.out.println("Before Call");

        Object retVal = pjp.proceed();

        System.out.println("After Call");

        System.out.printf("Call to %s took %d ms%n",
                pjp.getSignature(), start.until(Instant.now(), ChronoUnit.MILLIS));
        return retVal;
    }

    @Around("execution(* ttl.larku.service.*.*(..))")
    public Object timeCall(ProceedingJoinPoint pjp) throws Throwable {
        Instant start = Instant.now();
        System.out.println("Before Call");

        Object retVal = pjp.proceed();

        System.out.println("After Call");

        System.out.printf("Call to %s took %d ms%n",
                pjp.getSignature(), start.until(Instant.now(), ChronoUnit.MILLIS));
        return retVal;
    }
}

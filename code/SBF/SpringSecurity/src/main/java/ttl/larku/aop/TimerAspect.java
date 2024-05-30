package ttl.larku.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Aspect
@Component
public class TimerAspect {

    @Around("execution(* ttl.larku.service.*.*(..))")
    public Object timeCall(ProceedingJoinPoint pjp) throws Throwable {
        Instant start = Instant.now();
        System.out.println("Before Call to " + pjp.getSignature());

        Object retVal = pjp.proceed();

        System.out.println("After Call");

        System.out.println("Call to " + pjp.getSignature() + " took " + start.until(Instant.now(), ChronoUnit.MILLIS));
        // stop stopwatch
        return retVal;
    }
}

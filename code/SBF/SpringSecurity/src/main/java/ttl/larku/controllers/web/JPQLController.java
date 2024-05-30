package ttl.larku.controllers.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller
@RequestMapping("/jpql")
public class JPQLController {

    private static Logger logger = LoggerFactory.getLogger(JPQLController.class);

    @PersistenceUnit
    private EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager entityManager;

    private static Set<Class<?>> simpleTypes = Set.of(
            Boolean.class,
            Character.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Void.class,
            String.class
    );

    @GetMapping
    public String getJpqlPage(ModelAndView mav) {
        return "runJpql";
    }

    @PostMapping

    public ModelAndView runJpql(@RequestParam Map<String, String> params, HttpSession session) throws ClassNotFoundException {
        String jpql = params.get("jpql");
        //Save it now, in case we get execution errors
        session.setAttribute("jpqlstr", jpql);

        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> result = query.getResultList();

        boolean checkDistinct = Strings.isNotEmpty(params.get("distinct"));

        //We will get back a List of Object arrays
        //We just need to check the first element.  If it
        //is one of the "simpleTypes", then we assume
        //that all the elements are simpleTypes, and
        //just get their values.
        //If the first element is NOT a simpleType,
        //then our result contains objects, and we
        //need to use reflection to get the column names
        //and values.

        //Our goal is to come up with a Map of row to columValues
        var headers = new ArrayList<String>();

        boolean isSimple =
                !result.isEmpty()
                && (result.get(0)[0] == null || simpleTypes.contains(result.get(0)[0].getClass()));

        Object lastObject = null;

        //Turn everything into a map of row by columns.
        Map<Integer, List<Object>> rowCols = new HashMap<>();
        for (int row = 0; row < result.size(); row++) {
            var rowArray = result.get(row);
            //if (simpleTypes.contains(rowArray[0].getClass())) {
            if (isSimple) {
                rowCols.put(row, Arrays.asList(rowArray));
            } else {
                //It's not a "simple" object, so there should be just one.
                //Go through its properties.
                //Apparently Hibernate does not enforce distinctness
                //when working with Objects and Object arrays.
                //So we try and do a very simple minded
                // implementation of our own with 'lastObject' and
                // 'currObject'
                var currObject = rowArray[0];
//                if (currObject != lastObject) {
                    var propToValue = getObjects(currObject, headers, params);
                    rowCols.put(row, propToValue);

                    lastObject = currObject;
//                }
            }
        }

        //If you don't have any headers yet, it is because
        //we are dealing with projections or bits of an
        //entity, e.g. s.name, s.dob, etc.  In this
        //case we punt to have generic column names.
        if (!result.isEmpty() && headers.isEmpty()) {
            for (int i = 0; i < result.get(0).length; i++) {
                headers.add(STR."Field \{i}");
            }
        }

        var model = Map.of("headers", headers,
                "data", rowCols,
                "jpqlstr", jpql,
                    "resultSize", result.size());
        var mav = new ModelAndView("runJpql", model);
//        var mav = new ModelAndView("redirect:/jpql", model);

        System.out.println("Returning from POST method");
        return mav;
    }

    private static List<Object> getObjects(Object currObject,
                                           List<String> headers,
                                           Map<String, String> params) {

        boolean grabHeaders = headers.isEmpty();

        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(currObject);
        PropertyDescriptor[] propertyDescriptors = bw.getPropertyDescriptors();
        var propToValue = Arrays.stream(propertyDescriptors)
                .filter(pd -> {
                    return !pd.getName().equals("class") &&
                            !pd.getReadMethod().isAnnotationPresent(JsonIgnore.class);
                })
                .map(pd -> {
                    var name = pd.getName();
                    if (grabHeaders) {
                        headers.add(name);
                    }

                    Object value = null;
                    try {
                        value = pd.getReadMethod().invoke(currObject);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }

                    return value != null ? value : "";
                }).toList();
        return propToValue;
    }

    //Our very own Exception Handler
//    @ExceptionHandler(value = {Exception.class})
    protected ModelAndView jpaqlExceptionHandler(Exception ex, WebRequest request) {
        logger.error("UnExpected Exception in JPQL Exception Handler");
        ex.printStackTrace();
        var model = Map.of("errors", List.of(ex.getMessage()));
        var mav = new ModelAndView("runJpql", model);
        return mav;
    }
}

package utils;

import dto.ParametersDto;
import framework.state.ApplicationState;
import framework.utils.ConsoleUtils;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;

import java.util.Optional;

public class ParameterUtils {

    private ParameterUtils(){}

    public static Optional<ParametersDto> getParametersOrAskForThem(ApplicationState state) {
        PolynomialFunction function = (PolynomialFunction) state.getVariable("function");
        if (function == null) {
            ConsoleUtils.println("Pleas, specify the function");
            return Optional.empty();
        }
        double precision = (Double) state.getVariable("precision");
        if (precision <= 0) {
            ConsoleUtils.println("Pleas, specify the precision");
            return Optional.empty();
        }
        Interval interval = (Interval) state.getVariable("interval");
        if (interval == null) {
            ConsoleUtils.println("Pleas, specify the precision");
            return Optional.empty();
        }
        return Optional.of(new ParametersDto(function, interval, precision));
    }
}

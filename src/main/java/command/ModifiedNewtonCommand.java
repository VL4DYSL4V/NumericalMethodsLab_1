package command;

import dto.ParametersDto;
import framework.command.RunnableCommand;
import framework.state.ApplicationState;
import framework.state.ApplicationStateAware;
import framework.utils.ConsoleUtils;
import framework.utils.ValidationUtils;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import utils.ParameterUtils;

import java.util.Optional;

public class ModifiedNewtonCommand implements RunnableCommand, ApplicationStateAware {

    private ApplicationState state;

    @Override
    public void execute(String[] strings) {
        ValidationUtils.requireNonNull(state);
        Optional<ParametersDto> parametersOptional = ParameterUtils.getParametersOrAskForThem(state);
        if (parametersOptional.isPresent()) {
            PolynomialFunction function = (PolynomialFunction) state.getVariable("function");
            ParametersDto dto = parametersOptional.get();
            Interval interval = dto.getInterval();
            double product = function.value(interval.getInf()) * function.value(interval.getSup());
            if (product > 0) {
                ConsoleUtils.println("f(a)*f(b) must be <= 0");
                return;
            }
            modifiedNewton(function, interval, dto.getPrecision());
        }
    }

    public static void modifiedNewton(PolynomialFunction function, Interval interval, double precision) {
        PolynomialFunction firstDerivative = function.polynomialDerivative();
        PolynomialFunction secondDerivative = firstDerivative.polynomialDerivative();
        double x0 = function.value(interval.getInf()) * secondDerivative.value(interval.getInf()) <= 0
                ? interval.getSup()
                : interval.getInf();
        double x = x0;
        int iterationCount = 0;
        do {
            x -= function.value(x) / firstDerivative.value(x0);
            iterationCount++;
        } while (Math.abs(function.value(x) / firstDerivative.value(x0)) >= precision);
        ConsoleUtils.println(String.format("x = %f", x));
        ConsoleUtils.println(String.format("f(x) = %f", function.value(x)));
        ConsoleUtils.println(String.format("Iteration count: %d", iterationCount));
    }

    @Override
    public void setApplicationState(ApplicationState applicationState) {
        ValidationUtils.requireNonNull(applicationState);
        this.state = applicationState;
    }
}

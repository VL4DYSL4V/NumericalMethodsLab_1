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

public class RelaxationCommand implements RunnableCommand, ApplicationStateAware {

    private ApplicationState state;

    @Override
    public void execute(String[] strings) {
        ValidationUtils.requireNonNull(state);
        Optional<ParametersDto> parametersOptional = ParameterUtils.getParametersOrAskForThem(state);
        if (parametersOptional.isPresent()) {
            Interval interval = parametersOptional.get().getInterval();
            Interval intervalOfConvergence = getIntervalOfConvergence();
            if (interval.getInf() < intervalOfConvergence.getInf() || interval.getSup() > intervalOfConvergence.getSup()) {
                String template = "Interval of convergence: %f - %f";
                ConsoleUtils.println(String.format(template, intervalOfConvergence.getInf(), intervalOfConvergence.getSup()));
                return;
            }
            double precision = parametersOptional.get().getPrecision();
            PolynomialFunction function = (PolynomialFunction) state.getVariable("function");
            relaxation(function, interval, precision);
        }
    }

    private static void relaxation(PolynomialFunction function, Interval interval, double precision) {
        PolynomialFunction derivative = function.polynomialDerivative();
        double tau = getTau(derivative);
        double x = interval.getInf();
        ConsoleUtils.println(String.format("tau: %f", tau));
        double prevX = x;
        double prevX2;
        int iterationCount = 0;
        do {
            prevX2 = prevX;
            prevX = x;
            x = x + tau * function.value(x);
            iterationCount++;
        } while (iterationCount < 5 || Math.pow(x - prevX, 2) / Math.abs(2 * prevX - x - prevX2) < precision);
        ConsoleUtils.println(String.format("x = %f", x));
        ConsoleUtils.println(String.format("f(x) = %f", function.value(x)));
        ConsoleUtils.println(String.format("Iteration count: %d", iterationCount));

    }

    private static double getTau(PolynomialFunction derivative) {
        double term = Math.sqrt(17.0 / 3.0);
        double m1 = Math.max(Math.abs(derivative.value(1 - term)), 1e-15);
        double M1 = Math.abs(derivative.value(1));
        return 2.0 / (m1 + M1);
    }

    private static Interval getIntervalOfConvergence() {
        double term = Math.sqrt(17.0 / 3.0);
        return new Interval(1 - term, 1 + term);
    }

    @Override
    public void setApplicationState(ApplicationState applicationState) {
        ValidationUtils.requireNonNull(applicationState);
        this.state = applicationState;
    }
}

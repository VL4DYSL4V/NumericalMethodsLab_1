package command;

import dto.ParametersDto;
import framework.command.RunnableCommand;
import framework.state.ApplicationState;
import framework.state.ApplicationStateAware;
import framework.utils.ConsoleUtils;
import framework.utils.ValidationUtils;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialsUtils;
import utils.ParameterUtils;

import javax.naming.OperationNotSupportedException;
import java.util.Optional;

public class ModifiedNewtonCommand implements RunnableCommand, ApplicationStateAware {

    private ApplicationState state;

    @Override
    public void execute(String[] strings) {
        ValidationUtils.requireNonNull(state);
        Optional<ParametersDto> parametersOptional = ParameterUtils.getParametersOrAskForThem(state);
        if (parametersOptional.isPresent()) {
            ParametersDto dto = parametersOptional.get();
            if (dto.getFunction().degree() < 2) {
                ConsoleUtils.println("Polynomial function's degree is too small");
                return;
            }
            double product = dto.getFunction().value(dto.getInterval().getInf()) *
                    dto.getFunction().value(dto.getInterval().getSup());
            if (product >= 0) {
                ConsoleUtils.println("f(a)*f(b) must be < 0");
                return;
            }
            PolynomialFunction firstDerivative = dto.getFunction().polynomialDerivative();
            PolynomialFunction secondDerivative = firstDerivative.polynomialDerivative();
            ConsoleUtils.println(firstDerivative.toString());
            ConsoleUtils.println(secondDerivative.toString());


        }
    }

    private static double computeIterationsCount() {
        throw new RuntimeException();
    }

    @Override
    public void setApplicationState(ApplicationState applicationState) {
        ValidationUtils.requireNonNull(applicationState);
        this.state = applicationState;
    }
}

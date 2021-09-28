package command;

import dto.ParametersDto;
import framework.command.RunnableCommand;
import framework.state.ApplicationState;
import framework.state.ApplicationStateAware;
import framework.utils.ValidationUtils;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import utils.ParameterUtils;

import java.util.Optional;

public class DecompressionCommand implements RunnableCommand, ApplicationStateAware {

    private ApplicationState state;

    @Override
    public void execute(String[] strings) {
        ValidationUtils.requireNonNull(state);
        Optional<ParametersDto> parametersOptional = ParameterUtils.getParametersOrAskForThem(state);
        if (parametersOptional.isPresent()) {
//            PolynomialFunction function = parametersOptional.get().getFunction();
//            Interval interval = parametersOptional.get().getInterval();

        }
    }

    @Override
    public void setApplicationState(ApplicationState applicationState) {
        ValidationUtils.requireNonNull(applicationState);
        this.state = applicationState;
    }
}
